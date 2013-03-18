package com.supercompany.network.protocol;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.supercompany.models.TopoManagerMultiplayer;
import com.supercompany.network.Network;
import com.supercompany.network.Network.PlayerInfo;

public class ClientReconnectingProtocol implements Protocol {
	private TopoManagerMultiplayer context;
	private int myId;
	private Client client;
	private TreeSet<PlayerInfo> players;
	private Listener listener;
	
	private class ProtocolListener extends Listener{
	
		public void connected(Connection connection){
			
			
		}
		
		public void received(Connection connection, Object object){
			
		}
		
		public void disconnected(Connection connection){
			
		}
		
	}
	
	public ClientReconnectingProtocol(TopoManagerMultiplayer context, Client client, int myId, SortedSet<PlayerInfo> players){
		this.client = client;
		this.context = context;
		this.myId = myId;
		this.players = new TreeSet<PlayerInfo>();
		this.players.addAll(players);
		this.listener = new ThreadedListener(new ProtocolListener());
		client.addListener(listener);
		int nextServer = resolveNextServer();
	}
	
	private int resolveNextServer(){
		int result = 0;
		boolean found = false;
		Iterator<PlayerInfo> it = players.iterator();
		while( !found && it.hasNext() ){
			PlayerInfo player = it.next();
			if( player.mode != Network.MODE_SERVER){
				
			}
		}
		return result;
	}
}
