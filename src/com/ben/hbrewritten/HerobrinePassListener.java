package com.ben.hbrewritten;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class HerobrinePassListener implements Listener
{
	// Applies the herobrine pass to a player who right clicks the [Pass] sign.
	@EventHandler
	public void onSignClick(PlayerInteractEvent e)
	{	
		Player player = e.getPlayer();
		Block clickedBlock = e.getClickedBlock();
		Material myBlock;
		if (clickedBlock == null)
		{
			myBlock = Material.AIR;
		}
		else
		{
			myBlock = clickedBlock.getType();
		}
		
		// If the clickedBlock is a sign, and that sign's first line of text is "[Pass]", apply the herobrine pass to player
		if (myBlock.equals(Material.ACACIA_SIGN)   || myBlock.equals(Material.ACACIA_WALL_SIGN)   ||
			myBlock.equals(Material.BIRCH_SIGN)    || myBlock.equals(Material.BIRCH_WALL_SIGN)    ||
			myBlock.equals(Material.DARK_OAK_SIGN) || myBlock.equals(Material.DARK_OAK_WALL_SIGN) ||
			myBlock.equals(Material.JUNGLE_SIGN)   || myBlock.equals(Material.JUNGLE_WALL_SIGN)   ||
			myBlock.equals(Material.DARK_OAK_SIGN) || myBlock.equals(Material.DARK_OAK_WALL_SIGN) ||
			myBlock.equals(Material.OAK_SIGN)      || myBlock.equals(Material.OAK_WALL_SIGN))
		{
			if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK)
			{
				Sign clickedSign = (Sign) clickedBlock.getState();
				if (ChatColor.stripColor(clickedSign.getLine(0)).equalsIgnoreCase("[Pass]"))
				{
					/* 
					 * If the player is already herobrine, they already used the pass. 
					 * Since they clicked the sign again, refund them 1000 and set is_herobrine to false 
					 */
					if (isHerobrine(player))
					{
						removeHBPass(player);
					}
					else
					{
						applyHBPass(player);
					}
			    	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				}
			}
		}
		return;
	}
	
	// Checks if the player is already herobrine.
	public static boolean isHerobrine(Player player)
	{
		boolean isHerobrine = false;
		
		String sql = "SELECT is_herobrine ";
			  sql += "FROM hbstats ";
			  sql += "WHERE uuid = ?";
		
		try
		{
			PreparedStatement ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				isHerobrine = rs.getBoolean(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return isHerobrine;
	}
	
	// Marks the player to be herobrine for next round, and deducts passcost from the player's token amount.
	public static void applyHBPass(Player player)
	{
		int passCost = Main.getInstance().getConfig().getInt("passcost");
		int currentTokens = getTokens(player);
		
		if (currentTokens - passCost < 0)
		{
			player.sendMessage(ChatColor.RED + "" + (passCost - currentTokens) + " more tokens needed for a Herobrine Pass, keep grindin!");
			player.sendMessage(ChatColor.RED + "  Your tokens: " + currentTokens);
			player.sendMessage(ChatColor.RED + "  Required tokens: " + passCost);
			return;
		}
		
		String sql1 = "UPDATE hbstats ";
		  	  sql1 += "SET tokens = tokens - ? ";
		  	  sql1 += "WHERE uuid = ?";
		  	  sql1 += "AND tokens >= ?";
		
		String sql2 = "UPDATE hbstats ";
			  sql2 += "SET is_herobrine = true ";
			  sql2 += "WHERE uuid = ?";
			  
		try
		{
			PreparedStatement ps1 = Main.db.connection.prepareStatement(sql1);
			ps1.setInt(1, passCost);
			ps1.setString(2, player.getUniqueId().toString());
			ps1.setInt(3, passCost);
			ps1.executeUpdate();
			
			PreparedStatement ps2 = Main.db.connection.prepareStatement(sql2);
			ps2.setString(1, player.getUniqueId().toString());
			ps2.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		player.sendMessage(ChatColor.GOLD + "Pass purchased! You will now be the Herobrine >:D");
	}
	
	// Unmarks the player as herobrine for next round, and refunds passcost to the player's token amount.
	public static void removeHBPass(Player player)
	{
		int passCost = Main.getInstance().getConfig().getInt("passcost");
		
		String sql1 = "UPDATE hbstats ";
		  	  sql1 += "SET tokens = tokens + ? ";
		  	  sql1 += "WHERE uuid = ?";
		
		String sql2 = "UPDATE hbstats ";
			  sql2 += "SET is_herobrine = false ";
			  sql2 += "WHERE uuid = ?";
			  
		try
		{
			PreparedStatement ps1 = Main.db.connection.prepareStatement(sql1);
			ps1.setInt(1, passCost);
			ps1.setString(2, player.getUniqueId().toString());
			ps1.executeUpdate();
			
			PreparedStatement ps2 = Main.db.connection.prepareStatement(sql2);
			ps2.setString(1, player.getUniqueId().toString());
			ps2.executeUpdate();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		player.sendMessage(ChatColor.GOLD + "Pass refunded! You will no longer be Herobrine.");
		player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
	}
	// Gets the amount of tokens a player has
	
	private static int getTokens(Player player)
	{
		int tokens = -1; // Tokens should never actually be -1, this is basically a "null" initialization
		
		String sql = "SELECT tokens ";
		  	  sql += "FROM hbstats ";
		  	  sql += "WHERE uuid = ?";
		  	  
		try
		{
			PreparedStatement ps = Main.db.connection.prepareStatement(sql);
			ps.setString(1, player.getUniqueId().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next())
			{
				tokens = rs.getInt(1);
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return tokens;
	}
}
