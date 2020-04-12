package com.ben.hbrewritten.playerinventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class RuleBook
{
	private ItemStack ruleBook;
	private BookMeta ruleBookMeta;
	
	public RuleBook()
	{
		ruleBook = new ItemStack(Material.WRITTEN_BOOK);
		ruleBookMeta = (BookMeta) ruleBook.getItemMeta();
		
		ruleBookMeta.setTitle(ChatColor.YELLOW + "The Rules");
		ruleBookMeta.setAuthor("UndauntedPhish");
		ruleBookMeta.addPage(
				ChatColor.BOLD + "Rules\n\n" + ChatColor.RESET + "" + ChatColor.DARK_BLUE + 
				"1) Respect others\n2) Don't hack!\n3) Don't abuse glitches!\n4) Don't 'camp' the shard!"
		);
		
		ruleBook.setItemMeta(ruleBookMeta);
	}
	
	public ItemStack getItem()
	{
		return ruleBook;
	}
}
