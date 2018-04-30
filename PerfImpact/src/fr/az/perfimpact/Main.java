package fr.az.perfimpact;

import fr.az.perfimpact.commands.PerfImpactCommandExecutor;
import fr.az.perfimpact.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

public class Main extends JavaPlugin {

	public HashMap<String,ArrayList<Double>> timeImpact = new HashMap<String,ArrayList<Double>>();
	public HashMap<String,Long> timeCalled = new HashMap<String,Long>();
	
	public HashMap<String,TimerTask> timer = new HashMap<String,TimerTask>();
	
	public FileConfiguration config;
	
	private boolean isCapturing = false;
	private ConsoleCommandSender console;

	@Override
	public void onEnable()
	{
		saveDefaultConfig();
		
		config = getConfig();
		console = getServer().getConsoleSender();
		
		getCommand("perfimpact").setExecutor(new PerfImpactCommandExecutor(this));
		
		if (config.get("PathList:") != null) {
			
			for(String path : config.getString("PathList","").split(":")) {
				timeCalled.put(path, config.getLong("CallCount."+ path,0L));
				
				ArrayList<Double> exeTime = new ArrayList<Double>();
				for (long i = 0; i < timeCalled.get(path); i++) {
					exeTime.add(config.getDouble("Execution_Time." + path +"."+ i, 0L));
				}
				
				timeImpact.put(path, exeTime);
			}
		}
		
		console.sendMessage(ChatColor.GOLD + "PerfImpact successfully loaded ^^");
	}

	@Override
	public void onDisable() {
		String pathList = "";
		for (String path : timeImpact.keySet()) {
			pathList += path;
			pathList += ":";
			
			for (int i = 1; i < timeImpact.getOrDefault(path,new ArrayList<>()).size(); i++) {
				config.set("Execution_Time."+ path +"."+ i, timeImpact.get(path).get(i));
			}

			config.set("CallCount."+ path,timeCalled.getOrDefault(path,0L));
		}
		
		config.set("PathList", pathList);
	}
	
	public boolean setCapturing(boolean isCapturing) {
		this.isCapturing = isCapturing;
		return isCapturing;
	}
	
	public boolean isCapturing() {
		return isCapturing;
	}
}
