package com.cope.flight.screens;

import java.io.IOException;

import com.badlogic.gdx.Screen;
import com.cope.flight.Background;
import com.cope.flight.MainClass;
import com.cope.flight.data.Data;

public class StatsScreen implements Screen{
	
	MainClass main;
	Data data;
	Background bg;
	
	public StatsScreen(MainClass main, Background bg){
		this.main = main;
		this.bg = bg;
		data = null;
		
		try {
			data = Data.readData();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void render(float delta) {
		bg.render(delta);
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
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void dispose() {
		
	}

}
