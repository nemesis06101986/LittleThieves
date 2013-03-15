package com.supercompany.game;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.tools.imagepacker.TexturePacker;
import com.badlogic.gdx.tools.imagepacker.TexturePacker.Settings;

/**
 * Running class for the desktop version of the game.
 * @author Mario Ferreira Vilanova
 *
 */
public class GameDesktop {

	public static void main (String[] args){
		Settings settings = new Settings();
		settings.padding = 2;
		settings.maxWidth = 1024;
		settings.maxHeight = 1024;
		TexturePacker.process(settings, "images", "data");
		
		new JoglApplication (new TopillosGame(), "Little Thieves", 320, 480, false);
	}
}
