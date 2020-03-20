package com.ben.hbrewritten.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ben.hbrewritten.Main;

public class DatabaseListener implements Listener
{	
	private String table;
	
	// player table attributes
	private String uuid, rank, active_class, first_login; 
	private int points, shard_captures, kills_as_hb, deaths_as_hb, kills_as_s, deaths_as_s;
		
	public DatabaseListener(Main main)
	{				
		table = main.getConfig().getString("table");
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		uuid = player.getUniqueId().toString();
		rank = "CASPER";
		active_class = "PRIEST";
		points = 0;
		shard_captures = 0;
		kills_as_hb = 0;
		deaths_as_hb = 0;
		kills_as_s = 0;
		deaths_as_s = 0;
		
		// Setting first login date
		Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		first_login = currentTime.toString();
		
		/* DEBUG PURPOSES: Print query inserts
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
		*/
		
		if (!Database.isInDb(uuid))
		{
			// Inserts the player's data into the db as if they never logged on before
			String sql1 = "INSERT INTO player(uuid) ";
				  sql1 += "VALUES(?)";
			
			String sql2 = "INSERT INTO hbstats(uuid) ";
				  sql2 += "VALUES(?)";
			
			String sql3 = "INSERT INTO hb_prem_classes(uuid) ";
				  sql3 += "VALUES(?)";
			
			try
			{
				PreparedStatement ps1 = Main.db.connection.prepareStatement(sql1);
				ps1.setString(1, uuid);
				ps1.executeUpdate();
				
				PreparedStatement ps2 = Main.db.connection.prepareStatement(sql2);
				ps2.setString(1, uuid);
				ps2.executeUpdate();
				
				PreparedStatement ps3 = Main.db.connection.prepareStatement(sql3);
				ps3.setString(1, uuid);
				ps3.executeUpdate();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			
			/* REPLACED BY PREPAREDSTATEMENT
			Statement stmt1, stmt2;
			try
			{
				stmt1 = Database.connection.createStatement();
				stmt1.executeQuery("INSERT INTO " + table + " VALUES('" + uuid + "', " + points + ", '" + first_login + "', " + shard_captures + ", '" + rank + "', " + kills_as_hb + ", " + deaths_as_hb + ", " + kills_as_s + ", " + deaths_as_s + ", '" + active_class + "');");
				
				stmt2 = Database.connection.createStatement();
				stmt2.executeQuery("INSERT INTO prem_classes(uuid) VALUES('" + uuid + "');");
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
			*/ 
			
		}
		
		
	}
}
