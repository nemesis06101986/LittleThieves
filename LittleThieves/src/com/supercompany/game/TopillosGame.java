package com.supercompany.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.supercompany.screens.EndOfGameScreen;
import com.supercompany.screens.GameScreen;
import com.supercompany.screens.MainMenuScreen;
import com.supercompany.screens.ScoresScreen;

/**
 * Main class of the game. It holds all screens used, initializes both settings and assets and sets the menu screen as main screen when the game starts.
 * It also captures the android back button so the game gains control over it
 * @author Mario Ferreira Vilanova
 *
 */
public class TopillosGame extends Game implements InputProcessor{
	public final static String RECORD = "record";

	public Screen mainMenuScreen;
	public Screen gameScreen;
	public Screen endOfGameScreen;
	public Screen scoresScreen;
	
	@Override
	public void create() {
		Assets.load();
		Settings.load();
		
		Gdx.input.setCatchBackKey(true);
		Gdx.input.setInputProcessor(this);
		
		mainMenuScreen = new MainMenuScreen(this);
		gameScreen = new GameScreen(this);
		endOfGameScreen = new EndOfGameScreen(this);
		scoresScreen = new ScoresScreen(this);
		setScreen(mainMenuScreen);
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		return false;
	}

	@Override
	public boolean touchMoved(int x, int y) {
		return false;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		return false;
	}

}
