package com.ben.hbrewritten.GUIs;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class RightclickListener implements Listener
{
	
	@EventHandler
	public void onItemRightClick(PlayerInteractEvent e)
	{
		Player player = e.getPlayer();
		
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
		{
			if (player.getInventory().getItemInMainHand().getType() != null 
					&& player.getInventory().getItemInMainHand().getType() == Material.COMPASS)
			{
				ClassSelectionGUI.apply(player);
			}
		}
		return;
	}
}
