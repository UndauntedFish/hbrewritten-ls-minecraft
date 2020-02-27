package com.ben.hbrewritten;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import net.md_5.bungee.api.ChatColor;

public class DatabaseListener implements Listener
{
	private Main main;
	private String UUID, RANK, ACTIVE_CLASS, FIRST_LOGIN; 
	private int POINTS, SHARD_CAPTURES, KILLS_AS_HB, DEATHS_AS_HB, KILLS_AS_S, DEATHS_AS_S;
	
	
	public DatabaseListener(Main main)
	{
		this.main = main;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		UUID = player.getUniqueId().toString();
		RANK = "CASPER";
		ACTIVE_CLASS = "PRIEST";
		POINTS = 0;
		SHARD_CAPTURES = 0;
		KILLS_AS_HB = 0;
		DEATHS_AS_HB = 0;
		KILLS_AS_S = 0;
		DEATHS_AS_S = 0;
		
		// Setting first login date
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		FIRST_LOGIN = currentTime.toString();
		
		// Send query
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "table: " + main.db.getTable());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "UUID: " + UUID + "Length: " + UUID.length());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "POINTS: " + POINTS);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "FIRST_LOGIN: " + FIRST_LOGIN);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "SHARD_CAPTURES: " + SHARD_CAPTURES);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "RANK: " + RANK);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "KILLS_AS_HB: " + KILLS_AS_HB);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "DEATHS_AS_HB: " + DEATHS_AS_HB);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "KILLS_AS_S: " + KILLS_AS_S);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "DEATHS_AS_S: " + DEATHS_AS_S);
		Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "ACTIVE_CLASS: " + ACTIVE_CLASS);
		
		
		try
		{
			main.db.prepareStatement("INSERT INTO " + main.db.getTable() + " VALUES('" + UUID + "', " + POINTS + ", '" + FIRST_LOGIN + "', " + SHARD_CAPTURES + ", '" + RANK + "', " + KILLS_AS_HB + ", " + DEATHS_AS_HB + ", " + KILLS_AS_S + ", " + DEATHS_AS_S + ", '" + ACTIVE_CLASS + "');").executeUpdate();
			main.db.prepareStatement("INSERT INTO prem_classes(UUID) VALUES(LAST_INSERT_ID());").executeUpdate();
		} catch (SQLException e1)
		{
			e1.printStackTrace();
		}
		
	}
}
