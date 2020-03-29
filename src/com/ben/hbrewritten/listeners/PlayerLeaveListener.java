package com.ben.hbrewritten.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.ben.hbrewritten.HerobrinePassListener;

public class PlayerLeaveListener implements Listener
{
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		
		if (HerobrinePassListener.isHerobrine(player))
		{
			HerobrinePassListener.removeHBPass(player);
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		
		if (HerobrinePassListener.isHerobrine(player))
		{
			HerobrinePassListener.removeHBPass(player);
		}
	}
}
