package com.ben.hbrewritten.listeners;

import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ben.hbrewritten.HerobrinePassListener;
import com.ben.hbrewritten.Main;
import com.ben.hbrewritten.PlayerData;

import net.md_5.bungee.api.ChatColor;

public class PlayerLeaveListener implements Listener
{
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		
		if (HerobrinePassListener.isHerobrine(player))
		{
			HerobrinePassListener.removeHBPass(player);
		}
		
		Main.getInstance().playerData.remove(PlayerData.getPlayerData(player));
		e.setLeaveMessage(ChatColor.GOLD.toString() + player.getName() + ChatColor.RESET + " doesn't want anymore herby.");
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		
		if (HerobrinePassListener.isHerobrine(player))
		{
			HerobrinePassListener.removeHBPass(player);
		}
		
		Main.getInstance().playerData.remove(PlayerData.getPlayerData(player));
		e.setQuitMessage(ChatColor.GOLD.toString() + player.getName() + ChatColor.RESET + " doesn't want anymore herby.");
	}
}
