package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import com.ben.hbrewrittenls.database.Queries;
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
		PlayerData pd = Main.getInstance().playerDataMap.get(player.getUniqueId());
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
					if (pd.isHerobrine())
					{
						Queries.removeHBPass(player.getUniqueId());
						player.sendMessage(ChatColor.GOLD + "Pass refunded! You will no longer be Herobrine.");
					}
					else
					{
						Queries.applyHBPass(player.getUniqueId());
						player.sendMessage(ChatColor.GOLD + "Pass purchased! You will now be the Herobrine >:D");
					}
			    	player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
				}
			}
		}
		return;
	}
}
