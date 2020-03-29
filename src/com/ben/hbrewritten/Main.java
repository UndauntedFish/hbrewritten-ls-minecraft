package com.ben.hbrewritten;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

import com.ben.hbrewritten.GUIs.InventoryClickListener;
import com.ben.hbrewritten.GUIs.RightclickListener;
import com.ben.hbrewritten.database.Database;
import com.ben.hbrewritten.listeners.PlayerJoinListener;
import com.ben.hbrewritten.lobbytimer.Timer;

public class Main extends JavaPlugin
{
	// Instance of this main class used throughout all classes. Accessed via Main.getInstance().
	private static Main instance;
	
	// Key used in com.ben.hbrewritten.GUIs.ClassSelectionGUI to enable enchantment glows.
	public NamespacedKey key = new NamespacedKey(this, getDescription().getName());
	
	// Database setup class used to send queries. Used throughout all classes.
	public static Database db;
	
	// Lobby timer. Accessed by the PlayerJoinListener to add players to the bossbar.
	public Timer lobbyTimer;
		
	@Override
	public void onEnable()
	{
		loadConfig();
		registerEnchGlow();
		instance = this;
		
		// Sets up database connection
		db = new Database();
		
        // Sets up new lobby timer
		lobbyTimer = new Timer();
		
		// EventHandlers
        Bukkit.getPluginManager().registerEvents(new RightclickListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new HerobrinePassListener(), this);

        // Commands
        getCommand("points").setExecutor(new PointsCommand());
	}
	
	@Override
	public void onDisable()
	{
		instance = null;
	}
	
	// Loads info from config.yml
	private void loadConfig()
    {
    	this.getConfig().options().copyDefaults();
		saveDefaultConfig();
    }
	
	// Sets up custom enchantment glow
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
	
	// Gets an instance of the Main class to be used by other classes.
	public static Main getInstance()
	{
		return instance;
	}
}
