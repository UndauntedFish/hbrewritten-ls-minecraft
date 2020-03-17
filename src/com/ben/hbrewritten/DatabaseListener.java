package com.ben.hbrewritten;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ben.lobbyapi.LobbyAPI;

public class DatabaseListener implements Listener
{	
	private String table;
	private Main main = new Main();
	
	// player table attributes
	private String UUID, RANK, ACTIVE_CLASS, FIRST_LOGIN; 
	private int POINTS, SHARD_CAPTURES, KILLS_AS_HB, DEATHS_AS_HB, KILLS_AS_S, DEATHS_AS_S;
	
	private LobbyAPI lapi = new LobbyAPI();
	
	public DatabaseListener()
	{		
		lapi.db.enterHostData( 
				main.getConfig().getString("host"), 
				main.getConfig().getString("database"), 
				main.getConfig().getString("username"), 
				main.getConfig().getString("password"), 
				main.getConfig().getInt("port")
		);
		table = main.getConfig().getString("table");
		
		try
		{
			lapi.db.openConnection();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		// The connection is now set up and ready to accept queries!
		main.connection = lapi.db.getConnection();
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
		
		if (!lapi.db.isInDb(UUID, table, "uuid"))
		{
			// Inserts the player's data into the db as if they never logged on before
			try
			{
				Statement stmt1 = main.connection.createStatement();
				stmt1.executeQuery("INSERT INTO " + table + " VALUES('" + UUID + "', " + POINTS + ", '" + FIRST_LOGIN + "', " + SHARD_CAPTURES + ", '" + RANK + "', " + KILLS_AS_HB + ", " + DEATHS_AS_HB + ", " + KILLS_AS_S + ", " + DEATHS_AS_S + ", '" + ACTIVE_CLASS + "');");
				
				Statement stmt2 = main.connection.createStatement();
				stmt2.executeQuery("INSERT INTO prem_classes(uuid) VALUES('" + UUID + "');");
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
		
		
	}

	/* DEPRECATED BY dbSetup.java. Will be removed/restored after testing.
	 * If the player's uuid does not appear in the db, consider them to be new. This is their first login.
	// If the player's uuid DOES appear in the db, then it's not their first login.
	private boolean isFirstLogin(String UUID)
	{	
		int numberOfLogins = 0;
		
		// Query: SELECT COUNT(UUID) AS uuidcount FROM player;
		try
		{
			Statement stmt = Database.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(UUID) AS uuidcount FROM " + main.db.getTable() + ";");
			numberOfLogins = rs.getInt("uuidcount");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		if (numberOfLogins > 0)
		{
			return true;
		}
		return false;
	}
	*/
}
