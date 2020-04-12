package com.ben.hbrewritten;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointsCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String args[])
	{
		/*
		 * 	COMMAND:
		 *  /points <set/revert> player <amount 0-maxint>
		 *  arg idx:     0          1          2
		 */
		
		Player senderPlayer = null;
		if (sender instanceof Player)
		{
			senderPlayer = (Player) sender;
			if (!senderPlayer.isOp())
			{
				senderPlayer.sendMessage(ChatColor.RED + "You don't have permissions to do that!");
				return false;
			}
		}
		else
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "This command cannot be executed via the console!");
			return false;
		}
		
		if (args.length == 0)
		{
			senderPlayer.sendMessage(ChatColor.GRAY + "/points <set/revert> <playername> <point amount if setting>");
			return false;
		}
		
		Player targetPlayer = null;
		for (Player p : Bukkit.getOnlinePlayers())
		{
			if (p.getName().equals(args[1]))
			{
				targetPlayer = p;
			}
		}
		
		if (args[0].equalsIgnoreCase("set"))
		{
			int pointsToSet = Integer.parseInt(args[2]);
			//Queries.assignTempRank(targetPlayer, pointsToSet);
		}
		else if (args[0].equalsIgnoreCase("revert"))
		{
			//Queries.assignRank(targetPlayer);
		}
		
		return false;
	}
}
