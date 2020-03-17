package com.ben.hbrewritten;

import java.lang.reflect.Field;
import java.sql.Connection;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import com.ben.hbrewritten.GUIs.InventoryClickListener;
import com.ben.hbrewritten.GUIs.RightclickListener;

public class Main extends JavaPlugin
{
	// Key used in com.ben.hbrewritten.GUIs.ClassSelectionGUI to enable enchantment glows
	public NamespacedKey key = new NamespacedKey(this, getDescription().getName());
	
	// Connection used throughout all classes to send queries to the database as set in DatabaseListener
	public Connection connection;
	
	
	
	@Override
	public void onEnable()
	{
		loadConfig();
		registerEnchGlow();
		
		// Event Handlers
		Bukkit.getPluginManager().registerEvents(new DatabaseListener(), this);	
		Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
		Bukkit.getPluginManager().registerEvents(new RightclickListener(), this);

	}
	
	private void loadConfig()
    {
    	this.getConfig().options().copyDefaults();
		saveDefaultConfig();
    }
	
	
	public void registerEnchGlow() 
	{
        try 
        {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
        try 
        {
        	NamespacedKey key = new NamespacedKey(this, getDescription().getName());
           
            EnchGlow glow = new EnchGlow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e)
        {
        	
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
