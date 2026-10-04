package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.teamfive.dauntlessdungeoneer.screens.MainMenuScreen;
import com.teamfive.dauntlessdungeoneer.screens.GameplayScreen;

public class GameMain extends Game {
    // We keep the skin here so all screens can use it without reloading it
    public Skin skin;

    @Override
    public void create() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Switch to the Main Menu
        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.F11)) {
            if (Gdx.graphics.isFullscreen()) {
                Gdx.graphics.setWindowedMode(1280, 720);
            } else {
                Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
            }
        }

        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.ESCAPE)) {
            if (this.getScreen() instanceof com.teamfive.dauntlessdungeoneer.screens.TeamSelectScreen) {
                // Store the current screen, switch, then clean up the old one
                com.badlogic.gdx.Screen oldScreen = this.getScreen();
                this.setScreen(new MainMenuScreen(this));
                if (oldScreen != null) oldScreen.dispose();
            }
        }
        // This is crucial! It tells the current screen to render
        super.render();
    }

    @Override
    public void dispose() {
        // Clean up the skin when the game finally closes
        if (skin != null) skin.dispose();
        if (getScreen() != null) getScreen().dispose();
    }
}
