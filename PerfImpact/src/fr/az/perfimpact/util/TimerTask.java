package fr.az.perfimpact.util;

public class TimerTask extends java.util.TimerTask {
	
	private long time = 0;
	
	@Override
	public void run() {
		time += 1;
	}
	
	public long getTime() {
		return time;
	}
}
