package fr.az.perfimpact;

import fr.az.perfimpact.commands.PerfImpactCommandExecutor;
import fr.az.perfimpact.util.TimerTask;

import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

public class Main extends JavaPlugin {

	public HashMap<String,ArrayList<Long>> timeImpact = new HashMap<>();
	public HashMap<String,Long> timeCalled = new HashMap<>();
	
	public HashMap<String,Timer> timing = new HashMap<>();
	public HashMap<String,TimerTask> timer = new HashMap<>();
	
	public FileConfiguration config;
	
	private boolean isCapturing = false;
	private ConsoleCommandSender console;
	
	@Override
	public void onLoad() {
		saveDefaultConfig();
		
		config = getConfig();
		console = getServer().getConsoleSender();
		
		if (config.get("PathList:") != null) {
			
			for(String path : config.getString("PathList","").split(":")) {
				timeCalled.put(path, config.getLong("CallCount."+ path,0L));
				
				ArrayList<Long> exeTime = new ArrayList<>();
				for (long i = 0; i < timeCalled.get(path); i++) {
					exeTime.add(config.getLong("Execution_Time." + path +"."+ i, 0L));
				}
				
				timeImpact.put(path, exeTime);
			}
		}
		
		System.out.println(ChatColor.GOLD + "PerfImpact successfully loaded ^^");
	}

	@Override
	public void onEnable()
	{
		if (getCommand("perfimpact") == null) console.sendMessage(ChatColor.GOLD + "Perfimpact command does not" +  ChatColor.DARK_RED + " EXIST");

		getCommand("perfimpact").setExecutor(new PerfImpactCommandExecutor(this));
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
