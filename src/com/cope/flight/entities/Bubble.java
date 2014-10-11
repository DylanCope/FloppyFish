package com.cope.flight.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Bubble extends Entity{
	
	Random random;
	float velocity;
	ShapeRenderer sr;
	boolean isMoving;
	Sprite bubbleSprite;
	
	public Bubble(){
		random = new Random();
		velocity = Gdx.graphics.getHeight() / 10 + random.nextInt(Gdx.graphics.getHeight() / 5);
		width = 10 + random.nextInt(Gdx.graphics.getWidth() / 10);
		x = random.nextInt(2 * Gdx.graphics.getWidth());
		y = - width;
		
		sr = new ShapeRenderer();
		isMoving = true;
		bubbleSprite = new Sprite(new Texture("bubble.png"));
		bubbleSprite.setSize(width, width);
	}
	
	public void update(float delta, SpriteBatch batch){
		if (isMoving) {
			x += - 0.45f * Gdx.graphics.getWidth() * delta;
		}
		y += velocity * delta;
		
		bubbleSprite.setPosition(x, y);
		batch.begin();
		bubbleSprite.draw(batch);
		batch.end();
	}
	
	public void setMoving(boolean moving) {
		this.isMoving = moving;
	}
}
