package com.teamfive.dauntlessdungeoneer.screens;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.teamfive.dauntlessdungeoneer.GameMain;
import com.teamfive.dauntlessdungeoneer.GameplayScreen;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;
import com.teamfive.dauntlessdungeoneer.components.MonsterClass;

public class TeamSelectScreen implements Screen {

    private final GameMain game;
    private final Stage stage;

    private final Team playerTeam = new Team();

    private final Label teamLabel;

    public TeamSelectScreen(GameMain game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());

        teamLabel = new Label("Team: (empty)", game.skin);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        Label title = new Label("Build Your Party (1–4 Heroes)", game.skin);

        // ======================
        // CHARACTER BUTTONS
        // ======================
        TextButton tankBtn = new TextButton("Add TANK", game.skin);
        TextButton dpsBtn = new TextButton("Add DPS", game.skin);
        TextButton supportBtn = new TextButton("Add SUPPORT", game.skin);

        TextButton removeBtn = new TextButton("REMOVE LAST", game.skin);
        TextButton startBtn = new TextButton("START ADVENTURE", game.skin);

        // ======================
        // ADD TANK
        // ======================
        tankBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addToTeam(new Player(PlayerClass.TANK));
            }
        });

        // ======================
        // ADD DPS
        // ======================
        dpsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addToTeam(new Player(PlayerClass.DPS));
            }
        });

        // ======================
        // ADD SUPPORT
        // ======================
        supportBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                addToTeam(new Player(PlayerClass.SUPPORT));
            }
        });

        // ======================
        // REMOVE LAST
        // ======================
        removeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (playerTeam.getMembers().size() > 0) {
                    playerTeam.getMembers().remove(playerTeam.getMembers().size() - 1);
                    updateLabel();
                }
            }
        });

        // ======================
        // START GAME
        // ======================
        startBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                if (playerTeam.getMembers().size() < 1) {
                    return; // must have at least 1 player
                }

                Team enemyTeam = new Team();
                enemyTeam.addMember(new Player(MonsterClass.GOBLIN));
                enemyTeam.addMember(new Player(MonsterClass.DOG));

                game.setScreen(new GameplayScreen(game, playerTeam, enemyTeam));
            }
        });

        // ======================
        // LAYOUT
        // ======================
        root.add(title).padBottom(20).row();

        root.add(tankBtn).width(200).height(50).pad(5).row();
        root.add(dpsBtn).width(200).height(50).pad(5).row();
        root.add(supportBtn).width(200).height(50).pad(5).row();

        root.add(removeBtn).width(200).height(50).padTop(15).row();

        root.add(teamLabel).padTop(20).row();

        root.add(startBtn).width(250).height(60).padTop(30);
    }

    private void addToTeam(Player player) {
        if (playerTeam.getMembers().size() >= 4) return;

        playerTeam.addMember(player);
        updateLabel();
    }

    private void updateLabel() {
        StringBuilder sb = new StringBuilder("Team: ");

        for (Player p : playerTeam.getMembers()) {
            sb.append(p.getPlayerClass()).append(" ");
        }

        teamLabel.setText(sb.toString().trim());
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) { stage.getViewport().update(w, h, true); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void dispose() { stage.dispose(); }
}