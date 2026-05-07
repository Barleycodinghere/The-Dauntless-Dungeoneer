package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table; //can probably change it to import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton; // to reduce the number of imports
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener; // same for utils
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.teamfive.dauntlessdungeoneer.screens.SettingsScreen;
import com.teamfive.dauntlessdungeoneer.entities.*;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.components.*;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.math.MathUtils;
import java.util.ArrayList;
import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.FireballAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.MagicMissileAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.RecoverAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.HeavyAttackAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.CleaveAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.BlockAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.SmiteAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.MassHealAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.HealAction;
import com.teamfive.dauntlessdungeoneer.combat.actions.BasicActionHandler;
import com.teamfive.dauntlessdungeoneer.combat.actions.dpsActions.DpsActionHandler;
import com.teamfive.dauntlessdungeoneer.combat.actions.tankActions.TankActionHandler;
import com.teamfive.dauntlessdungeoneer.combat.actions.supportActions.SupportActionHandler;
import com.teamfive.dauntlessdungeoneer.combat.components.CombatantComponent;
import com.teamfive.dauntlessdungeoneer.combat.managers.CombatManager;
import com.teamfive.dauntlessdungeoneer.combat.managers.TurnManager;
import com.teamfive.dauntlessdungeoneer.combat.results.CombatResult;
import com.teamfive.dauntlessdungeoneer.combat.systems.AccuracySystem;
import com.teamfive.dauntlessdungeoneer.combat.systems.CombatResolver;
import com.teamfive.dauntlessdungeoneer.combat.systems.DamageSystem;
import com.teamfive.dauntlessdungeoneer.combat.systems.HealthSystem;
import com.teamfive.dauntlessdungeoneer.combat.systems.TargetingSystem;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;
import com.teamfive.dauntlessdungeoneer.ui.CombatAreaView;
import com.teamfive.dauntlessdungeoneer.ui.CombatLogBox;
import com.teamfive.dauntlessdungeoneer.ui.SkillPanel;

public class GameplayScreen implements Screen {
    private final GameMain game;
    private Stage stage;
    private CombatAreaView combatAreaView;
    private Table root;

    private Team playerTeam;
    private Team enemyTeam;
    private Entity currentTarget;
    private Entity lastActor;
    private CombatManager combatManager;
    private CombatLogBox combatLogBox;
    private SkillPanel skillPanel;

    private ProgressBar.ProgressBarStyle hpStyle;
    private ProgressBar.ProgressBarStyle manaStyle;
    private Label currentTurnLabel;
    private boolean uiInitialized = false;
    private boolean hasBeenShownBefore = false;

    private BasicActionHandler basicActionHandler;
    private DpsActionHandler dpsActionHandler;
    private TankActionHandler tankActionHandler;
    private SupportActionHandler supportActionHandler;

    public GameplayScreen(GameMain game, Team playerTeam, Team enemyTeam) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        if (!hasBeenShownBefore) {
            if (!uiInitialized) {
                root = new Table();
                root.setFillParent(true);
                stage.addActor(root);

                initializeCombatSystems();

                // ADDED: Define how the HP bars look (Red fill, Gray background)
                hpStyle = new ProgressBar.ProgressBarStyle();
                hpStyle.background = game.skin.newDrawable("white", Color.DARK_GRAY);
                hpStyle.background.setMinHeight(20); 
                hpStyle.knobBefore = game.skin.newDrawable("white", Color.RED); 
                hpStyle.knobBefore.setMinHeight(20);

                // ADDED: Define how the Mana bars look (Blue fill, Gray background)
                manaStyle = new ProgressBar.ProgressBarStyle();
                manaStyle.background = game.skin.newDrawable("white", Color.DARK_GRAY);
                manaStyle.background.setMinHeight(20);
                manaStyle.knobBefore = game.skin.newDrawable("white", Color.BLUE);
                manaStyle.knobBefore.setMinHeight(20);

                combatAreaView = new CombatAreaView(game.skin, hpStyle, manaStyle, selectedTarget -> {
                    currentTarget = selectedTarget;
                    addCombatLog("Target selected: " + ((Player) selectedTarget).getName() + ". Press Basic Attack.");
                });
                combatAreaView.refresh(playerTeam.getMembers(), enemyTeam.getMembers(), selectedTarget -> {
                    currentTarget = selectedTarget;
                    addCombatLog("Target selected: " + ((Player) selectedTarget).getName() + ". Press Basic Attack.");
                });

                skillPanel = new SkillPanel(game.skin, skillIndex -> {
                    Entity currentActor = combatManager.getCurrentCombatant();
                    if (!(currentActor instanceof Player)) return;

                    Player player = (Player) currentActor;
                    
                    // Update handlers with current target
                    if (basicActionHandler != null) basicActionHandler.setCurrentTarget(currentTarget);
                    if (dpsActionHandler != null) dpsActionHandler.setCurrentTarget(currentTarget);
                    if (tankActionHandler != null) tankActionHandler.setCurrentTarget(currentTarget);
                    if (supportActionHandler != null) supportActionHandler.setCurrentTarget(currentTarget);
                    
                    if (skillIndex == 1) {
                        if (basicActionHandler != null) basicActionHandler.performBasicAttack();
                    } else if (skillIndex == 2) {
                        if (((PlayerClass) player.getPlayerClass()) == PlayerClass.DPS) {
                            if (dpsActionHandler != null) dpsActionHandler.performFireball();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.TANK) {
                            if (tankActionHandler != null) tankActionHandler.performHeavyAttack();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.SUPPORT) {
                            if (supportActionHandler != null) supportActionHandler.performSmite();
                        }
                    } else if (skillIndex == 3) {
                        if (((PlayerClass) player.getPlayerClass()) == PlayerClass.DPS) {
                            if (dpsActionHandler != null) dpsActionHandler.performMagicMissile();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.TANK) {
                            if (tankActionHandler != null) tankActionHandler.performCleave();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.SUPPORT) {
                            if (supportActionHandler != null) supportActionHandler.performMassHeal();
                        }
                    } else if (skillIndex == 4) {
                        if (((PlayerClass) player.getPlayerClass()) == PlayerClass.DPS) {
                            if (dpsActionHandler != null) dpsActionHandler.performRecover();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.TANK) {
                            if (tankActionHandler != null) tankActionHandler.performBlock();
                        } else if (((PlayerClass) player.getPlayerClass()) == PlayerClass.SUPPORT) {
                            if (supportActionHandler != null) supportActionHandler.performHeal();
                        }
                    } else {
                        addCombatLog("Skill " + skillIndex + " is not ready yet.");
                    }
                });

                combatLogBox = new CombatLogBox(game.skin, 360, 180, 6);

                currentTurnLabel = new Label("Current Turn: ", game.skin);
                currentTurnLabel.setFontScale(1.5f); // Make it bigger

                Table uiArea = new Table();
                uiArea.add(currentTurnLabel).colspan(2).center().padBottom(10).row();
                uiArea.add(skillPanel.getActor()).left().top().expandY().pad(10);
                uiArea.add(combatLogBox.getActor()).right().top().pad(10);

                root.add(combatAreaView.getRoot()).expand().center().row();
                root.add(uiArea).height(220).fillX().padBottom(20);

                uiInitialized = true;

                // Initialize action handlers
                basicActionHandler = new BasicActionHandler(combatManager, combatAreaView, combatLogBox, 
                    playerTeam, enemyTeam, this::clearTargetSelection, this::handleCharacterDeath);
                dpsActionHandler = new DpsActionHandler(combatManager, combatAreaView, combatLogBox,
                    playerTeam, enemyTeam, this::clearTargetSelection, this::handleCharacterDeath);
                tankActionHandler = new TankActionHandler(combatManager, combatAreaView, combatLogBox,
                    playerTeam, enemyTeam, this::clearTargetSelection, this::handleCharacterDeath);
                supportActionHandler = new SupportActionHandler(combatManager, combatAreaView, combatLogBox,
                    playerTeam, enemyTeam, this::clearTargetSelection, this::handleCharacterDeath);
            }
            hasBeenShownBefore = true;
        }
    }

    private void updatePlayerUI(Player player) {
        StatsComponent stats = player.getComponent(StatsComponent.class);
        if (stats == null) return;

        String unitID = "unit_" + player.getId();
        
        ProgressBar hpBar = stage.getRoot().findActor(unitID + "_hpBar");
        Label hpLabel = stage.getRoot().findActor(unitID + "_hpLabel");
        ProgressBar manaBar = stage.getRoot().findActor(unitID + "_manaBar");
        Label manaLabel = stage.getRoot().findActor(unitID + "_manaLabel");

        if (hpBar != null && hpLabel != null) {
            hpBar.setRange(0, stats.getMaxHP());
            hpBar.setValue(stats.getCurrentHP());
            hpLabel.setText(stats.getCurrentHP() + "/" + stats.getMaxHP());
        }

        if (manaBar != null && manaLabel != null) {
            manaBar.setRange(0, stats.getMaxMana());
            manaBar.setValue(stats.getCurrentMana());
            manaLabel.setText(stats.getCurrentMana() + "/" + stats.getMaxMana());
        }
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new SettingsScreen(game, this));
            return;
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1);

        for (Player p : playerTeam.getMembers()) {
            updatePlayerUI(p);
        }
        for (Player e : enemyTeam.getMembers()) {
            updatePlayerUI(e);
        }
        if (combatManager != null && combatManager.isCombatActive()) {
            Entity currentActor = combatManager.getCurrentCombatant();
            if (currentActor != lastActor) {
                lastActor = currentActor;
                if (currentActor != null && currentActor.getComponent(CombatantComponent.class).team == CombatantComponent.Team.ENEMY) {
                    performEnemyTurn(currentActor);
                    if (currentTurnLabel != null && currentActor instanceof Player) {
                        currentTurnLabel.setText("Current Turn: " + ((Player) currentActor).getName());
                    }
                } else {
                    addCombatLog("Player turn: choose a target and use Basic Attack.");
                    if (skillPanel != null && currentActor instanceof Player) {
                        skillPanel.setPlayer((Player) currentActor);
                    }
                    if (currentTurnLabel != null && currentActor instanceof Player) {
                        currentTurnLabel.setText("Current Turn: " + ((Player) currentActor).getName());
                    }
                }
            }
        }

        if (skillPanel != null) {
            skillPanel.setBasicAttackEnabled(isPlayerTurn() && combatManager.isCombatActive());
        }

        stage.act(delta);
        stage.draw();
    }

    private void initializeCombatSystems() {
        TargetingSystem targetingSystem = new TargetingSystem();
        CombatResolver combatResolver = new CombatResolver(
            targetingSystem,
            new AccuracySystem(),
            new DamageSystem(),
            new HealthSystem()
        );

        combatManager = new CombatManager(new TurnManager(), combatResolver);
        ArrayList<Entity> combatants = new ArrayList<>();

        for (Player player : playerTeam.getMembers()) {
            if (player.getComponent(CombatantComponent.class) == null) {
                player.addComponent(CombatantComponent.class, new CombatantComponent(CombatantComponent.Team.PLAYER));
            }
            combatants.add(player);
        }

        for (Player enemy : enemyTeam.getMembers()) {
            if (enemy.getComponent(CombatantComponent.class) == null) {
                enemy.addComponent(CombatantComponent.class, new CombatantComponent(CombatantComponent.Team.ENEMY));
            }
            combatants.add(enemy);
        }

        combatManager.startCombat(combatants);
        lastActor = null;
    }

    private boolean isPlayerTurn() {
        if (combatManager == null || !combatManager.isCombatActive()) {
            return false;
        }

        Entity current = combatManager.getCurrentCombatant();
        if (current == null) {
            return false;
        }

        CombatantComponent combatant = current.getComponent(CombatantComponent.class);
        return combatant != null && combatant.team == CombatantComponent.Team.PLAYER;
    }

    private void performEnemyTurn(Entity enemy) {
        Array<Player> candidates = new Array<>();
        for (Player player : playerTeam.getMembers()) {
            CombatantComponent combatant = player.getComponent(CombatantComponent.class);
            if (combatant != null && combatant.isAlive) {
                candidates.add(player);
            }
        }

        if (candidates.size == 0) {
            addCombatLog("All players are defeated.");
            return;
        }

        Player target = candidates.get(MathUtils.random(candidates.size - 1));
        CombatResult result = combatManager.performAction(new AttackAction(enemy, target));

        if (result == null) {
            addCombatLog("Enemy action failed.");
            return;
        }

        String enemyName = "Enemy";
        if (enemy instanceof Player) {
            enemyName = ((Player) enemy).getName();
        }
        String targetName = ((Player) target).getName();

        if (!result.didHit) {
            addCombatLog(enemyName + " tried to attack " + targetName + " but missed.");
        } else {
            addCombatLog(enemyName + " attacked " + targetName + " for " + result.damageDealt + " damage.");
        }

        if (result.targetDefeated) {
            addCombatLog(targetName + " was defeated by " + enemyName + ".");
            handleCharacterDeath(target);
        }

        clearTargetSelection();
        combatAreaView.refresh(playerTeam.getMembers(), enemyTeam.getMembers(), selectedTarget -> {
            currentTarget = selectedTarget;
            addCombatLog("Target selected: " + ((Player) selectedTarget).getName() + ". Press Basic Attack.");
        });
    }

    private void clearTargetSelection() {
        currentTarget = null;
        if (combatAreaView != null) {
            combatAreaView.clearSelection();
        }
    }

    private void handleCharacterDeath(Player defeatedCharacter) {
        if (defeatedCharacter == null || combatAreaView == null) {
            return;
        }
        
        // Remove the defeated character from the UI
        combatAreaView.removeCharacter(defeatedCharacter);
        
        // If the defeated character was the selected target, clear selection
        if (currentTarget == defeatedCharacter) {
            currentTarget = null;
        }
    }

    private void addCombatLog(String message) {
        if (combatLogBox != null) {
            combatLogBox.addEntry(message);
        }
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}
