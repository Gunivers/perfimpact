package fr.az.perfimpact.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import fr.az.perfimpact.Main;
import fr.az.perfimpact.util.ErrorHandler;

@SuppressWarnings("unused")
public class Pf_start extends ErrorHandler implements CommandExecutor 
{
	private Main main;
	
	public Pf_start(Main main)
	{
		this.main = main;
		help = ChatColor.GOLD + "pf" + ChatColor.DARK_BLUE + "start" + ChatColor.GREEN + "<path>";
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		errorLevel = 0;
		
		if (args.length < 1)
			errorOccured();
		
		if (sendHelp(sender)) return true;
		
		StringBuilder path = new StringBuilder("");
		
		for (String arg : args)
		{
			path.append(arg + " ");
		}
		
		
		return false;
	}
}
