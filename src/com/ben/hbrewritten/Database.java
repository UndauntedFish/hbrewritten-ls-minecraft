package com.ben.hbrewritten;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Database
{
	private static Connection connection;
	private String host, database, table, username, password;
	private int port;
	
	public Database(Main main)
	{
		// Connects minecraft plugin to the database management system
		host = main.getConfig().getString("host");
		port = main.getConfig().getInt("port");
		database = main.getConfig().getString("database");
		table = main.getConfig().getString("table");
		username = main.getConfig().getString("username");
		password = main.getConfig().getString("password");
		
		try 
		{
			openConnection();
			
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "MySQL CONNECTED!");
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
	
	public PreparedStatement prepareStatement(String query)
	{
		PreparedStatement ps = null;
		try
		{
			ps = connection.prepareStatement(query);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return ps;
	}
	
	public String getTable()
	{
		return table;
	}
	
}
