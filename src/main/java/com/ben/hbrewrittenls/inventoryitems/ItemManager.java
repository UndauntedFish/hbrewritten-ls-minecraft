package com.ben.hbrewrittenls.inventoryitems;

import org.bukkit.entity.Player;

public class ItemManager
{
	private static ClassCompass compass = new ClassCompass();
	private static HBRBook hbrBook = new HBRBook();
	private static RuleBook ruleBook = new RuleBook();
	
	public static void addItemsToPlayerInventory(Player player)
	{
		player.getInventory().clear();
		player.getInventory().setItem(0, compass.getItem());
		player.getInventory().setItem(1, ruleBook.getItem());
		player.getInventory().setItem(2, hbrBook.getItem());
	}
}
