package com.teamfive.dauntlessdungeoneer;
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {

		final Main game;
		
		Texture backgroundTexture;
		Texture DPSTexture;
		Texture TankTexture;
		Texture SupportTexture;
		
		SpriteBatch spriteBatch;
		FitViewport viewport;
		
		Sprite DPSSprite;
		Sprite TankSprite;
		Sprite SupportSprite;
		
		Vector2 touchPos;
		
		private OrthographicCamera camera;
		


	public GameScreen(final Main game) {
		this.game = game;
		
		//load images for background, DPS, tank, and support
		backgroundTexture = new Texture("background.png");
		DPSTexture = new Texture("dps.png");
		TankTexture = new Texture("support.png");
		SupportTexture = new Texture("tank.png");
		
		spriteBatch = new SpriteBatch();
		
		DPSSprite = new Sprite(DPSTexture);
		DPSSprite.setSize(1, 1);
		DPSSprite.setPosition(2, 1.9f);
		
		
		TankSprite = new Sprite(TankTexture);
		TankSprite.setSize(1, 1);
		TankSprite.setPosition(1.2f, 1.9f);
		
		SupportSprite = new Sprite(SupportTexture);
		SupportSprite.setSize(1, 1);
		SupportSprite.setPosition(2.8f, 1.9f);
		
		touchPos = new Vector2();
		
	    camera = new OrthographicCamera();
	    viewport = new FitViewport(8, 5, camera);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void render(float delta) {
		input();
		logic();
		draw();

	}
	
	private void input() {
		float speed = 4f;
		//gonna add float delta in case we end up needing it can prob delete later if we dont
		float delta = Gdx.graphics.getDeltaTime();
		
		if(Gdx.input.isTouched()) {
			touchPos.set(Gdx.input.getX(),Gdx.input.getY());
			game.viewport.unproject(touchPos);
			DPSSprite.setCenterX(touchPos.x);
		}
		
	}
	
	private void logic() {
		
		
	}
	
	private void draw() {
		ScreenUtils.clear(Color.BLACK);
		game.viewport.apply();
		spriteBatch.setProjectionMatrix(game.viewport.getCamera().combined);
		spriteBatch.begin();
		
		float worldWidth = viewport.getWorldWidth();
		float worldHeight = viewport.getWorldHeight();
		
		
		spriteBatch.draw(backgroundTexture, 0, 0, worldWidth, worldHeight);
		
		
		DPSSprite.draw(spriteBatch);
		TankSprite.draw(spriteBatch);
		SupportSprite.draw(spriteBatch);
		
		spriteBatch.end();
		
		
	}

	@Override
	public void resize(int width, int height) {
		game.viewport.update(width,height,true);

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
		backgroundTexture.dispose();
		DPSTexture.dispose();
		TankTexture.dispose();
		SupportTexture.dispose();
		

	}

}
