package com.ben.hbrewritten.GUIs;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener
{
	@EventHandler
	public void onGUIClick(InventoryClickEvent e)
	{
		Player player = (Player) e.getWhoClicked();

        // If opened GUI's title is equal to that of our GUI, don't let the player take any items from there
        if (ChatColor.translateAlternateColorCodes('&', e.getView().getTitle()).equals("Please select a class:"))
        {
            if (e.getCurrentItem() != null)
            {
                e.setCancelled(true);
            }
            else
            {
            	// This makes sure that no error is thrown if the player selects an empty slot in the GUI
                return;
            }
            
            // Send the player a chat message/ding sound confirming them of their selected class.
            String activeClass = ClassSelectionGUI.getActiveClass(player);
            
            switch (activeClass)
            {
                case "SCOUT":
                	player.sendMessage(ChatColor.GOLD + "Class set to Scout!");
                    break;
                case "ARCHER":
                	player.sendMessage(ChatColor.GOLD + "Class set to Archer!");
                    break;
                case "PRIEST":
                	player.sendMessage(ChatColor.GOLD + "Class set to Priest!");
                    break;
    			case "WIZARD":
    				player.sendMessage(ChatColor.GOLD + "Class set to Wizard!");
                    break;
    			case "DEMO":
    				player.sendMessage(ChatColor.GOLD + "Class set to Demo!");
                    break;
    			case "MAGE":
    				player.sendMessage(ChatColor.GOLD + "Class set to Mage!");
                    break;
    			case "PALADIN":
    				player.sendMessage(ChatColor.GOLD + "Class set to Paladin!");
                    break;
    			case "SORCEROR":
    				player.sendMessage(ChatColor.GOLD + "Class set to Sorceror!");
                    break;
                default:
                    return;
            }
        	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

        }
	}
}
