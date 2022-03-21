package me.thedreps.hypixeltweaks;

public class ServerInfo {
	
	String server = "default";
	String gametype = "default";
	String mode = "default";
	String map = "default";
	String lobbyname = "default";
	String json = "default";
	
	public void reset() {
		this.server = "default";
		this.gametype = "default";
		this.mode = "default";
		this.map = "default";
		this.lobbyname = "default";
		this.json = "default";
		
	}
	
	public String debug() {
		String dump = "server: " + server + ", gameType: " + gametype + ", mode: " + mode + ", map: " + map + ", lobbyName: " + lobbyname + ", JSON: " + json;
		return dump;
	}
	
	
	
	
//	public void CollectInfo(String JSON) {
//		this.json = JSON;
//		String[] types = {"server", "gameType", "mode", "map", "lobbyname"};
//		
//		for(String type : types) {
//			if(JSON.contains(type)) {
//				parseJSON(type);
//			}
//		}
//	}
//	
//	public String parseJSON(String type) {
//		int index = this.json.indexOf(type.concat("='"));
//		String trimmed = this.json.substring(index + type.length()+2);
//		index = this.json.indexOf("'");
//		trimmed = trimmed.substring(0, index);
//		
//		return trimmed;
//	}
	
}
