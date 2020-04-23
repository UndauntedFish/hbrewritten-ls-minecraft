package com.ben.hbrewrittenls.GUIs;

import com.ben.hbrewrittenls.EnchGlow;
import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import com.ben.hbrewrittenls.enums.ClassData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ClassSelectionGUI
{
	private static Main main = Main.getInstance();
	
	/* ITEMSTACKS DECLARATION */
	
	private static ItemStack scout = null, archer = null, priest = null, wizard = null, demo = null,
			mage = null, sorcerer = null, paladin = null;
	
	private static LeatherArmorMeta scoutMeta = null, archerMeta = null, priestMeta = null, wizardMeta = null, demoMeta = null,
			mageMeta = null, sorcererMeta = null, paladinMeta = null;
	
	
	/*
	 * Displays the class selection GUI to a specified player.
	 */
	public static void apply(Player player)
	{
		PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());

		/* BEGINNING */
		Inventory gui = Bukkit.createInventory(null, 18, "Pick your class");
		
		/* LORES */
		
		/* ITEMSTACKS SETTING */
		loadDefaultState();

		String activeClass = pd.getActiveClass();
		if (!activeClass.equals(null))
		{
			// selected state: if a player already has a class active, mark it as selected. All else is deselected.
			selectClass(activeClass); // updates newly active class in the GUI
		}
		
		/* ITEM SETTING */
		gui.setItem(2, archer);
		gui.setItem(3, priest);
		gui.setItem(4, scout);
		gui.setItem(5, wizard);
		gui.setItem(6, demo);
		
		gui.setItem(12, mage);
		gui.setItem(13, paladin);
		gui.setItem(14, sorcerer);
		
		// FINAL
        player.openInventory(gui);
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
			case "SORCERER":
				sorcererMeta.addEnchant(glow, 1, true);
				sorcerer.setItemMeta(sorcererMeta);
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
		archerMeta.setDisplayName(ClassData.ARCHER.getName());
		archerMeta.setColor(ClassData.ARCHER.getHelmetColor());
		archer.setItemMeta(archerMeta);
		
		priest = new ItemStack(Material.LEATHER_HELMET);
		priestMeta = (LeatherArmorMeta) priest.getItemMeta();
		priestMeta.setDisplayName(ClassData.PRIEST.getName());
		priestMeta.setColor(ClassData.PRIEST.getHelmetColor());
		priest.setItemMeta(priestMeta);
		
		scout = new ItemStack(Material.LEATHER_HELMET);
		scoutMeta = (LeatherArmorMeta) scout.getItemMeta();
		scoutMeta.setDisplayName(ClassData.SCOUT.getName());
		scoutMeta.setColor(ClassData.SCOUT.getHelmetColor());
		scout.setItemMeta(scoutMeta);

		wizard = new ItemStack(Material.LEATHER_HELMET);
		wizardMeta = (LeatherArmorMeta) wizard.getItemMeta();
		wizardMeta.setDisplayName(ClassData.WIZARD.getName());
		wizardMeta.setColor(ClassData.WIZARD.getHelmetColor());
		wizard.setItemMeta(wizardMeta);

		demo = new ItemStack(Material.LEATHER_HELMET);
		demoMeta = (LeatherArmorMeta) demo.getItemMeta();
		demoMeta.setDisplayName(ClassData.DEMO.getName());
		demoMeta.setColor(ClassData.DEMO.getHelmetColor());
		demo.setItemMeta(demoMeta);

		mage = new ItemStack(Material.LEATHER_HELMET);
		mageMeta = (LeatherArmorMeta) mage.getItemMeta();
		mageMeta.setDisplayName(ClassData.MAGE.getName());
		mageMeta.setColor(ClassData.MAGE.getHelmetColor());
		mage.setItemMeta(mageMeta);
		
		paladin = new ItemStack(Material.LEATHER_HELMET);
		paladinMeta = (LeatherArmorMeta) paladin.getItemMeta();
		paladinMeta.setDisplayName(ClassData.PALADIN.getName());
		paladinMeta.setColor(ClassData.PALADIN.getHelmetColor());
		paladin.setItemMeta(paladinMeta);
		
		sorcerer = new ItemStack(Material.LEATHER_HELMET);
		sorcererMeta = (LeatherArmorMeta) sorcerer.getItemMeta();
		sorcererMeta.setDisplayName(ClassData.SORCERER.getName());
		sorcererMeta.setColor(ClassData.SORCERER.getHelmetColor());
		sorcerer.setItemMeta(sorcererMeta);
	}
}
