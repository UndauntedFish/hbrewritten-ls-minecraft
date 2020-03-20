package com.ben.hbrewritten.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

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
                Color.AQUA.asRGB();
            	return;
            }
            
            // Send the player a chat message/ding sound confirming them of their selected class.
            ItemStack clickedItem = e.getCurrentItem();
            LeatherArmorMeta clickedItemMeta = (LeatherArmorMeta) clickedItem.getItemMeta();
            String clickedItemName = clickedItemMeta.getDisplayName();
            
            // If the player is already the class they selected, do nothing.
            // If the player chose a new class, update that info in the db.
            switch (clickedItemName)
            {
                case "Scout":
                	if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Scout!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Scout!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
                    break;
                case "Archer":
                	if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Archer!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Archer!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
                    break;
                case "Priest":
                	if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                		player.sendMessage(ChatColor.GOLD + "You have already chosen Priest!");
                	}
                	else
                	{
                		player.sendMessage(ChatColor.GOLD + "Class set to Scout!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                		ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
                	break;
    			case "Wizard":
    				if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Wizard!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Wizard!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
                    break;
    			case "Demo":
    				if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Demo!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Demo!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
    				break;
    			case "Mage":
    				if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Mage!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Mage!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}
    				break;
    			case "Paladin":
    				if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Paladin!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Paladin!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}                    
    				break;
    			case "Sorceror":
    				if (ClassSelectionGUI.getActiveClass(player).equals(clickedItemName.toUpperCase()))
                	{
                    	player.sendMessage(ChatColor.GOLD + "You have already chosen Sorceror!");
                	}
                	else
                	{
                    	player.sendMessage(ChatColor.GOLD + "Class set to Sorceror!");
                    	ClassSelectionGUI.selectClass(clickedItemName.toUpperCase());
                    	ClassSelectionGUI.setActiveClass(player, clickedItemName.toUpperCase());
                    	ClassSelectionGUI.apply(player);
                	}                    
    				break;
                default:
                	Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBR] Error at InventoryClickListener.onGUIClick(): " 
                			+ player.getName() + " tried to chose an invalid class!");
                    return;
            }
        	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);

        }
	}
}
