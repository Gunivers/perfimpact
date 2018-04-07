package fr.az.perfimpact.commands;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import fr.az.perfimpact.util.TimerTask;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.az.perfimpact.Main;
import fr.az.perfimpact.util.Node;

public class PerfImpactCommandExecutor implements CommandExecutor {

	private Main main;
	
	public PerfImpactCommandExecutor(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (args.length == 0) {
			cmd.execute(sender, label, new String[] {"help"});
			return true;
		}
		
		String path = "";
		
		for (int i = 1; i < args.length; i++) {
			path += args[i];
		}
		
		if (args[0] == "capture") {
			if (args.length == 1) {
				sender.sendMessage("§6Capture enabled: §2" + main.isCapturing());
				return true;
			}
			
			if (args[1] == "start") {
				main.setCapturing(true);
			}
			else if (args[1] == "stop") {
				main.setCapturing(false);
			}
			else cmd.execute(sender, label, new String[] {"help","capture"});
				
			return true;
		}
		
		if (args[0] == "start") {
				if (args.length == 1) {
					cmd.execute(sender, label, new String[] {"help","start"});
					return true;
				}
				
				main.timeCalled.put(path, main.timeCalled.getOrDefault(path, 0L) +1);
				
				main.timing.put(path, new Timer());
				main.timer.put(path, new TimerTask());
				
				main.timing.get(path).schedule(main.timer.get(path), 1L);
				
				sender.sendMessage("§6Timer successfully started");
				return true;
		}
		
		if (args[0] == "stop" ) {
			if (args.length == 1) {
				cmd.execute(sender,label,new String[] {"help","start"});
				return true;
			}
			
			main.timing.getOrDefault(path, new Timer()).cancel();
			main.timer.getOrDefault(path, new TimerTask()).cancel();
			
			long exeTime = main.timer.getOrDefault(path, new TimerTask()).getTime();
			
			ArrayList<Long> timeImpact = main.timeImpact.getOrDefault(path, new ArrayList<Long>());
			timeImpact.add(exeTime);
			main.timeImpact.put(path, timeImpact);
			
			main.timing.remove(path);
			main.timer.remove(path);
			
			sender.sendMessage("§6The function "+ path +" was executed in §2"+ exeTime +"s");
			return true;
		}
		
		if (args[0] == "resume") {
			boolean isCapturing = main.isCapturing();
			main.setCapturing(false);
			
			if (args.length == 1) {
			
				long totalExeTime = 0L;
				
				for (ArrayList<Long> temp : main.timeImpact.values()) {
					for (long impact : temp) {
						totalExeTime += impact;
					}
				}
				
				Node base = new Node("");
				
				for (String pathway : main.timeCalled.keySet()) {
					
					Node current = base;
					for (String tag : pathway.split("/")) {
						
						Recursive : while (true) {
							Node previous = current;
							
							for (Node child : (Node[]) current.getChildren().toArray()) {
								if (child.getPath() == tag) {
									current = child;
									
									if (child.getPath().endsWith(".mcfunction")) {
										child.setCallCount(main.timeCalled.get(pathway));
										
										for (int i = 0; i < child.getCallCount(); i++) {
											child.setExecutionTime(child.getExecutionTime() +main.timeImpact.get(pathway).get(i));
										}
										
										child.setImpact(child.getExecutionTime() / totalExeTime);
										child.setExecutionTime(child.getExecutionTime() / child.getCallCount());
									}
									
									break Recursive;
								}
							}
							
							if (previous.equals(current)) {
								current.addChild(new Node(tag));
							}
						}
					}
				}
				
				ArrayList<String> Tree = base.getTree();
				
				
				
				try {
					FileWriter fw = new FileWriter("perfimpact-log.txt");
					for (String line : Tree) fw.write(line);
					fw.close();
					
				} catch (IOException e) {e.printStackTrace();}
				
				return true;
			}
			
			else {
				double mean = 0;
				for (long exeTime : main.timeImpact.get(path)) {
					mean += exeTime;
				}
				mean /= main.timeImpact.get(path).size();
				
				long callCount = main.timeCalled.get(path);
				ArrayList<Long> exeTime = main.timeImpact.get(path);
				
				sender.sendMessage("§5Function "+ path +"\n§6Mean of execution time: §4"+ mean + "§6; Total Calls: §4" + callCount);
				sender.sendMessage("§6Execution Times: ");
				
				for (long i : exeTime) {
					sender.sendMessage("§4"+ i +"§6; ");
				}
				
				main.setCapturing(isCapturing);
				return true;
			}
		}
		
		if (args[0] == "help") {
			sender.sendMessage("§2The help of '§6"+path+"§2' is coming soon !");
			return true;
		}
	

		return false;
	}

}
