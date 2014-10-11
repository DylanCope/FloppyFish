package com.cope.flight.screens;

import java.io.IOException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cope.flight.Background;
import com.cope.flight.GameLogic;
import com.cope.flight.MainClass;
import com.cope.flight.data.Data;
import com.cope.flight.data.Event;
import com.cope.flight.entities.ImageButton;
import com.purplebrain.adbuddiz.sdk.AdBuddiz;

public class PlayScreen implements Screen{
	
	MainClass main;
	GameLogic logic;
	ImageButton pauseButton;
	SpriteBatch batch;
	public static Background bg;
	
	public PlayScreen(MainClass main, Background bg){
		if (!Gdx.files.local("data.dat").exists()){
			Data data = new Data();
			try {
				Data.saveData(data);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		PlayScreen.bg = bg;
		this.main = main;
		logic = new GameLogic();
		
		Sprite pauseSprite = new Sprite(new Texture("pauseButton.png"));
		pauseSprite.setSize(Gdx.graphics.getWidth() / 8, Gdx.graphics.getWidth() / 8);
		pauseSprite.setPosition(
				0.4f * pauseSprite.getWidth(), 
				Gdx.graphics.getHeight() - 1.4f * pauseSprite.getHeight());
		
		pauseButton = new ImageButton(pauseSprite, 
				new Event(){
					public void onEvent(){
						if (!logic.isPaused()) {
							logic.pause();
						}else{
							logic.unpause();
						}
					}
				});
		
		batch = new SpriteBatch();
	}
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.85f, 0.85f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		bg.render(delta);
		logic.update(delta);
		pauseButton.update(batch);
	}

	@Override
	public void resize(int width, int height) {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {
		logic.pause();
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
