package com.ben.hbrewritten;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	public Database db;
	
	@Override
	public void onEnable()
	{
		loadConfig();
		
		// Event Handlers
		Bukkit.getPluginManager().registerEvents(new DatabaseListener(this), this);
		
		// MySQL database creation
		db = new Database(this);
		
	}
	
	private void loadConfig()
    {
    	this.getConfig().options().copyDefaults();
		saveDefaultConfig();
    }
}
