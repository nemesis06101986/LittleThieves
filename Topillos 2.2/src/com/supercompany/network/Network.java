package com.supercompany.network;

import java.util.Comparator;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

public class Network {
	static public final int port = 58555;
	static public final int MODE_SERVER = 0;
	static public final int MODE_CLIENT = 1;
	
	static public void register(EndPoint endPoint){
		Kryo kryo = endPoint.getKryo();
		kryo.register(TopoHit.class);
		kryo.register(PlayerInfo.class);
	}
	
	public static class TopoHit{
		public int topoId;
		public int playerId;
	}
	
	public static class PlayerInfo{
		public int id;
		public String address;
		public int mode;
	}
	
	public static class PlayerComparator implements Comparator<PlayerInfo>{

		@Override
		public int compare(PlayerInfo o1, PlayerInfo o2) {
			return Integer.compare(o1.id, o2.id);
		}
		
	}
	
}
