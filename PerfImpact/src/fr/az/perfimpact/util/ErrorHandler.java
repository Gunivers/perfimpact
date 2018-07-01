package fr.az.perfimpact.util;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class ErrorHandler
{
	protected int errorLevel = 0;
	protected String help;

	public void errorOccured()
	{
		this.errorLevel++;
	}

	public void errorOccured(int errorLevel)
	{
		this.errorLevel += errorLevel;
	}

	public boolean sendHelp(CommandSender sender)
	{
		if (errorLevel > 0)
		{
			sender.sendMessage(ChatColor.DARK_RED + "Error Level: " + errorLevel + "\n" + ChatColor.RESET + help);
			return true;
		}
		
		return false;
	}
}
