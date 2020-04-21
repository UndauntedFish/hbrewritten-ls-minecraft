package com.ben.hbrewrittenls.inventoryitems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class HBRBook
{
	private ItemStack HBRBook;
	private BookMeta HBRBookMeta;
	
	public HBRBook()
	{
		HBRBook = new ItemStack(Material.WRITTEN_BOOK);
		HBRBookMeta = (BookMeta) HBRBook.getItemMeta();
		
		HBRBookMeta.setTitle(ChatColor.YELLOW + "Herobrine " + ChatColor.BLUE + "" + ChatColor.BOLD + "Rewritten");
		HBRBookMeta.setAuthor("UndauntedPhish");
		HBRBookMeta.addPage(
			ChatColor.BOLD + "Welcome to HBR\n\n" + ChatColor.RESET + "" + ChatColor.DARK_BLUE +
			"I present you a complete rewrite of the imfamous Herobrine minigame found on The Hive!\n\n" + 
			"HBR is still unreleased, so things may get buggy. Hang tight!",
			
			ChatColor.BLACK + "" + ChatColor.BOLD + "Legal:\n\n" + ChatColor.RESET + "" + ChatColor.DARK_BLUE +
			"HBR is an independent recreation of TheHerobrine and it is not affiliated with The Hive, TheHerobrine, or TheHerobrine V2.\n\n" + 
			"HBR was rewritten as an educational instance. We do not hold copyright for any of the files."
		);
		
		HBRBook.setItemMeta(HBRBookMeta);
	}
	
	public ItemStack getItem()
	{
		return HBRBook;
	}
}
