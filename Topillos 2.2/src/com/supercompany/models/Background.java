package com.supercompany.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.supercompany.game.Assets;

/**
 * Convenience class to print the background. In this case the background is formed from various tiles and images. 
 * @author Mario Ferreira Vilanova
 *
 */
public class Background {
	
	public void draw (SpriteBatch batcher, int fromx, int fromy, int tox, int toy){
		for (int i=fromx; i < tox; i=i+2){
			for (int o = 0; o < 10; o=o+2){
				batcher.draw(Assets.background, i, o, 2, 2);
			}
		}
		
		for (int i=fromx; i < tox; i=i+2){
			batcher.draw(Assets.cielo, i, 10, 2, 5);
		}
		
		for (int i=fromx; i < tox; i=i+2){
			batcher.draw(Assets.valla, i, 10, 2, 2);
		}
	}

}
