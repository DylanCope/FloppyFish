package com.cope.flight.entities;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class PipeManager {
	
	private ArrayList<Pipe> pipes;
	int index;
	
	public PipeManager(){
		pipes = new ArrayList<Pipe>();
		pipes.add(new Pipe(0));
	}
	
	public void update(float delta, SpriteBatch batch){
		Pipe first = pipes.get(0);
		Pipe last = pipes.get(pipes.size() - 1);
		
		if (first.getX() < - first.getWidth()){
			pipes.remove(0);
		}
		if (last.getX() < Gdx.graphics.getWidth()){
			index++;
			pipes.add(new Pipe(index));
		}
		
		for (int i = 0; i < pipes.size(); i++){
			pipes.get(i).update(delta, batch);
		}
	}
	
	public boolean isCollidingWith(Rectangle r){
		boolean state = false;
		for (int i = 0; i < pipes.size(); i++){
			if (pipes.get(i).isCollidingWith(r)){
				state = true;
			}	
		}
		return state;
	}
	
	public void setMoving(boolean moving){
		for (int i = 0; i < pipes.size(); i++){
			pipes.get(i).setMoving(moving);
		}
	}
	
	public Pipe get(int index){
		return pipes.get(index);
	}
}
