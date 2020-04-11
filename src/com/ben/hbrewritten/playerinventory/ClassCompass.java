package com.ben.hbrewritten.playerinventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ClassCompass
{
	private ItemStack compass;
	private ItemMeta compassMeta;
	
	public ClassCompass()
	{
		compass = new ItemStack(Material.COMPASS);
		compassMeta = compass.getItemMeta();
		
		compassMeta.setDisplayName(ChatColor.WHITE + "Chose " + ChatColor.YELLOW + "" + ChatColor.BOLD + "Class");
		compass.setItemMeta(compassMeta);
	}
	
	public ItemStack getItem()
	{
		return compass;
	}
	
}
