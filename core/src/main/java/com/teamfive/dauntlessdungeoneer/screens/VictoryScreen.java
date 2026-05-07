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
import com.teamfive.dauntlessdungeoneer.screens.MainMenuScreen;

public class VictoryScreen implements Screen {
    private final GameMain game;
    private final Stage stage;

    public VictoryScreen(GameMain game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        // Essential: Allow the stage to receive button clicks
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label victoryLabel = new Label("VICTORY!", game.skin);
        victoryLabel.setFontScale(3.0f);

        Label subLabel = new Label("The dungeon has been cleared!", game.skin);

        // ======================
        // MAIN MENU BUTTON
        // ======================
        TextButton menuButton = new TextButton("Return to Menu", game.skin);
        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Switch back to the main menu screen
                game.setScreen(new MainMenuScreen(game));
            }
        });

        table.add(victoryLabel).padBottom(20).row();
        table.add(subLabel).padBottom(50).row();
        table.add(menuButton).width(300).height(60);
    }

    @Override
    public void render(float delta) {
        // Dark green "Victory" background
        ScreenUtils.clear(0.1f, 0.2f, 0.1f, 1);
        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void dispose() { stage.dispose(); }
    @Override public void pause() {}
    @Override public void resume() {}
}
