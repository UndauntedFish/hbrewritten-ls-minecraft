package com.ben.hbrewritten.GUIs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
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
	private static Main main = new Main();
	
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
		Inventory gui = Bukkit.createInventory(null, 27, "Please select a class:");
		
		/* LORES */
		
		/* ITEMSTACKS SETTING */
		if (!getActiveClass(player).equals(null))
		{
			// selected state: if a player already has a clas active, mark it as selected. All else is deselected.
			
			setActiveClass(player, getActiveClass(player)); // updates newly active class in MySQL database
			selectClass(getActiveClass(player)); // updates newly active class in the GUI
		}
		else
		{
			// default state: if a player has no active class, all classes are marked as deselected.
				// this should never actually happen, since the default class is always scout.
			loadDefaultState();
		}
		
		/* ITEM SETTING */
		gui.setItem(2, scout);
		gui.setItem(3, archer);
		gui.setItem(4, priest);
		gui.setItem(5, wizard);
		gui.setItem(6, demo);
		
		gui.setItem(12, mage);
		gui.setItem(13, sorceror);
		gui.setItem(14, paladin);
		
		// FINAL
        player.openInventory(gui);
	}
	
	/*
	 * SQL Query that fetches the player's active class from the database.
	 */
	public static String getActiveClass(Player player)
	{
		/*
		 * Query:
		 * SELECT active_class as activeClass 
		 * FROM player 
		 * WHERE uuid = '<input UUID>';
		 */
		try
		{
			Statement stmt = Main.db.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT active_class AS activeClass FROM player WHERE uuid = '" + player.getUniqueId() + "';");
			return rs.getString("activeClass");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	private static void setActiveClass(Player player, String myclass)
	{
		/*
		 * Query:
		 * UPDATE player 
		 * SET active_class = '<input Class such as PALADIN>' 
		 * WHERE uuid = '<input UUID>';
		 */
		try
		{
			Statement stmt = Main.db.connection.createStatement();
			stmt.executeUpdate("UPDATE player SET active_class = '" + myclass + "' WHERE uuid = '" + player.getUniqueId() + "';");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/*
	 * Helper function to applyClassSelectionGUI().
	 * Will take in a class and set it as selected in the GUI.
	 */
	private static void selectClass(String myclass)
	{
		loadDefaultState();
		
		// Enchantment glow setup
		EnchGlow glow = new EnchGlow(main.key);
		
		switch (myclass)
		{
			case "SCOUT":
				scoutMeta.addEnchant(glow, 1, true);
				break;
			case "ARCHER":
				archerMeta.addEnchant(glow, 1, true);
				break;
			case "PRIEST":
				priestMeta.addEnchant(glow, 1, true);
				break;
			case "WIZARD":
				wizardMeta.addEnchant(glow, 1, true);
				break;
			case "DEMO":
				demoMeta.addEnchant(glow, 1, true);
				break;
			case "MAGE":
				mageMeta.addEnchant(glow, 1, true);
				break;
			case "PALADIN":
				paladinMeta.addEnchant(glow, 1, true);
				break;
			case "SORCEROR":
				sorcerorMeta.addEnchant(glow, 1, true);
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
		scout = new ItemStack(Material.LEATHER_HELMET);
		scoutMeta = (LeatherArmorMeta) scout.getItemMeta();
		scoutMeta.setColor(Color.YELLOW);
		
		archer = new ItemStack(Material.LEATHER_HELMET);
		archerMeta = (LeatherArmorMeta) archer.getItemMeta();
		archerMeta.setColor(Color.GREEN);

		priest = new ItemStack(Material.LEATHER_HELMET);
		priestMeta = (LeatherArmorMeta) priest.getItemMeta();
		priestMeta.setColor(Color.SILVER);

		wizard = new ItemStack(Material.LEATHER_HELMET);
		wizardMeta = (LeatherArmorMeta) wizard.getItemMeta();
		wizardMeta.setColor(Color.FUCHSIA);

		demo = new ItemStack(Material.LEATHER_HELMET);
		demoMeta = (LeatherArmorMeta) demo.getItemMeta();
		demoMeta.setColor(Color.WHITE);

		mage = new ItemStack(Material.LEATHER_HELMET);
		mageMeta = (LeatherArmorMeta) mage.getItemMeta();
		mageMeta.setColor(Color.fromRGB(173, 216, 230)); // Light blue #add8e6
		
		paladin = new ItemStack(Material.LEATHER_HELMET);
		paladinMeta = (LeatherArmorMeta) paladin.getItemMeta();
		paladinMeta.setColor(Color.ORANGE);
		
		sorceror = new ItemStack(Material.LEATHER_HELMET);
		sorcerorMeta = (LeatherArmorMeta) sorceror.getItemMeta();
		sorcerorMeta.setColor(Color.RED);
	}
}
