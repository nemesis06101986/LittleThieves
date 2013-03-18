package com.supercompany.network.protocol;

import java.io.IOException;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;
import com.esotericsoftware.kryonet.Server;
import com.supercompany.models.TopoManagerMultiplayer;
import com.supercompany.network.Network;
import com.supercompany.network.Network.TopoHit;

public class ServerRunningProtocol implements Protocol{
	private TopoManagerMultiplayer context;
	private Server server;
	private int myId;
	
	private class ProtocolListener extends Listener{
		/**
		 * Called whenever a new client is connected to this server. 
		 * @param connection includes information about the new connection. 
		 */
		public void connected(Connection connection){
			
			
		}
		
		public void received(Connection connection, Object object){
			if (object instanceof TopoHit){
				TopoHit msg = (TopoHit)object;
				if(context.isAlive(msg.topoId)){
					server.sendToAllTCP(msg);
					context.topoHasBeenHit(msg.topoId, false);
				}
			}
		}
		
		public void disconnected(Connection connection){
			
		}
		
	}
	
	public ServerRunningProtocol() throws IOException{
		myId = 0;
		server = new Server();
		server.start();
		Network.register(server);
		server.addListener(new ThreadedListener(new ProtocolListener()));
		server.bind(Network.port);
		server.start();
	}
	
	public void topoHit(int id){
		if(context.isAlive(id)){
			TopoHit msg = new TopoHit();
			msg.playerId = myId;
			msg.topoId = id;
			server.sendToAllTCP(msg);
			context.topoHasBeenHit(id, true);
		}
	}
	
}
