package com.cope.flight;

import java.io.IOException;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Sine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.cope.flight.data.Data;
import com.cope.flight.entities.PipeManager;
import com.cope.flight.entities.Plane;
import com.cope.flight.screens.PlayScreen;
import com.cope.flight.tween.TextLabelAccessor;
import com.cope.game.MainActivity;

public class GameLogic {
	
	boolean onTouch, offTouch, scored, paused;
	boolean onTouch1, offTouch1;
	PipeManager pipes;
	Plane plane;
	Sound ding, death, water, ambience;
	int score;

	Label 
		averageText,
		averageValue,
		timePlayedText,
		timePlayedValue,
		highScoreLabel, 
		highScoreText, 
		secondScore, 
		thirdScore, 
		forthScore, 
		fifthScore, 
		scoreLabel, 
		pausedLabel;
	
	LabelStyle style;
	BitmapFont font;
	Stage stage;
	
	TweenManager tweenManager;
	
	Data data;
	
	SpriteBatch batch;
	
	long startTime, pauseStart;
	float playedTime;
	
	public GameLogic(){
		data = null;
		try {
			data = Data.readData();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		ding = Gdx.audio.newSound(Gdx.files.internal("ding.wav"));
		death = Gdx.audio.newSound(Gdx.files.internal("death.wav"));
		water = Gdx.audio.newSound(Gdx.files.internal("water_slosh.wav"));
		ambience = Gdx.audio.newSound(Gdx.files.internal("underwater_ambience.wav"));
		
		ambience.loop(0.5f);
		ambience.play();
		
		onTouch = false;
		offTouch = true;
		
		pipes = new PipeManager();
		plane = new Plane();
		
		font = new BitmapFont(Gdx.files.internal("square.fnt"));
		
		float widthScaler = 0.8f * Gdx.graphics.getWidth() / 1200f;
		float heightScaler = 0.8f * Gdx.graphics.getHeight() / 2000f;
		
		font.setScale(font.getScaleY() * widthScaler, font.getScaleX() * heightScaler);
		
		style = new LabelStyle();
		style.font = font;
		
		Data data = null;
		
		try {
			data = Data.readData();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		style.font.setScale(0.5f);
		
		MainActivity.showAd(MainActivity.getActivity());
		
		highScoreText = new Label("Highscores", style);
		highScoreText.setScale(1.5f);
		highScoreText.setPosition(
				-highScoreText.getWidth(), 
				3 * Gdx.graphics.getHeight() / 4 - highScoreText.getHeight() / 2);
		highScoreLabel = new Label(""+data.getScore(0)+"", style);
		secondScore = new Label(""+data.getScore(1)+"", style);
		thirdScore = new Label(""+data.getScore(2)+"", style);
		forthScore = new Label(""+data.getScore(3)+"", style);
		fifthScore = new Label(""+data.getScore(4)+"", style);
		
		averageText = new Label("Average Score : ", style);
		averageText.setX(-averageText.getWidth());
		averageText.setY(highScoreText.getY() -  highScoreLabel.getHeight() * 8.4f);
		averageValue = new Label("", style);
		
		timePlayedText = new Label("Minutes Played : ", style);
		timePlayedText.setX(-timePlayedText.getWidth());
		timePlayedText.setY(averageText.getY() -  timePlayedText.getHeight() * 1.1f);
		timePlayedValue = new Label("", style);
		
		scoreLabel = new Label("0", style);
		pausedLabel = new Label("Game Paused", style);
		
		stage = new Stage();
		
//		stage.addActor(averageText);
//		stage.addActor(averageValue);
//		stage.addActor(timePlayedText);
//		stage.addActor(timePlayedValue);
		stage.addActor(highScoreText);
		stage.addActor(highScoreLabel);
		stage.addActor(secondScore);
		stage.addActor(thirdScore);
		stage.addActor(forthScore);
		stage.addActor(fifthScore);
		stage.addActor(scoreLabel);
		stage.addActor(pausedLabel);
		
		batch = new SpriteBatch();
		
		tweenManager = new TweenManager();
		Tween.registerAccessor(Label.class, new TextLabelAccessor());
	}
	
	public void update(float delta){
		tweenManager.update(delta);
		playedTime = System.currentTimeMillis() - startTime;
		
		if (!paused) {
			pipes.update(delta, batch);
			plane.update(delta, batch);
		}
		stage.draw();
		stage.act();
		
		scoreLabel.setPosition(
				(Gdx.graphics.getWidth() - scoreLabel.getTextBounds().width)/ 2, 
				Gdx.graphics.getHeight() - 2 * scoreLabel.getTextBounds().height);
		
		averageValue.setX(averageText.getX() + averageText.getWidth() * 1.05f);
		averageValue.setY(averageText.getY() + averageValue.getHeight());
		
		timePlayedValue.setX(timePlayedText.getX() + timePlayedText.getWidth() * 1.05f);
		timePlayedValue.setY(timePlayedText.getY() + timePlayedValue.getHeight());
		
		if (paused){
			pausedLabel.setPosition((Gdx.graphics.getWidth() - pausedLabel.getWidth()) / 2, Gdx.graphics.getHeight() / 2);
		}else{
			pausedLabel.setPosition(2 * Gdx.graphics.getWidth(), 0);
		}
		
		if (pipes.isCollidingWith(plane.getBounds()) || plane.getY() < 0 || plane.getY() > Gdx.graphics.getHeight()){
			if (plane.isAlive()) {
				death.play();
				playedTime -= System.currentTimeMillis() - pauseStart;
				data.addScore(score, playedTime);
				
				try {
					Data.saveData(data);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				highScoreLabel.setText(""+data.getScore(0)+"");
				secondScore.setText(""+data.getScore(1)+"");
				thirdScore.setText(""+data.getScore(2)+"");
				forthScore.setText(""+data.getScore(3)+"");
				fifthScore.setText(""+data.getScore(4)+"");
				
				secondScore.setPosition(Gdx.graphics.getWidth() / 2 - secondScore.getWidth() / 2, -secondScore.getHeight());
				thirdScore.setPosition(Gdx.graphics.getWidth() / 2 - thirdScore.getWidth() / 2, -secondScore.getHeight());
				forthScore.setPosition(Gdx.graphics.getWidth() / 2 - forthScore.getWidth() / 2, -secondScore.getHeight());
				fifthScore.setPosition(Gdx.graphics.getWidth() / 2 - fifthScore.getWidth() / 2, -secondScore.getHeight());
				
				averageValue.setText(""+(int)(data.getTotalScore() / data.getGamesPlayed())+"");
				averageText.setX(-averageText.getWidth() - averageValue.getWidth());
				
				timePlayedValue.setText(""+data.getSecondsPlayed() / 60 +"");
				timePlayedText.setX(-timePlayedText.getWidth() - timePlayedValue.getWidth());
				
				plane.kill();
				pipes.setMoving(false);
				plane.setVelocity(100, plane.getVelocity().y);
				
				Tween.to(highScoreLabel, TextLabelAccessor.PosY, 0.7f)
					.target(highScoreText.getY() -  highScoreLabel.getHeight() * 2)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(secondScore, TextLabelAccessor.PosY, 0.9f)
					.target(highScoreText.getY() -  highScoreLabel.getHeight() * 3.1f)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(thirdScore, TextLabelAccessor.PosY, 1.1f)
					.target(highScoreText.getY() -  highScoreLabel.getHeight() * 4.2f)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(forthScore, TextLabelAccessor.PosY, 1.3f)
					.target(highScoreText.getY() -  highScoreLabel.getHeight() * 5.3f)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(fifthScore, TextLabelAccessor.PosY, 1.5f)
					.target(highScoreText.getY() -  highScoreLabel.getHeight() * 6.4f)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(highScoreText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() / 2 - highScoreText.getWidth() / 2)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(averageText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() / 2 - averageText.getWidth() * 1.05f / 2 - averageValue.getWidth() / 2)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
				Tween.to(timePlayedText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() / 2 - timePlayedText.getWidth() * 1.05f / 2 - timePlayedValue.getWidth() / 2)
					.ease(Sine.INOUT)
					.start(tweenManager);
				
			}
		}
		if (!plane.isAlive()) {
			PlayScreen.bg.setMoving(false);
		}else{
			highScoreLabel.setPosition(
					(Gdx.graphics.getWidth() - highScoreLabel.getTextBounds().width)/ 2, 
					2 * highScoreLabel.getTextBounds().height);
			
			secondScore.setPosition(Gdx.graphics.getWidth() / 2 - secondScore.getWidth() / 2, -secondScore.getHeight());
			thirdScore.setPosition(Gdx.graphics.getWidth() / 2 - thirdScore.getWidth() / 2, -secondScore.getHeight());
			forthScore.setPosition(Gdx.graphics.getWidth() / 2 - forthScore.getWidth() / 2, -secondScore.getHeight());
			fifthScore.setPosition(Gdx.graphics.getWidth() / 2 - fifthScore.getWidth() / 2, -secondScore.getHeight());
		}
		if (Gdx.input.isTouched(0) && !paused){
			offTouch = false;
			if (!onTouch && plane.isAlive()){
				onTouch = true;
				plane.setVelocity(0, 5.7f * Gdx.graphics.getHeight() / 7);
				water.play(0.5f);
			}else if (!plane.isAlive()){
				pipes = new PipeManager();
				plane = new Plane();
				PlayScreen.bg.setMoving(true);
				score = 0;
				scoreLabel.setText(""+score+"");
				
				Tween.to(highScoreText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() + highScoreText.getWidth())
					.ease(Sine.INOUT)
					.setCallback(new TweenCallback() {

						@Override
						public void onEvent(int type, BaseTween<?> source) {
							highScoreText.setX(-highScoreText.getWidth());
							
						}
						
					})
					.start(tweenManager);
				
				Tween.to(averageText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() + averageText.getWidth())
					.ease(Sine.INOUT)
					.setCallback(new TweenCallback() {

						@Override
						public void onEvent(int type, BaseTween<?> source) {
							averageText.setX(-averageText.getWidth() - averageValue.getWidth() - averageValue.getWidth());
							
						}
						
					})
					.start(tweenManager);
				
				Tween.to(timePlayedText, TextLabelAccessor.PosX, 1.6f)
					.target(Gdx.graphics.getWidth() + timePlayedText.getWidth())
					.ease(Sine.INOUT)
					.setCallback(new TweenCallback() {
						
						@Override
						public void onEvent(int type, BaseTween<?> source) {
							timePlayedText.setX(-timePlayedText.getWidth() - timePlayedValue.getWidth() - timePlayedValue.getWidth());
							
						}
						
					})
					.start(tweenManager);
			}
		}
		else if (!paused){
			onTouch = false;
			if (!offTouch){
				offTouch = true;
			}
		}
		
		if (pipes.get(0).getX() + pipes.get(0).getWidth() / 2 < plane.getX() && scored == false && plane.isAlive()){
			ding.play(0.7f);
			scored = true;
			score++;
			scoreLabel.setText(""+score+"");
			if (data.getScore(0) < score){
				highScoreLabel.setText(""+score+"");
			}
		}
		else if (pipes.get(0).getX() + pipes.get(0).getWidth() / 2 > plane.getX()){
			scored = false;
		}
	}
	
	public boolean isPaused(){
		return paused;
	}
	
	public void pause(){
		paused = true;
		pauseStart = System.currentTimeMillis();
	}
	
	public void unpause(){
		paused = false;
		playedTime -= System.currentTimeMillis() - pauseStart;
		pauseStart = 0;
	}

}