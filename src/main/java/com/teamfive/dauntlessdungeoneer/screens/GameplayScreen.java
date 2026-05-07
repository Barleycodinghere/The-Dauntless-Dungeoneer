package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table; 
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.teamfive.dauntlessdungeoneer.screens.SettingsScreen;
import com.teamfive.dauntlessdungeoneer.entities.*;
import com.teamfive.dauntlessdungeoneer.components.*;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import com.teamfive.dauntlessdungeoneer.combat.actions.AttackAction;
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
import com.teamfive.dauntlessdungeoneer.combat.abilities.Ability;
import com.teamfive.dauntlessdungeoneer.combat.abilities.AbilityLoadout;
import com.teamfive.dauntlessdungeoneer.combat.systems.MonsterAISystem;




public class GameplayScreen implements Screen {
    private final GameMain game;
    private final Stage stage;
    private CombatAreaView combatAreaView;
    private Table root;

    private final Team playerTeam;
    private final Team enemyTeam;
    private MonsterAISystem monsterAI;
    private Entity currentTarget;
    private Entity lastActor;
    private CombatManager combatManager;
    private CombatLogBox combatLogBox;
    private SkillPanel skillPanel;

    private ProgressBar.ProgressBarStyle hpStyle;
    private ProgressBar.ProgressBarStyle manaStyle;
    private Label currentTurnLabel;
    private boolean uiInitialized = false;

    public GameplayScreen(GameMain game, Team playerTeam, Team enemyTeam) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

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
                addCombatLog("Target selected: " + ((Player) selectedTarget).getName());
            });
            combatAreaView.refresh(playerTeam.getMembers(), enemyTeam.getMembers(), selectedTarget -> {
                currentTarget = selectedTarget;
                addCombatLog("Target selected: " + ((Player) selectedTarget).getName());
            });

            skillPanel = new SkillPanel(game.skin, skillIndex -> {

                Entity currentActor = combatManager.getCurrentCombatant();
                if (!(currentActor instanceof Player)) return;
                Player player = (Player) currentActor;

                StatsComponent stats = player.getComponent(StatsComponent.class);
                if (stats == null) return;

                if (currentTarget == null) {
                    addCombatLog("Select a target first.");
                    return;
                }

                int index = skillIndex - 1;

                Ability ability = player.getAbilityLoadout().get(index);
                System.out.println("Clicked index: " + skillIndex + " | Ability: " + ability.getName() + " | Cost: " + ability.getManaCost());

                if (ability == null) {
                    addCombatLog("No ability in slot " + skillIndex);
                    return;
                }
                if (stats.getCurrentMana() < ability.getManaCost()) {
                    addCombatLog("Not enough mana for " + ability.getName());
                    return;
                }

                CombatResult result = ability.execute(currentActor, currentTarget, combatManager);

                // DEDUCT MANA
                stats.useMana(ability.getManaCost());

                addCombatLog(player.getName() + " used " + ability.getName());

                if (result != null && result.targetDefeated) {
                    handleCharacterDeath((Player) currentTarget);
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
        }
    }

    private void updatePlayerUI(Player player) {
        StatsComponent stats = player.getComponent(StatsComponent.class);
        if (stats == null || !stats.isAlive()) return;

        String unitID = "unit_" + player.getId();
        Group unitContainer = stage.getRoot().findActor(unitID);
        if (unitContainer == null) return; 
        
        ProgressBar hpBar = unitContainer.findActor(unitID + "_hpBar");
        Label hpLabel = unitContainer.findActor(unitID + "_hpLabel");
        ProgressBar manaBar = unitContainer.findActor(unitID + "_manaBar");
        Label manaLabel = unitContainer.findActor(unitID + "_manaLabel");

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
                if (currentActor != null) {
                        CombatantComponent cc = currentActor.getComponent(CombatantComponent.class);
                    if (cc != null && cc.team == CombatantComponent.Team.ENEMY) {
                        monsterAI.executeTurn(currentActor);
                        if (currentTurnLabel != null && currentActor instanceof Player) {
                            currentTurnLabel.setText("Current Turn: " + ((Player) currentActor).getName());
                        }
                    } else {
                        addCombatLog("Player turn: choose a target.");
                        if (skillPanel != null && currentActor instanceof Player) {
                            skillPanel.setPlayer((Player) currentActor);
                        }
                        if (currentTurnLabel != null && currentActor instanceof Player) {
                            currentTurnLabel.setText("Current Turn: " + ((Player) currentActor).getName());
                        }
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

        monsterAI = new MonsterAISystem(
                            combatManager, 
                            playerTeam, 
                            this::addCombatLog,
                            this::handleCharacterDeath 
        );


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

        if (combatManager != null) {
            combatManager.handleDeath(defeatedCharacter);
        }
        
        // Remove the defeated character from the UI
        combatAreaView.removeCharacter(defeatedCharacter);

        playerTeam.getMembers().remove(defeatedCharacter);
        enemyTeam.getMembers().remove(defeatedCharacter);
        
        // If the defeated character was the selected target, clear selection
        if (currentTarget == defeatedCharacter) {
            clearTargetSelection();
        }

        if (enemyTeam.getMembers().isEmpty()) {
            addCombatLog("VICTORY! All enemies defeated.");
            game.setScreen(new MainMenuScreen(game));
        }
        else if (playerTeam.getMembers().isEmpty()) {
            addCombatLog("GAME OVER: Your party was wiped out!");
            game.setScreen(new MainMenuScreen(game));
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
