package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.Game;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 * Uses a libGDX {@link Game} to support screen-based architecture.
 */
public class GameMain extends Game {

    @Override
    public void create() {
        setScreen(new com.teamfive.dauntlessdungeoneer.screens.MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        if (getScreen() != null) {
            getScreen().dispose();
        }
        super.dispose();
    }
}
