package fr.az.perfimpact.util;

import org.bukkit.command.CommandSender;

public interface Help
{
	public String getHelp(CommandSender sender);
	
	public default void sendHelp(CommandSender sender)
	{
		sender.sendMessage(getHelp(sender));
	}
}
