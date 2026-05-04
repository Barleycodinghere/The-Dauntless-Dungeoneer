package com.teamfive.dauntlessdungeoneer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.teamfive.dauntlessdungeoneer.GameMain;

public class SettingsScreen implements Screen {
    private final GameMain game;
    private final Screen previousScreen;
    private Stage stage;

    public SettingsScreen(GameMain game, Screen previousScreen) {
        this.game = game;
        this.previousScreen = previousScreen;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(game.skin.get(TextButton.TextButtonStyle.class));
        style.up = game.skin.newDrawable("white", Color.valueOf("#787276"));
        style.fontColor = Color.WHITE;

        TextButton resumeButton = new TextButton("Resume", style);
        TextButton fullScreenButton = new TextButton("Fullscreen", style);
        TextButton windowedFullScreenButton = new TextButton("Windowed Mode", style);
        TextButton exitButton = new TextButton("Exit Game", style);

        resumeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(previousScreen);
            }
        });

        fullScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            if (Gdx.graphics.isFullscreen()) {
                // Switch to a default windowed resolution
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                // Switch to the monitor's current display mode (native resolution)
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
                }
            }
        });

        windowedFullScreenButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                int screenWidth = Gdx.graphics.getDisplayMode().width;
                int screenHeight = Gdx.graphics.getDisplayMode().height;

                if (Gdx.graphics.isFullscreen()) {
                    Gdx.graphics.setWindowedMode(1280, 720); 
                }

                Gdx.graphics.setWindowedMode(screenWidth, screenHeight - 70);
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        table.add(resumeButton).width(300).height(60).pad(10).row();
        table.add(fullScreenButton).width(300).height(60).pad(10).row();
        table.add(windowedFullScreenButton).width(300).height(60).pad(10).row();
        table.add(exitButton).width(250).height(60).padTop(50);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.1f, 0.1f, 0.1f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}
