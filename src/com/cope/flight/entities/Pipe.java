package com.cope.flight.entities;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public class Pipe extends Entity{
	
	Rectangle r1, r2;
	Random random;
	ShapeRenderer sr;
	boolean isMoving;
	Color colour;
	
	Sprite pipeSprite;
	
	public Pipe(int i){
		random = new Random();
		width = Gdx.graphics.getWidth() / 5;
		
		float gap = Gdx.graphics.getHeight() / 4;
		
		float h1 = Gdx.graphics.getHeight() / 5 + random.nextInt((int) (3 * Gdx.graphics.getHeight() / 4 - gap));
		float h2 = Gdx.graphics.getHeight() - h1 + 1;
		
		r1 = new Rectangle();
		r2 = new Rectangle();
		
		r1.set(0, -1, width, h1);
		r2.set(0, h1 + gap, width, h2);
		
		x = Gdx.graphics.getWidth() + 3 * width;
	
		sr = new ShapeRenderer();
		isMoving = true;

		colour = new Color(0.2f, 1, 0.2f, 0.5f);
		
		pipeSprite = new Sprite(new Texture("pipe.png"));
		pipeSprite.setSize(width, pipeSprite.getHeight() * width / pipeSprite.getWidth());
	}
	
	public void update(float delta, SpriteBatch batch){
		if (isMoving) {
			x += - 0.45f * Gdx.graphics.getWidth() * delta;
		}
		r1.setX(x);
		r2.setX(x);
		
//		sr.begin(ShapeType.Line);
//		sr.setColor(colour);
//		sr.rect(r1.getX(), r1.getY(), r1.getWidth(), r1.getHeight());
//		sr.rect(r2.getX(), r2.getY(), r2.getWidth(), r2.getHeight());
//		sr.end();

		pipeSprite.setX(x);
		batch.begin();
		pipeSprite.setY(r1.getHeight() - pipeSprite.getHeight());
		pipeSprite.draw(batch);
		pipeSprite.flip(false, true);
		pipeSprite.setY(r2.getY());
		pipeSprite.draw(batch);
		pipeSprite.flip(false, true);
		batch.end();
		
	}
	
	public boolean isCollidingWith(Rectangle r){
		if (r1.overlaps(r) || r2.overlaps(r)){
			return true;
		}else{
			return false;
		}
	}
	
	public void setMoving(boolean moving){
		isMoving = moving;
	}
}
