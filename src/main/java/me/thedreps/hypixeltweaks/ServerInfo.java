package me.thedreps.hypixeltweaks;

import com.google.gson.Gson;

public class ServerInfo {
	
	String server = "default";
	String gameType = "default";
	String mode = "default";
	String map = "default";
	String lobbyname = "default";
	String json = "default";
	
	public void reset() {
		this.server = "default";
		this.gameType = "default";
		this.mode = "default";
		this.map = "default";
		this.lobbyname = "default";
		this.json = "default";
	}
	
	public boolean hasData() {
		if (this.server.equals("default")) {
			return false;
		}else{
			return true;
		}
	}
	
}
