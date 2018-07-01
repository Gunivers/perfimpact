package fr.az.perfimpact;

import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;

import fr.az.perfimpact.util.TimerTask;
import fr.az.perfimpact.util.node.Node;

public class Main extends JavaPlugin
{
	private Node registered = new Node("");
	private HashMap<String, TimerTask> measuring = new HashMap<>();
	
	public Node getRegistered()
	{
		return registered;
	}
	
	public HashMap<String, TimerTask> getMeasuring()
	{
		return measuring;
	}
}
