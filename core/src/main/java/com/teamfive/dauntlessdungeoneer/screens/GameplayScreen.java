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

public class GameplayScreen implements Screen {
    private final GameMain game;
    private Stage stage;
    private Table combatArea;
    private Table root;

    private Team playerTeam;
    private Team enemyTeam;
    private Entity currentTarget;
    private Entity lastActor;
    private CombatManager combatManager;
    private Label statusLabel;
    private TextButton attackButton;

    private ProgressBar.ProgressBarStyle hpStyle;
    private ProgressBar.ProgressBarStyle manaStyle;

    public GameplayScreen(GameMain game, Team playerTeam, Team enemyTeam) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        this.playerTeam = playerTeam;
        this.enemyTeam = enemyTeam;
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

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
        
        combatArea = new Table();
        refreshCombatArea();

        Table uiArea = new Table();
        TextButton.TextButtonStyle abilityStyle = new TextButton.TextButtonStyle(game.skin.get(TextButton.TextButtonStyle.class));
        abilityStyle.up = game.skin.newDrawable("white", Color.valueOf("#787276"));
        abilityStyle.fontColor = Color.WHITE;

        for (int i = 1; i <= 4; i++) {
            final int skillIndex = i;
            TextButton skillBtn = new TextButton(skillIndex == 1 ? "Basic Attack" : "Skill " + skillIndex, abilityStyle);

            if (skillIndex == 1) {
                attackButton = skillBtn;
            } else {
                skillBtn.setDisabled(true); // Only basic attack is active for now
            }

            skillBtn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (skillIndex == 1) {
                        tryPerformPlayerAttack();
                    } else {
                        setStatus("Skill " + skillIndex + " is not ready yet.");
                    }
                }
            });
            uiArea.add(skillBtn).width(120).height(50).pad(20);
        }

        statusLabel = new Label("Choose an enemy target and press Basic Attack.", game.skin);
        statusLabel.setAlignment(Align.center);

        root.add(combatArea).expand().center().row();
        root.add(uiArea).height(150).fillX().padBottom(10).row();
        root.add(statusLabel).colspan(4).fillX().padBottom(20);
    }

    private void refreshCombatArea() {
        combatArea.clearChildren();

        for (Player hero : playerTeam.getMembers()) {
            combatArea.add(createCharacterUnit(hero, false)).pad(20);
        }

        combatArea.add().width(100);

        for (Player enemy : enemyTeam.getMembers()) {
            combatArea.add(createCharacterUnit(enemy, true)).pad(20);
        }
    }

    // ADDED: Helper method that builds the "Unit" (Button + 2 Bars)
    private Table createCharacterUnit(final Player player, boolean isEnemy) {
        Table unitGroup = new Table();
        String unitID = "unit_" + player.getId();

        // 1. Character Button (Displays "DPS", "TANK", etc.)
        String label = isEnemy ? "ENEMY\n" + player.getPlayerClass() : player.getPlayerClass().toString();
        final TextButton btn = new TextButton(label, game.skin);

        // 2. Health Bar
        ProgressBar hpBar = new ProgressBar(0, 100, 1, false, hpStyle);
        hpBar.setName(unitID + "_hpBar");
        Label hpLabel = new Label("0/0", game.skin);
        hpLabel.setName(unitID + "_hpLabel");
        hpLabel.setAlignment(Align.center);

        Stack hpStack = new Stack();
        hpStack.add(hpBar);
        hpStack.add(hpLabel);

        // 3. Mana Bar Stack (Height increased to 18)
        ProgressBar manaBar = new ProgressBar(0, 100, 1, false, manaStyle);
        manaBar.setName(unitID + "_manaBar");
        Label manaLabel = new Label("0/0", game.skin);
        manaLabel.setName(unitID + "_manaLabel");
        manaLabel.setAlignment(Align.center);

        Stack manaStack = new Stack(); 
        manaStack.add(manaBar);
        manaStack.add(manaLabel);

        // Layout: Stack them on top of each other
        unitGroup.add(btn).size(100, 100).row();
        unitGroup.add(hpStack).width(120).height(20).padTop(10).row(); 
        unitGroup.add(manaStack).width(120).height(20).padTop(5).row();

        // Only add selection logic to enemy buttons
        if (isEnemy) {
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    currentTarget = player; // Set this enemy as target
                    for (Actor unit : combatArea.getChildren()) {
                        if (unit instanceof Table) {
                            Actor potentialBtn = ((Table) unit).getChildren().first();
                            potentialBtn.setColor(Color.WHITE);
                        }
                    }
                    btn.setColor(Color.YELLOW); // Highlight selected enemy Yellow
                    Player targetPlayer = (Player) currentTarget;
                    setStatus("Target selected: " + targetPlayer.getPlayerClass() + ". Press Basic Attack.");
                    System.out.println("Targeted: " + targetPlayer.getPlayerClass());
                }
            });
        }

        return unitGroup;
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
                } else {
                    setStatus("Player turn: choose a target and use Basic Attack.");
                }
            }
        }

        if (attackButton != null) {
            attackButton.setDisabled(!isPlayerTurn() || !combatManager.isCombatActive());
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

    private void tryPerformPlayerAttack() {
        if (!combatManager.isCombatActive()) {
            setStatus("Combat has ended.");
            return;
        }

        if (!isPlayerTurn()) {
            setStatus("It is not your turn yet.");
            return;
        }

        if (currentTarget == null) {
            setStatus("Select a target before attacking.");
            return;
        }

        Entity attacker = combatManager.getCurrentCombatant();
        if (attacker == null) {
            setStatus("No attacker available.");
            return;
        }

        CombatResult result = combatManager.performAction(new AttackAction(attacker, currentTarget));

        if (result == null) {
            setStatus("Could not perform attack.");
            return;
        }

        if (!result.didHit) {
            setStatus("Basic Attack missed " + ((Player) result.defender).getPlayerClass() + "!");
        } else {
            setStatus("Basic Attack dealt " + result.damageDealt + " damage to " + ((Player) result.defender).getPlayerClass() + ".");
        }

        if (result.targetDefeated) {
            setStatus(((Player) result.defender).getPlayerClass() + " was defeated!");
        }

        clearTargetSelection();
        refreshCombatArea();
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
            setStatus("All players are defeated.");
            return;
        }

        Player target = candidates.get(MathUtils.random(candidates.size - 1));
        CombatResult result = combatManager.performAction(new AttackAction(enemy, target));

        if (result == null) {
            setStatus("Enemy action failed.");
            return;
        }

        if (!result.didHit) {
            setStatus("Enemy missed " + target.getPlayerClass() + "!");
        } else {
            setStatus("Enemy hit " + target.getPlayerClass() + " for " + result.damageDealt + " damage.");
        }

        if (result.targetDefeated) {
            setStatus(target.getPlayerClass() + " was defeated by the enemy.");
        }

        clearTargetSelection();
        refreshCombatArea();
    }

    private void clearTargetSelection() {
        currentTarget = null;
        for (Actor unit : combatArea.getChildren()) {
            if (unit instanceof Table) {
                Actor potentialBtn = ((Table) unit).getChildren().first();
                potentialBtn.setColor(Color.WHITE);
            }
        }
    }

    private void setStatus(String message) {
        if (statusLabel != null) {
            statusLabel.setText(message);
        }
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}
