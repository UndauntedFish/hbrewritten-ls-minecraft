package com.ben.hbrewritten;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.ben.hbrewritten.enums.Rank;

public class RankManager implements Listener
{
	// Assigns a player a rank, which is determined by the amount of points they have.
	public static void assignRank(Player player)
	{
		int points = getPointsFromDB(player);
		Rank rank = Rank.setRankFromPoints(points);
		
		/*
		String displayName = player.getName(); // Change to getName() if this is causing duplicate rank prefixes.
		player.setDisplayName(ChatColor.YELLOW + "" + points + "" + ChatColor.RESET  + "" + ChatColor.GRAY + " | " + ChatColor.RESET + "" + rank.getDisplayName() + "" + ChatColor.RESET + " " + displayName);
		*/
	}
	
	// Assigns a player a rank, without updating the database. Will reset if they relog. Used by PointsCommand.
	public static void assignTempRank(Player player, int points)
	{
		Rank rank = Rank.setRankFromPoints(points);
		
		/*
		String displayName = player.getName(); // Change to getName() if this is causing duplicate rank prefixes.
		player.setDisplayName(rank.getDisplayName() + "" + ChatColor.RESET + " " + displayName);
		*/
	}
	
	// Should be called in a PlayerJoinListener to load up all player's ranks as they join the server.
	public static int getPointsFromDB(Player player)
	{
		// Attempts to get the player's points as set in the hbstats database table
		int points = -2;
		
		String sql = "SELECT points ";
			  sql += "FROM hbstats ";
			  sql += "WHERE uuid = ?";
		
		PreparedStatement ps;
		ResultSet rs;
		try
		{
			ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, player.getUniqueId().toString());
			rs = ps.executeQuery();
			while (rs.next())
			{
				points = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBR] ERROR at RankManager.getRankFromDB(): Could not load " + player.getName() + "'s rank from the database! (UUID: " + player.getUniqueId());
			e.printStackTrace();
		}
		
		if (points == -2)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBR] ERROR at RankManager.getRankFromDB(): Could not load " + player.getName() + "'s rank from the database! (UUID: " + player.getUniqueId());
		}
		
		return points;
	}
}
