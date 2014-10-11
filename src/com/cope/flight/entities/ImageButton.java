package com.cope.flight.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.cope.flight.data.Event;

public class ImageButton extends Entity{
	
	private Event event;
	private Sprite sprite;
	private boolean onTouch;
	
	public ImageButton(Sprite sprite, Event event){
		this.sprite = sprite;
		this.event = event;
		x = sprite.getX();
		y = sprite.getY();
		width = sprite.getWidth();
		height = sprite.getHeight();
		
		onTouch = false;
	}
	
	public void update(SpriteBatch batch){
		batch.begin();
		sprite.draw(batch);
		batch.end();
		
		if (x < Gdx.input.getX() && Gdx.input.getX() < x + width && Gdx.input.isTouched()){
			if (y < Gdx.graphics.getHeight() - Gdx.input.getY()
					&& Gdx.graphics.getHeight() - Gdx.input.getY() < y + height){
				if (!onTouch) {
					event.onEvent();
					onTouch = true;
				}
			}
		}else{
			onTouch = false;
		}
	}
	
}
