package com.cope.flight.screens;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Elastic;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.cope.flight.Background;
import com.cope.flight.MainClass;
import com.cope.flight.tween.SpriteAccessor;
import com.cope.flight.tween.TextButtonAccessor;

public class MainMenu implements Screen {
	
	MainClass main;
	Background bg;
	
	Stage stage;
	TextButton play, options, stats;
	TextButtonStyle style;
	BitmapFont font;
	TweenManager tweenManager;
	Sprite logo;
	SpriteBatch batch;
	
	public MainMenu(MainClass main, Background bg){
		this.main = main;
		this.bg = bg;
		bg.setMoving(false);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.85f, 0.85f, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		
		
		bg.render(delta);
		stage.draw();
		stage.act();
		tweenManager.update(delta);
		
		batch.begin();
		logo.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		stage = new Stage();
		
		font = new BitmapFont(Gdx.files.internal("square.fnt"));
		style = new TextButtonStyle();
		style.font = font;
		
		float widthScaler = 0.8f * Gdx.graphics.getWidth() / 1200f;
		float heightScaler = 0.8f * Gdx.graphics.getHeight() / 2000f;
		
		font.setScale(font.getScaleY() * widthScaler, font.getScaleX() * heightScaler);
		
		play = new TextButton("PLAY", style);
		options = new TextButton("Options", style);
		stats = new TextButton("Stats", style);
		
		play.setPosition((Gdx.graphics.getWidth() - play.getWidth()) / 2, - play.getHeight());
		stats.setPosition((Gdx.graphics.getWidth() - stats.getWidth()) / 2,  play.getY());
		options.setPosition((Gdx.graphics.getWidth() - options.getWidth()) / 2,  play.getY());
		
		play.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int playerStats) {
				close("PLAYSCREEN");
				return false;
			}
			
		});
		stats.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int playerStats) {
				close("STATS");
				return false;
			}
			
		});
		options.addListener(new InputListener(){

			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int playerStats) {
				close("OPTIONS");
				return false;
			}
				
		});
		
		stage.addActor(play);
		stage.addActor(stats);
		stage.addActor(options);
		
		Gdx.input.setInputProcessor(stage);
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(TextButton.class, new TextButtonAccessor());
		
		Tween.to(play, TextButtonAccessor.PosY, 3f)
			.delay(2)
			.target(Gdx.graphics.getHeight() / 2 - stats.getHeight())
			.ease(Elastic.OUT)
			.start(tweenManager);
		
//		Tween.to(stats, TextButtonAccessor.PosY, 3.2f)
//			.delay(2)
//			.target(6 * Gdx.graphics.getHeight() / 10 - 2.5f * stats.getHeight())
//			.ease(Elastic.OUT)
//			.start(tweenManager);
//		
//		Tween.to(options, TextButtonAccessor.PosY, 3.4f)
//			.delay(2)
//			.target(6 * Gdx.graphics.getHeight() / 10 - 4 * stats.getHeight())
//			.ease(Elastic.OUT)
//			.start(tweenManager);
		
		batch = new SpriteBatch();
		logo = new Sprite(new Texture("logo.png"));
		logo.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getWidth() * logo.getHeight() / (2 * logo.getWidth()));
		logo.setPosition(Gdx.graphics.getWidth() / 2 - logo.getWidth() / 2, 5 * Gdx.graphics.getHeight() / 4);
		
		Tween.registerAccessor(Sprite.class, new SpriteAccessor());
		
		Tween.to(logo, SpriteAccessor.PosY, 2.5f)
			.delay(1)
			.target(3 * Gdx.graphics.getHeight() / 4)
			.ease(Elastic.OUT)
			.start(tweenManager);
		
	}
	
	public void close(final String targetScreen){
		
		Tween.to(logo, SpriteAccessor.PosY, 0.6f)
			.target(5 * Gdx.graphics.getHeight() / 4)
			.ease(Sine.INOUT)
			.start(tweenManager);
		
		Tween.to(options, TextButtonAccessor.PosY, 0.5f)
			.target(-2*play.getHeight())
			.ease(Sine.INOUT)
			.start(tweenManager);
		
		Tween.to(stats, TextButtonAccessor.PosY, 0.7f)
			.target(-2*stats.getHeight())
			.ease(Sine.INOUT)
			.start(tweenManager);
		
		Tween.to(play, TextButtonAccessor.PosY, 0.9f)
			.target(-2*options.getHeight())
			.ease(Sine.INOUT)
			.setCallback(new TweenCallback(){

				@Override
				public void onEvent(int arg0, BaseTween<?> arg1) {
					if (targetScreen == "OPTIONS"){
						main.setOptionsScreen();
					}else if (targetScreen == "PLAYSCREEN"){
						main.setPlayScreen();
					}else if (targetScreen == "STATS"){
						main.setStatsScreen();
					}
				}
				
			})
			.start(tweenManager);
		
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}

}
