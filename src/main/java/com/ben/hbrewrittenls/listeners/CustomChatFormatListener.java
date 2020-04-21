package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;


public class CustomChatFormatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		PlayerData playerData = Main.getInstance().playerDataMap.get(uuid);
		
		String format = ChatColor.YELLOW + "<points>" + ChatColor.DARK_GRAY + "▐" + ChatColor.RESET + " <rank> " + ChatColor.BLUE + "<name> " + ChatColor.DARK_GRAY + "»" + "" + ChatColor.RESET + " <message>";
		format = format.replace("<points>", playerData.getPoints() + ""); //eventually replace with player_data class
		format = format.replace("<rank>", playerData.getRank().getDisplayName());
		format = format.replace("<name>", "%1$s");
		format = format.replace("<message>", "%2$s");
		
		e.setFormat(format);
		e.setMessage(e.getMessage().replaceAll("&", "§")); 
	}
}
