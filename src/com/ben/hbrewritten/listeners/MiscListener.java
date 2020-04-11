package com.ben.hbrewritten.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class MiscListener implements Listener
{
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemMove(InventoryClickEvent e)
	{
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent e)
	{
		e.setCancelled(true);
	}
	
}
