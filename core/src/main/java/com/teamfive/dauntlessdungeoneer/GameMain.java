package com.teamfive.dauntlessdungeoneer;

import com.badlogic.gdx.ApplicationListener; 
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {
	
	public SpriteBatch batch;
	public BitmapFont font;
	public FitViewport viewport;
	
    public void create() {
    	batch = new SpriteBatch();
    	viewport = new FitViewport(8,5);
    
    	
    	font = new BitmapFont();
    	font.setUseIntegerPositions(false);
    	font.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    	
    	this.setScreen(new MainMenuScreen(this));
        // Prepare your application here.
    }

    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        
       // viewport.update(width, height, true);

        // Resize your application here. The parameters represent the new window size.
    }

    public void render() {
    	super.render();
       
    }
    
    

    public void pause() {
        // Invoked when your application is paused.
    }

    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    public void dispose() {
        // Destroy application's resources here.
    }

	public void show() {
		// TODO Auto-generated method stub
		
	}

	public void render(float delta) {
		// TODO Auto-generated method stub
		
	}

	public void hide() {
		// TODO Auto-generated method stub
		
	}
	
}
