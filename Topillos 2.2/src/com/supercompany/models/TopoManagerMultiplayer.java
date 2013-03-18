package com.supercompany.models;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import com.badlogic.gdx.math.Vector3;
import com.supercompany.game.Assets;
import com.supercompany.network.protocol.Protocol;

/**
 * Convenience class to generate topos. It uses a pseudorandom algorithm to generate a new topo every certain time. 
 * This time is reduced regularly until reaching the minimum intervar between generations.
 * All this times can be changed to adjust the game (new levels, new difficulties...)
 * @author Mario Ferreira Vilanova
 *
 */
public class TopoManagerMultiplayer extends TopoManager{
	public final static int MODE_CLIENT = 1;
	public final static int MODE_SERVER = 2;

	private int mode;
	private Protocol protocol;
	
	/**
	 * Creates a new manager
	 * @param topos list of all the topos the manager is going to take care of :)
	 * @param interval initial interval between generations
	 * @param owner the owner of the list of topos. This will be used for notifications
	 */
	public TopoManagerMultiplayer(LinkedList<Topo> topos, float interval, World owner, int mode) {
		super(topos, interval, owner);
		this.mode = mode;
	}

	/**
	 * Updates the list of topos
	 * @param delta delta time used to determine the timing of generations
	 */
	public void update(float delta){
		timeNewTopoCounter = timeNewTopoCounter - delta;
		timeReduceIntervalCounter = timeReduceIntervalCounter - delta;
		
		if (mode == MODE_SERVER){
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
		}else{
			//TODO espacio reservado para su publicidad
		}
		
		/*
		 * para un futuro modo cooperativo. En el modo multijugador actual no hay vidas
		Iterator<Topo> iter = topos.iterator();
		while (iter.hasNext()){
			Topo temp = iter.next();
			if (temp.missed){
				temp.missed = false;
				updateLifes();
			}
		}
		*/
	}
	
	/**
	 * This function should be called everytime the user clicks on the screen to check wether a topo has been hit or not
	 * @param position position of the click
	 */
	public void hit (Vector3 position){ // se llama a este metodo cuando se ha clicado en la pantalla
		Iterator<Topo> iter = topos.iterator();
		while (iter.hasNext()){
			Topo temp = iter.next();
			if (temp.isHit(position)){
				if (temp.state != Topo.ESTADO_ALCANZADO && temp.state != Topo.ESTADO_VACIO){
					//TODO notificar al protocolo de que se ha alcanzado un topo
					
					//temp.switchToState(Topo.ESTADO_ALCANZADO);
					//updatePoints();
				}
			}
		}
	}
	
	/**
	 * Private method used to randomly generate a new topo. 
	 * It uses PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL to generate an extra topo. This probability is reduced to prevent all topos to appear at the same time
	 */
	public void generateTopo(){ // genera un topo en una posicion aleatoria
		Random randomGenerator = new Random();
		int randomgeneration = ((randomGenerator.nextInt(100)));
		int randomTopo = ((randomGenerator.nextInt())%topos.size());
		
		while (randomTopo < 0 || (topos.get(randomTopo).state != Topo.ESTADO_VACIO)){
			randomTopo = (randomGenerator.nextInt()%topos.size());
		}
		
		//TODO avisar al protocolo que se ha generado un topo en la posicion randomtopo
		
		if (randomgeneration > 100-probabilidad) {
			probabilidad = probabilidad - 5; // para evitar que se siga entrando en el bucle se reduce la posibilidad de topos consecutivos
			generateTopo();
		}else{
			probabilidad = PROBABILIDAD_DE_GENERACION_ALEATORIA_INICIAL;
		}
	}
	
	/**
	 * Used to tell the protocoll that a new topo should wake up
	 * @param id id del topo que hay que despertar
	 */
	public void wakeUpTopo(int id){
		topos.get(id).switchToState(Topo.ESTADO_SALIENDO);
	}
	
	/**
	 * Used to tell the manager that a topo has been hit
	 * @param id id del topo que ha sido alcanzado
	 * @param mine verdadero si el topo ha sido alcanzado por esta máquina
	 */
	public void topoHasBeenHit (int id, boolean mine){
		if (mine){
			
		}else{
			
		}
	}
	
	public void setProtocol(Protocol protocol){
		this.protocol = protocol;
	}
	/**
	 * Updates the points of the world that ownes the topos everytime one of them has been hit.
	 * @param id identification number
	 */
	public void updatePoints(){
		owner.points += 1;
		Assets.hitSound.play();
	}
	
	/**
	 * Updates the world's lifes everytime the user misses a topo.
	 */
	public void updateLifes(){
		owner.lifes -= 1;
	}
}
