package com.cope.flight;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cope.flight.entities.Bubble;

public class Background {
	
	ArrayList<Bubble> bubbles;
	Random random;
	Sprite bg;
	SpriteBatch batch;
	
	public Background(){
		bubbles = new ArrayList<Bubble>();
		random = new Random();
		bg = new Sprite(new Texture("backgroundSea.png"));
		bg.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
	}
	
	public void render(float delta){
		batch.begin();
		bg.draw(batch);
		batch.end();
		if (random.nextInt(75) == 50){
			bubbles.add(new Bubble());
		}
		for (int i = 0; i < bubbles.size(); i++){
			bubbles.get(i).update(delta, batch);
			if (bubbles.get(i).getY() > Gdx.graphics.getHeight() + bubbles.get(i).getWidth()){
				bubbles.remove(i);
			}
		}
	}
	
	public void setMoving(boolean moving){
		for (int i = 0; i < bubbles.size(); i++) {
			bubbles.get(i).setMoving(moving);
		}
	}
	
}
