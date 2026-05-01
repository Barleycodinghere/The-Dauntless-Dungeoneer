package com.teamfive.dauntlessdungeoneer;
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class MainMenuScreen implements Screen{

	final GameMain game;
	//Texture playButtonTexture;
	//Sprite playButtonSprite;
	//Rectangle playButtonBounds;
	Vector3 touchPos;
	
	private OrthographicCamera camera;
	
	
	public MainMenuScreen(final GameMain game) {
		
		this.game = game;
		
	//	playButtonTexture = new Texture("playbutton.png");
		
	//	playButtonSprite = new Sprite(playButtonTexture);
	//	playButtonSprite.setSize(2.5f, 1f);
	//	playButtonSprite.setPosition(3.2f, 3.2f);
		
	//	playButtonBounds = new Rectangle();
	//	playButtonBounds.setPosition(playButtonSprite.getX(), playButtonSprite.getY());
		
		touchPos = new Vector3();
		
		  camera = new OrthographicCamera();
		  
	
		 
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		

		//float playButtonWidth = playButtonSprite.getWidth();
		//float playButtonHeight = playButtonSprite.getHeight();
		//playButtonBounds.set(playButtonSprite.getX(),playButtonSprite.getY(),playButtonWidth,playButtonHeight);
		
		
		
		ScreenUtils.clear(Color.BLACK);
		
		
		game.viewport.apply();
		game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

		game.batch.begin();
		
		game.font.draw(game.batch, "Welcome to Dauntless Dungeoneer!!! ", 3.5f, 3);
		//game.batch.draw(playButtonSprite,playButtonSprite.getX(), playButtonSprite.getY());
		game.font.draw(game.batch, "Tap anywhere to begin!", 3.5f, 2.5f);
		
		game.batch.end();
		
		if (Gdx.input.isTouched()) {
			game.setScreen(new GameScreen(game));
			dispose();
		} 
		
		/* if(Gdx.input.justTouched()) {
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			
			if(playButtonBounds.contains(touchPos.x,touchPos.y)) {
				System.out.println("Play button pressed!!!!!");
				game.setScreen(new GameScreen(game));
				dispose();
				
			} 
		} */
		
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height, true);
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		game.batch.dispose();
		game.font.dispose();
		
		
		// TODO Auto-generated method stub
		
	}

}
