package com.supercompany.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Settings class. This is a convenience wrapper for the Preferences class in libgdx. It allows easy access to save and load small amounts of data
 * @author Mario Ferreira Vilanova
 *
 */
public class Settings {
	public static final Preferences preferences = Gdx.app.getPreferences("preferences");
	public static final float TIME_TO_PRESS_BACK_AGAIN = 0.75f;
	public static final int MAXSCORES = 5;
	public static int record = 0;
	public static int lastGamePoints = 0;
	public static int[] scores = new int[MAXSCORES];
	public static float backpressed = 0;
	
	/**
	 * Initializes the class. It creates a new preferences file if missing
	 */
	public static void load() {
		if (!preferences.contains("record")) // if settings file is empty. Just check any field
			save();
	
		for (int i = 0; i < scores.length; i++){
			scores[i] = preferences.getInteger("score"+i , 0);
		}
		record = scores[getMaximumScoreIndex()];
		lastGamePoints = preferences.getInteger("lastGamePoints", 0);
	}
	
	/**
	 * Saves all changes to make them permanent.
	 */
	public static void save() {
		//preferences.putInteger("record", record);
		preferences.putInteger("lastGamePoints", lastGamePoints);
		for (int i = 0; i < scores.length; i++){
			preferences.putInteger("score"+i , scores[i]);
		}
		preferences.flush();
	}
	
	
	/**
	 * Updates the saved data with the incoming scores. Always updates lasGamePoints, and introduces the new score within the best 5 if necessary
	 * @param points score of the last game played
	 */
	public static void updateScores (int points){
		int index = getMinimumScoreIndex();
		if (scores[index] < points){
			scores[index] = points;
		}
		orderScores();
		lastGamePoints = points;
		save();
	}
	
	/**
	 * Currently unused. It gives the array index of the smallest score saved
	 * @return Array index of the smallest score saved
	 */
	public static int getMinimumScoreIndex(){
		int min = 0;
		for (int i = 0; i < scores.length; i++){
			if (scores[i] < scores[min]){
				min = i;
			}
		}
		return min;
	}
	
	/**
	 * Currently unused. It gives the array index of the bigest score saved
	 * @return Array index of the bigest score saved
	 */
	public static int getMaximumScoreIndex(){
		int max = 0;
		for (int i = 0; i < scores.length; i++){
			if (scores[i] > scores[max]){
				max = i;
			}
		}
		return max;
	}
	
	/**
	 * Private method used to reorganize the list of saved scores. It uses the bubble method for ordering
	 */
	private static void orderScores(){
		for (int i = 0; i < scores.length; i++){
			for (int p = 0; p < scores.length-1; p++){
				if (scores[p] < scores[p+1]){
					int temp = scores[p+1];
					scores[p+1] = scores[p];
					scores[p] = temp;
				}
			}	
		}
	}

}
