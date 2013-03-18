package com.supercompany.network.protocol;

import java.util.SortedSet;
import java.util.TreeSet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.supercompany.models.TopoManagerMultiplayer;
import com.supercompany.network.Network.PlayerInfo;
import com.supercompany.network.Network.TopoHit;

public class ClientRunningProtocol implements Protocol{
	private TopoManagerMultiplayer context;
	private int myId;
	private Client client;
	private TreeSet<PlayerInfo> players;
	private Listener listener;
	
	private class ProtocolListener extends Listener{
	
		public void connected(Connection connection){
			
			
		}
		
		public void received(Connection connection, Object object){
			if (object instanceof TopoHit){
				TopoHit msg = (TopoHit)object;
				context.topoHasBeenHit(msg.topoId, msg.playerId == myId);
			}
		}
		
		public void disconnected(Connection connection){
			ClientReconnectingProtocol newProtocol = new ClientReconnectingProtocol(context, client, myId, players);
			client.removeListener(listener);
			context.setProtocol(newProtocol);
		}
		
	}
	
	public ClientRunningProtocol(TopoManagerMultiplayer context, Client client, int myId, SortedSet<PlayerInfo> players){
		this.client = client;
		this.context = context;
		this.myId = myId;
		this.players = new TreeSet<PlayerInfo>();
		this.players.addAll(players);
		this.listener = new ThreadedListener(new ProtocolListener());
		client.addListener(listener);
	}
	
	public void topoHit(int id){
		TopoHit msg = new TopoHit();
		client.sendTCP(msg);
	}
	
}
