package com.ben.hbrewritten.GUIs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.ben.hbrewritten.EnchGlow;
import com.ben.hbrewritten.Main;

public class ClassSelectionGUI
{
	private static Main main = Main.getInstance();
	
	/* ITEMSTACKS DECLARATION */
	
	private static ItemStack scout = null, archer = null, priest = null, wizard = null, demo = null, 
			mage = null, sorceror = null, paladin = null;
	
	private static LeatherArmorMeta scoutMeta = null, archerMeta = null, priestMeta = null, wizardMeta = null, demoMeta = null, 
			mageMeta = null, sorcerorMeta = null, paladinMeta = null;
	
	
	/*
	 * Displays the class selection GUI to a specified player.
	 */
	public static void apply(Player player)
	{
		/* BEGINNING */
		Inventory gui = Bukkit.createInventory(null, 18, "Pick your class");
		
		/* LORES */
		
		/* ITEMSTACKS SETTING */
		loadDefaultState();
		
		if (!getActiveClass(player).equals(null))
		{
			// selected state: if a player already has a class active, mark it as selected. All else is deselected.
			selectClass(getActiveClass(player)); // updates newly active class in the GUI
		}
		
		/* ITEM SETTING */
		gui.setItem(2, archer);
		gui.setItem(3, priest);
		gui.setItem(4, scout);
		gui.setItem(5, wizard);
		gui.setItem(6, demo);
		
		gui.setItem(12, mage);
		gui.setItem(13, paladin);
		gui.setItem(14, sorceror);
		
		// FINAL
        player.openInventory(gui);
	}
	
	/*
	 * SQL Query that fetches the player's active class from the database.
	 */
	public static String getActiveClass(Player player)
	{		
		String sql = "SELECT active_class ";
			  sql += "FROM hbstats ";
			  sql += "WHERE uuid = ?";
		try
		{
			PreparedStatement ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, player.getUniqueId().toString());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				return rs.getString(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * SQL Query that updates the player's active class in the database.
	 */
	public static void setActiveClass(Player player, String myclass)
	{
		String sql = "UPDATE hbstats ";
			  sql += "SET active_class = ? ";
			  sql += "WHERE uuid = ?";
		
		try
		{
			PreparedStatement ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, myclass);
			ps.setString(2, player.getUniqueId().toString());
			ps.executeUpdate();
		} catch (SQLException e)
		{
			Bukkit.getConsoleSender().sendMessage("[HBR] Error while setting the class: " + myclass + "! Is it a vaild class?");
			e.printStackTrace();
		}
	}
	
	/*
	 * Helper function to applyClassSelectionGUI().
	 * Will take in a class and set it as selected in the GUI.
	 */
	public static void selectClass(String myclass)
	{
		loadDefaultState();
		
		// Enchantment glow setup
		EnchGlow glow = new EnchGlow(main.key);
		
		switch (myclass)
		{
			case "SCOUT":
				scoutMeta.addEnchant(glow, 1, true);
				scout.setItemMeta(scoutMeta);
				break;
			case "ARCHER":
				archerMeta.addEnchant(glow, 1, true);
				archer.setItemMeta(archerMeta);
				break;
			case "PRIEST":
				priestMeta.addEnchant(glow, 1, true);
				priest.setItemMeta(priestMeta);
				break;
			case "WIZARD":
				wizardMeta.addEnchant(glow, 1, true);
				wizard.setItemMeta(wizardMeta);
				break;
			case "DEMO":
				demoMeta.addEnchant(glow, 1, true);
				demo.setItemMeta(demoMeta);
				break;
			case "MAGE":
				mageMeta.addEnchant(glow, 1, true);
				mage.setItemMeta(mageMeta);
				break;
			case "PALADIN":
				paladinMeta.addEnchant(glow, 1, true);
				paladin.setItemMeta(paladinMeta);
				break;
			case "SORCEROR":
				sorcerorMeta.addEnchant(glow, 1, true);
				sorceror.setItemMeta(sorcerorMeta);
				break;
			default:
				return;
		}
	}
	
	/*
	 * Helper function to applyClassSelectionGUI(). 
	 * Restores GUI state to default, deselecting everything.
	 */
	public static void loadDefaultState()
	{
		archer = new ItemStack(Material.LEATHER_HELMET);
		archerMeta = (LeatherArmorMeta) archer.getItemMeta();
		archerMeta.setDisplayName(ChatColor.WHITE + "Archer");
		archerMeta.setColor(Color.fromRGB(102, 127, 51));
		archer.setItemMeta(archerMeta);
		
		priest = new ItemStack(Material.LEATHER_HELMET);
		priestMeta = (LeatherArmorMeta) priest.getItemMeta();
		priestMeta.setDisplayName(ChatColor.WHITE + "Priest");
		priestMeta.setColor(Color.fromRGB(255, 255, 255));
		priest.setItemMeta(priestMeta);
		
		scout = new ItemStack(Material.LEATHER_HELMET);
		scoutMeta = (LeatherArmorMeta) scout.getItemMeta();
		scoutMeta.setDisplayName(ChatColor.WHITE + "Scout");
		scoutMeta.setColor(Color.fromRGB(229, 229, 51));
		scout.setItemMeta(scoutMeta);

		wizard = new ItemStack(Material.LEATHER_HELMET);
		wizardMeta = (LeatherArmorMeta) wizard.getItemMeta();
		wizardMeta.setDisplayName(ChatColor.WHITE + "Wizard");
		wizardMeta.setColor(Color.fromRGB(127, 63, 178));
		wizard.setItemMeta(wizardMeta);

		demo = new ItemStack(Material.LEATHER_HELMET);
		demoMeta = (LeatherArmorMeta) demo.getItemMeta();
		demoMeta.setDisplayName(ChatColor.WHITE + "Demo");
		demoMeta.setColor(Color.WHITE);
		demo.setItemMeta(demoMeta);

		mage = new ItemStack(Material.LEATHER_HELMET);
		mageMeta = (LeatherArmorMeta) mage.getItemMeta();
		mageMeta.setDisplayName(ChatColor.WHITE + "Mage");
		mageMeta.setColor(Color.fromRGB(102, 153, 216));
		mage.setItemMeta(mageMeta);
		
		paladin = new ItemStack(Material.LEATHER_HELMET);
		paladinMeta = (LeatherArmorMeta) paladin.getItemMeta();
		paladinMeta.setDisplayName(ChatColor.WHITE + "Paladin");
		paladinMeta.setColor(Color.fromRGB(216, 127, 51));
		paladin.setItemMeta(paladinMeta);
		
		sorceror = new ItemStack(Material.LEATHER_HELMET);
		sorcerorMeta = (LeatherArmorMeta) sorceror.getItemMeta();
		sorcerorMeta.setDisplayName(ChatColor.WHITE + "Sorceror");
		sorcerorMeta.setColor(Color.fromRGB(153, 51, 51));
		sorceror.setItemMeta(sorcerorMeta);
	}
}
