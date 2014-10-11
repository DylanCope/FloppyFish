package com.cope.flight;

import com.badlogic.gdx.Game;
import com.cope.flight.screens.MainMenu;
import com.cope.flight.screens.OptionsScreen;
import com.cope.flight.screens.PlayScreen;
import com.cope.flight.screens.StatsScreen;

public class MainClass extends Game {
	
	Game game;
	PlayScreen playScreen;
	MainMenu mainMenu;
	OptionsScreen options;
	StatsScreen stats;
	Background bg;
	float times[] = {0, 0};
	
	@Override
	public void create() {
		bg = new Background();
		game = this;
		playScreen = new PlayScreen(this, bg);
		mainMenu = new MainMenu(this, bg);
		options = new OptionsScreen(this, bg);
		stats = new StatsScreen(this, bg);
		game.setScreen(mainMenu);
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();	
	}

	@Override
	public void resume() {
		super.resume();
	}
	
	public void setMainMenu(){
		game.setScreen(mainMenu);
	}
	
	public void setPlayScreen(){
		game.setScreen(playScreen);
	}
	
	public void setStatsScreen(){
		game.setScreen(stats);
	}
	
	public void setOptionsScreen(){
		game.setScreen(options);
	}
}
