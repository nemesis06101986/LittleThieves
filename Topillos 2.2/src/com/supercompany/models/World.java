package com.supercompany.models;

import java.util.LinkedList;

import com.badlogic.gdx.math.Vector3;

/**
 * Holds all the topos and the logic within the actual game.
 * @author Mario Ferreira Vilanova
 *
 */
public class World {
	public final float TIEMPO_FUERA_INICIAL = 3f;
	public final float TIEMPO_ENTRE_TOPOS_INICIAL = 1f;
	public final int PUNTOS_INICIALES = 0;
	public final int VIDAS_INICIALES = 3;
	
	public LinkedList<Topo> topos;
	public TopoManager manager;
	public int points, lifes;
	public boolean isFinished;

	/**
	 * Initializes a new world
	 */
	public World (){
		initiate();
	}

	/**
	 * Initializes all the topos, lifes and points
	 */
	public void initiate() {
		topos = new LinkedList<Topo>();

		topos.add(new Topo (1, 1, 8, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (2, 4, 8, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (3, 7, 8, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		
		topos.add(new Topo (4, 1, 6, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (5, 4, 6, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (6, 7, 6, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		
		topos.add(new Topo (7, 1, 4, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (8, 4, 4, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (9, 7, 4, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		
		topos.add(new Topo (10, 1, 2, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (11, 4, 2, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		topos.add(new Topo (12, 7, 2, 2, 2, Topo.ESTADO_VACIO, TIEMPO_FUERA_INICIAL));
		
		manager = new TopoManager(topos, TIEMPO_ENTRE_TOPOS_INICIAL, this);
		
		points = PUNTOS_INICIALES;
		lifes = VIDAS_INICIALES;
		isFinished = false;
	}
	
	/**
	 * updates the world's params
	 * @param delta
	 */
	public void update(float delta){
		manager.update(delta);
		isFinished = lifes <= 0;
	}
	
	/**
	 * Function used to tell the world that the user has clicked on the screen. It currently calls the manager to determine if it has hit a topo
	 * @param position
	 */
	public void hit(Vector3 position){
		manager.hit(position);
		
	}
	
	/**
	 * Called when a topo has been hit
	 * @param id identification number of the hit topo
	 */
	public void topoHit(int id){
		points++;
	}
	
	/**
	 * topo when a topo has been missed
	 * @param id identification number of the missed topo. We will miss you :(
	 */
	public void topoMissed(int id){
		lifes--;
	}
}
