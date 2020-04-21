package com.ben.hbrewritten.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import com.ben.hbrewritten.Main;

public class Database
{
	// Connection used throughout all classes to send queries to the database as set in DatabaseListener.
	public Connection connection;
	
	private String host, database, username, password;
	private int port;
	
	public Database()
	{
		// Connects minecraft plugin to the database management system with credentials from config.yml
		Main main = Main.getInstance();
		host = main.getConfig().getString("host");
		port = main.getConfig().getInt("port");
		database = main.getConfig().getString("database");
		username = main.getConfig().getString("username");
		password = main.getConfig().getString("password");
		
		try 
		{
			openConnection();
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBR] MySQL CONNECTED!");
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	// Opens a connection from the plugin to the database github test
	private void openConnection() throws SQLException
	{
		// If the connection is already open, then don't attempt to open it again
		if (connection != null && !connection.isClosed())
		{
			return;
		}
		
		connection = DriverManager.getConnection("jdbc:mysql://" + 
		this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	}
	
	/* 
	 * If the player's uuid does not appear in the db, consider them to be new. This is their first login.
	 * If the player's uuid DOES appear in the db, then it's not their first login.
	 */
	public static boolean isInDb(String UUID)
	{	
		int numberOfLogins = 0;
		
		String sql = "SELECT COUNT(uuid) ";
			  sql += "FROM player ";
			  sql += "WHERE uuid = ?";
		
		try
		{
			PreparedStatement ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, UUID);
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				numberOfLogins = rs.getInt(1);
			}
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
}
