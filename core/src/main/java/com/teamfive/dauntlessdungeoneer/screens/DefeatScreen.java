package com.teamfive.dauntlessdungeoneer.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.teamfive.dauntlessdungeoneer.GameMain;

public class DefeatScreen implements Screen {
    private final GameMain game;
    private final Stage stage;

    public DefeatScreen(GameMain game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Defeat Message
        Label defeatLabel = new Label("DEFEATED", game.skin);
        defeatLabel.setFontScale(3.0f);
        defeatLabel.getStyle().fontColor = com.badlogic.gdx.graphics.Color.RED;

        Label subLabel = new Label("Your party has fallen in the dungeon...", game.skin);

        // Buttons
        TextButton menuButton = new TextButton("Return to Menu", game.skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(defeatLabel).padBottom(20).row();
        table.add(subLabel).padBottom(50).row();
        table.add(menuButton).width(300).height(60);
    }

    @Override
    public void render(float delta) {
        // Dark red/black "Death" background
        ScreenUtils.clear(0.15f, 0.05f, 0.05f, 1); 
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}
