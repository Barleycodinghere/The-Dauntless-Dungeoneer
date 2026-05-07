package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Color;
import com.teamfive.dauntlessdungeoneer.screens.SettingsScreen;
import com.teamfive.dauntlessdungeoneer.entities.Team;
import com.teamfive.dauntlessdungeoneer.entities.Player;
import com.teamfive.dauntlessdungeoneer.components.PlayerClass;
import com.teamfive.dauntlessdungeoneer.components.MonsterClass;
import com.teamfive.dauntlessdungeoneer.screens.TeamSelectScreen;

public class MainMenuScreen implements Screen {
    private final GameMain game;
    private Stage stage;

    public MainMenuScreen(GameMain game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // Set the input processor so the buttons work
        Gdx.input.setInputProcessor(stage);

        Color normalGray = Color.valueOf("#787276"); // Your specific gray
        Color hoverGray = Color.valueOf("#918b8e");  // A slightly lighter gray for hover
        Color downGray = Color.valueOf("#5e595c");   // A darker gray for clicking

        TextButton.TextButtonStyle playStyle = new TextButton.TextButtonStyle(game.skin.get(TextButton.TextButtonStyle.class));
        playStyle.up = game.skin.newDrawable("white", normalGray);
        playStyle.over = game.skin.newDrawable("white", hoverGray);
        playStyle.down = game.skin.newDrawable("white", downGray);
        playStyle.fontColor = Color.WHITE;


        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Access the skin from our GameMain class
        TextButton playButton = new TextButton("START ADVENTURE", playStyle);
        TextButton settingsButton = new TextButton("SETTINGS", playStyle);
        TextButton exitButton = new TextButton("EXIT GAME", playStyle);

        playButton.getLabel().getStyle().fontColor = Color.WHITE;

        // Add listeners
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //creates default team
                //Team playerTeam = Team.createDefaultPlayerTeam();
                game.setScreen(new TeamSelectScreen(game));// This switches screens!
            }
        });

        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            // Pass 'this' so SettingsScreen knows to return here
            game.setScreen(new SettingsScreen(game, MainMenuScreen.this)); 
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        // Layout
        table.add(playButton).width(250).height(60).padBottom(10);
        table.row();
        table.add(settingsButton).width(250).height(60).padBottom(10);
        table.row();
        table.add(exitButton).width(250).height(60).padBottom(10);
        
    }

    @Override
    public void render(float delta) {
        // delta is the time between frames
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void pause() {}
    @Override public void resume() {}
    
    @Override
    public void hide() {
        // Stop taking input when this screen isn't visible
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
