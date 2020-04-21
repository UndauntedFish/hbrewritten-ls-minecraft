package com.ben.hbrewritten.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.ben.hbrewritten.Main;
import com.ben.hbrewritten.PlayerData;


public class CustomChatFormatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		PlayerData playerData = null;
		for (PlayerData pd : Main.getInstance().playerData)
		{
			if (pd.getPlayer().equals(player))
			{
				playerData = pd;
			}
		}
		
		String format = ChatColor.YELLOW + "<points>" + ChatColor.DARK_GRAY + "▐" + ChatColor.RESET + " <rank> " + ChatColor.BLUE + "<name> " + ChatColor.DARK_GRAY + "»" + "" + ChatColor.RESET + " <message>";
		
		format = format.replace("<points>", playerData.getPoints() + ""); //eventually replace with player_data class
		format = format.replace("<rank>", playerData.getRank().getDisplayName());
		format = format.replace("<name>", "%1$s");
		format = format.replace("<message>", "%2$s");
		
		e.setFormat(format);
		e.setMessage(e.getMessage().replaceAll("&", "§")); 
	}
}
