package fr.az.perfimpact.util;

public class TimerTask {
	
	private long time = System.currentTimeMillis();
	
	public TimerTask() {
		this.time = System.currentTimeMillis();
	}
	
	public double getTime() {
		return (System.currentTimeMillis() - time) / 1000;
	}
}
