package com.teamfive.dauntlessdungeoneer.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

public class CombatAreaView {

    public interface TargetSelectionListener {
        void onTargetSelected(Player target);
    }

    private final Table root;
    private final Table playerTable;
    private final Table enemyTable;
    private final List<TextButton> enemyButtons = new ArrayList<>();
    private TextButton selectedEnemyButton;
    private Player selectedTarget;
    private final Skin skin;
    private final ProgressBar.ProgressBarStyle hpStyle;
    private final ProgressBar.ProgressBarStyle manaStyle;

    public CombatAreaView(Skin skin, ProgressBar.ProgressBarStyle hpStyle, ProgressBar.ProgressBarStyle manaStyle, TargetSelectionListener listener) {
        this.skin = skin;
        this.hpStyle = hpStyle;
        this.manaStyle = manaStyle;
        root = new Table();
        playerTable = new Table();
        enemyTable = new Table();

        root.add(playerTable).pad(20);
        root.add().width(150);
        root.add(enemyTable).pad(20);

        refresh(new ArrayList<Player>(), new ArrayList<Player>(), listener);
    }

    public Actor getRoot() {
        return root;
    }

    public void refresh(ArrayList<Player> playerTeam, ArrayList<Player> enemyTeam, TargetSelectionListener listener) {
        playerTable.clearChildren();
        enemyTable.clearChildren();
        enemyButtons.clear();
        selectedEnemyButton = null;
        selectedTarget = null;

        for (Player hero : playerTeam) {
            playerTable.add(createCharacterUnit(hero, false, listener)).pad(20);
        }

        enemyTable.row();
        for (Player enemy : enemyTeam) {
            enemyTable.add(createCharacterUnit(enemy, true, listener)).pad(20);
        }
    }

    public Player getSelectedTarget() {
        return selectedTarget;
    }

    public void clearSelection() {
        selectedTarget = null;
        if (selectedEnemyButton != null) {
            selectedEnemyButton.setColor(Color.WHITE);
            selectedEnemyButton = null;
        }
    }

    public void removeCharacter(Player player) {
        String unitID = "unit_" + player.getId();
        Actor unitGroup = root.findActor(unitID);

        if (unitGroup == null) {
            unitGroup = enemyTable.findActor(unitID);
        }
        
        if (unitGroup != null) {
            unitGroup.remove();
        }

        
        // If the removed character was the selected target, clear selection
        if (selectedTarget == player) {
            clearSelection();
        }
    }

    private Table createCharacterUnit(final Player player, boolean isEnemy, TargetSelectionListener listener) {
        Table unitGroup = new Table();
        String unitID = "unit_" + player.getId();
        unitGroup.setName(unitID);

        Image image = new Image(player.getSprite());

        String labelText = isEnemy ? "ENEMY\n" + player.getName() : player.getName();
        final TextButton btn = new TextButton(labelText, skin);

        ProgressBar hpBar = new ProgressBar(0, 100, 1, false, hpStyle);
        hpBar.setName(unitID + "_hpBar");
        Label hpLabel = new Label("0/0", skin);
        hpLabel.setName(unitID + "_hpLabel");
        hpLabel.setAlignment(Align.center);

        Stack hpStack = new Stack();
        hpStack.add(hpBar);
        hpStack.add(hpLabel);

        ProgressBar manaBar = new ProgressBar(0, 100, 1, false, manaStyle);
        manaBar.setName(unitID + "_manaBar");
        Label manaLabel = new Label("0/0", skin);
        manaLabel.setName(unitID + "_manaLabel");
        manaLabel.setAlignment(Align.center);

        Stack manaStack = new Stack();
        manaStack.add(manaBar);
        manaStack.add(manaLabel);

        unitGroup.add(image).size(200, 200).padBottom(10).row();
        unitGroup.add(btn).size(140, 45).row();
        unitGroup.add(hpStack).width(160).height(20).padTop(10).row();
        unitGroup.add(manaStack).width(160).height(20).padTop(5).row();

        if (isEnemy) {
            btn.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectEnemy(btn, player);
                    listener.onTargetSelected(player);
                }
            });
            enemyButtons.add(btn);
        }

        return unitGroup;
    }

    private void selectEnemy(TextButton btn, Player player) {
        if (selectedEnemyButton != null) {
            selectedEnemyButton.setColor(Color.WHITE);
        }
        selectedEnemyButton = btn;
        selectedTarget = player;
        selectedEnemyButton.setColor(Color.YELLOW);
    }
}
