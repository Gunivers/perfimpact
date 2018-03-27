package fr.az.perfimpact.util;

public abstract class GuniPlugin {

	private GuniPlugin getPlugin() {
		return null;
	}
	
	public int send(String message) {
		
		return message.length();
	}
	
	public String receive(int count) {
		return "";
	}
	
	public abstract boolean onLoad();
	
	public abstract boolean onDisable();
}
