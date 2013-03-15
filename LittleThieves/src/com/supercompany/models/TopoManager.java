package com.supercompany.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector3;

/**
 * Convenience class to generate topos. It uses a pseudorandom algorithm to generate a new topo every certain time. 
 * This time is reduced regularly until reaching the minimum intervar between generations.
 * All this times can be changed to adjust the game (new levels, new difficulties...)
 * @author Mario Ferreira Vilanova
 *
 */
public class TopoManager {
	public final float MINIMUM_INTERVAL = 0.25f; // tiempo minimo de intervalo del cual ya no se bajará. En segundos
	public final float TIME_REDUCE_INTERVAL = 6f; // tiempo tras el cual se reduce el intervalo. En segundos
	public final float RECUDE_INTERVAL_RATE = 0.95f; // porcentaje de reduccion del intervalo
	public final int PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL = 15;
	
	protected LinkedList<Topo> topos;
	protected float interval;
	protected float timeNewTopoCounter, timeReduceIntervalCounter;
	protected World owner;
	protected int probabilidad;
	
	/**
	 * Creates a new manager
	 * @param topos list of all the topos the manager is going to take care of :)
	 * @param interval initial interval between generations
	 * @param owner the owner of the list of topos. This will be used for notifications
	 */
	public TopoManager(LinkedList<Topo> topos, float interval, World owner){
		this.topos = topos;
		this.interval = interval;
		this.timeNewTopoCounter = interval;
		this.owner = owner;
		this.timeReduceIntervalCounter = TIME_REDUCE_INTERVAL;
		probabilidad = PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL;
	}
	
	/**
	 * Updates the list of topos
	 * @param delta delta time used to determine the timing of generations
	 */
	public void update(float delta){
		timeNewTopoCounter = timeNewTopoCounter - delta;
		timeReduceIntervalCounter = timeReduceIntervalCounter - delta;
		
		if (timeNewTopoCounter <= 0){ // si se ha cumplido el tiempo de espera generar un nuevo topo
			generateTopo();
			timeNewTopoCounter = interval;
		}
		
		if (timeReduceIntervalCounter <= 0 && interval > MINIMUM_INTERVAL){		// si se ha cumplido el tiempo de velocidad, aumentar la velocidad
			interval = interval * RECUDE_INTERVAL_RATE;
			
			Iterator<Topo> iter = topos.iterator();
			while (iter.hasNext()){
				iter.next().setAnimationLength(interval*3);
			}
			
			timeReduceIntervalCounter = TIME_REDUCE_INTERVAL;
		}
		
		Iterator<Topo> iter = topos.iterator();
		while (iter.hasNext()){
			Topo temp = iter.next();
			if (temp.missed){
				temp.missed = false;
				updateLifes(temp.id);
			}
		}
	}
	
	/**
	 * This function should be called everytime the user clicks on the screen to check wether a topo has been hit or not
	 * @param position position of the click
	 */
	public void hit (Vector3 position){ 
		Iterator<Topo> iter = topos.iterator();
		while (iter.hasNext()){
			Topo temp = iter.next();
			if (temp.isHit(position)){
				if (temp.state != Topo.ESTADO_ALCANZADO && temp.state != Topo.ESTADO_VACIO){
					temp.switchToState(Topo.ESTADO_ALCANZADO);
					owner.topoHit(temp.id);
					//updatePoints(temp.id);
				}
			}
		}
	}
	
	/**
	 * Private method used to randomly generate a new topo. 
	 * It uses PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL to generate an extra topo. This probability is reduced to prevent all topos to appear at the same time
	 */
	private void generateTopo(){ // genera un topo en una posicion aleatoria
		Random randomGenerator = new Random();
		int randomgeneration = ((randomGenerator.nextInt(100)));
		int randomTopo = ((randomGenerator.nextInt())%topos.size());
		
		while (randomTopo < 0 || (topos.get(randomTopo).state != Topo.ESTADO_VACIO)){
			randomTopo = (randomGenerator.nextInt()%topos.size());
		}
		topos.get(randomTopo).switchToState(Topo.ESTADO_SALIENDO); // con un porcentage del X% se genera un topo extra
		if (randomgeneration > 100-probabilidad) {
			probabilidad = probabilidad - 5; // para evitar que se siga entrando en el bucle se reduce la posibilidad de topos consecutivos
			generateTopo();
		}else{
			probabilidad = PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL;
		}
	}
	
	/**
	 * Updates the points of the world that ownes the topos everytime one of them has been hit.
	 * @param id identification number
	 */
	public void updatePoints(int id){
		/*
		owner.points += 1;
		Assets.hitSound.play();
		*/
		owner.topoHit(id);
	}
	
	/**
	 * Updates the world's lifes everytime the user misses a topo.
	 */
	public void updateLifes(int id){
		//owner.lifes -= 1;
		owner.topoMissed(id);
	}
	

}
