package com.supercompany.models;

import java.util.Random;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.supercompany.game.Assets;

/**
 * Topo entity. A topo knows knows how to be drawn and how to behave. It can be set to autogenerate, but this option is disabled by default. 
 * @author Mario Ferreira Vilanova
 *
 */
public class Topo {
	public final static int ESTADO_VACIO = 0;
	public final static int ESTADO_SALIENDO = 1;
	public final static int ESTADO_FUERA = 2;
	public final static int ESTADO_METIENDOSE = 3;
	public final static int ESTADO_ALCANZADO = 4;
	
	public int state;
	public int id;
	public Vector3 position;
	public Vector3 size;
	public boolean missed;
	public boolean autoGenerate;
	
	private BoundingBox box;
	private Animation saliendo;
	private Animation fuera;
	private Animation metiendose;
	private Animation hit;
	private float animationTime;
	private float idleTime;
	private float hiddenTime;
	
	/**
	 * Generates a new topo
	 * @param id identification number for the topo. For convention is recommended to use consecutive numbers
	 * @param x x position
	 * @param y y position
	 * @param width width 
	 * @param height height
	 * @param state state where the topo is going to start the animation loop
	 * @param duration the time where the topo is going to be in the state "out" or "Fuera"
	 */
	public Topo (int id, float x, float y, int width, int height, int state, float duration){
		this.id = id;
		this.state = state;
		this.position = new Vector3(x,y,0);
		box = new BoundingBox(new Vector3(x,y,0), new Vector3(x+width, y+height, 0));
		
		setAnimationLength(duration);
		
		autoGenerate = false; // por defecto los topos son manejador por el toposManager
		animationTime = 0;
		missed = false;
	}
	
	/**
	 * Used to get the textureRegion in wich the topo is at.
	 * @param delta delta time form the last frame
	 * @return textureRegion of the current state of the topo
	 */
	public TextureRegion getDrawing(float delta){
		TextureRegion result;
		
		animationTime = animationTime + delta;
		
		switch (state){
		case ESTADO_VACIO: result = Assets.Topo0000; 
			if (autoGenerate && animationTime >= hiddenTime){
				hiddenTime = idleTime * new Random().nextInt(4) + 10;
				switchToState(Topo.ESTADO_SALIENDO);
			}
			break;
		case ESTADO_SALIENDO: result = saliendo.getKeyFrame(animationTime, false);
			if (saliendo.isAnimationFinished(animationTime)){
				switchToState(Topo.ESTADO_FUERA);
			}
			break;
		case ESTADO_FUERA: result = fuera.getKeyFrame(animationTime, false); 
			if (animationTime >= idleTime){
				switchToState(Topo.ESTADO_METIENDOSE);
			}
			break;
		case ESTADO_METIENDOSE: result = metiendose.getKeyFrame(animationTime, false);
			if (metiendose.isAnimationFinished(animationTime)){
				missed = true;
				switchToState(Topo.ESTADO_VACIO);
			}
			break;
		case ESTADO_ALCANZADO: result = hit.getKeyFrame(animationTime, false);
			if (hit.isAnimationFinished(animationTime)){
				switchToState(Topo.ESTADO_VACIO);
			}
			break;
		default: result = Assets.zanahoria ;break;
		}
		
		return result;
	}
	
	/**
	 * Wether the given point corresponds within the bounds of the topo
	 * @param hitPoint the point we want to check
	 * @return wether the point is or isn't within the bounds of the topo
	 */
	public boolean isHit(Vector3 hitPoint){
		return box.contains(hitPoint);
	}
	
	/**
	 * Manually changes the state where the topo is at. It also plays the sounds adecuate to the change (when it's been hit, or has been missed)
	 * @param state state to wich we want to switch
	 */
	public void switchToState (int state){
		animationTime = 0;
		if (state == ESTADO_ALCANZADO){
			Assets.hitSound.play();
		}
		this.state = state;
	}
	
	/**
	 * Changes the length of the iddle animation (When the topo is out). 
	 * It also initializes the other animations. This is done here in case we want to make all animations relative to each other
	 * @param duration new duration of the iddle animation
	 */
	public void setAnimationLength (float duration){
		saliendo = new Animation (0.025f, Assets.saliendoFrames);
		idleTime = duration;
		hiddenTime = duration*2;
		fuera = new Animation (duration/5f, Assets.fueraFrames);
		metiendose = new Animation (0.025f, Assets.entrandoFrames);
		hit = new Animation (0.025f, Assets.hitFrames);
	}
	
	/**
	 * Sets wether the topo should autogenerate or not
	 * @param autog wether the topo should autogenerate or not
	 */
	public void setAutogenerate (boolean autog){
		autoGenerate = autog;
	}
}
