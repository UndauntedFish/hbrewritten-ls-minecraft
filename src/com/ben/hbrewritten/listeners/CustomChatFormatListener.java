package com.ben.hbrewritten.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ben.hbrewritten.RankManager;
import com.ben.hbrewritten.enums.Rank;


public class CustomChatFormatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		
		String format = ChatColor.YELLOW + "<points>" + ChatColor.RESET + " | <rank> " + ChatColor.BLUE + "<name> " + ChatColor.DARK_GRAY + "»" + "" + ChatColor.RESET + " <message>";
		
		format = format.replace("<points>", RankManager.getPointsFromDB(player) + ""); //eventually replace with player_data class
		format = format.replace("<rank>", Rank.setRankFromPoints(RankManager.getPointsFromDB(player)).getDisplayName());
		format = format.replace("<name>", "%1$s");
		format = format.replace("<message>", "%2$s");
		
		e.setFormat(format);
		e.setMessage(e.getMessage().replaceAll("&", "§")); 
		/*
		 *  If the player's actual name is including the format <points> | Herobrine | <name>
		 *  Then do all of the chat formatting here instead of in the rankmanager.
		 */
	}
}
