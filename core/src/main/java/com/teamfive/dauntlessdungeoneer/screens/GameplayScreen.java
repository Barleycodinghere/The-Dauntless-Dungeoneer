package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.teamfive.dauntlessdungeoneer.screens.SettingsScreen;


public class GameplayScreen implements Screen {
    private final GameMain game;
    private Stage stage;

    public GameplayScreen(GameMain game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        // ROOT TABLE (Split screen into Top and Bottom)
        Table root = new Table();
        root.setFillParent(true);
        stage.addActor(root);

        // --- TOP AREA: Combatants ---
        Table combatArea = new Table();
        // Add 4 Heroes on the Left
        Table playerSide = new Table();
        for (int i = 1; i <= 4; i++) {
            // Placeholder: Replace "START ADVENTURE" style with a Character Sprite/Label later
            playerSide.add(new TextButton("HERO " + i, game.skin)).pad(10).row();
        }

        // Add 4 Enemies on the Right
        Table enemySide = new Table();
        for (int i = 1; i <= 4; i++) {
            enemySide.add(new TextButton("ENEMY " + i, game.skin)).pad(10).row();
        }

        combatArea.add(playerSide).expand().left().padLeft(50);
        combatArea.add(enemySide).expand().right().padRight(50);

        // --- BOTTOM AREA: UI ---
        Table uiArea = new Table();
        // Use the gray style you liked for abilities
        TextButton.TextButtonStyle abilityStyle = new TextButton.TextButtonStyle(game.skin.get(TextButton.TextButtonStyle.class));
        abilityStyle.up = game.skin.newDrawable("white", Color.valueOf("#787276"));
        abilityStyle.fontColor = Color.WHITE;

        for (int i = 1; i <= 4; i++) {
            uiArea.add(new TextButton("SKILL " + i, abilityStyle)).width(120).height(50).pad(5);
        }

        // Assemble the Root Table
        root.add(combatArea).expand().fill().row(); // Top 2/3 roughly
        root.add(uiArea).height(150).fillX().padBottom(20); // Bottom 1/3
    }

    @Override
    public void render(float delta) {

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            game.setScreen(new SettingsScreen(game, this));
            return; // Stop rendering the rest of this frame since we are switching screens
        }

        ScreenUtils.clear(0.15f, 0.15f, 0.15f, 1); // Dark dungeon vibe
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
