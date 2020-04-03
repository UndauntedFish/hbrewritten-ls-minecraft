package com.ben.hbrewritten.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.ben.hbrewritten.Main;
import com.ben.hbrewritten.RankManager;
import com.ben.hbrewritten.database.DatabaseListener;

public class PlayerJoinListener implements Listener
{
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player player = e.getPlayer();
		
		DatabaseListener.addPlayerToDB(player);
		RankManager.assignRank(player);
		
		addPlayerToTimer(player);
	}
	
	// Safely adds player to timer. If there is not enough players, it won't add the player until the minplayers threshold is met, etc.
	private void addPlayerToTimer(Player player)
	{
		int playersOnline = Bukkit.getOnlinePlayers().size() + 1; // +1 to include the player that just joined.
		int minPlayersNeededToStart = Main.getInstance().getConfig().getInt("minplayers");
		/* 
		 * If the timer wasn't already started AND there's enough players to start the timer, 
		 * start the timer and add all waiting players to the timer.
		 * 
		 * But if the timer was already started, just add the new player to the timer. (handled in else-if statement)
		 */
		if ( (!Main.getInstance().lobbyTimer.wasStarted()) && (playersOnline > minPlayersNeededToStart) ) 
		{
			/*
			 *  Code will reach here if the timer wasn't already started AND 
			 *  there's enough players to start the timer.
			 *  
			 *  So we'll start the timer and add the new player and all players who were waiting prior.
			 */
			Main.getInstance().lobbyTimer.startTimer();
			
			// Adds the player who just joined, and the players already waiting to the timer.
			for (Player p : Bukkit.getOnlinePlayers())
			{
				Main.getInstance().lobbyTimer.getTimer().addPlayer(p);
			}
		}
		else if (Main.getInstance().lobbyTimer.wasStarted())
		{
			// Code will reach here if the timer was started already. So we just add the new player to the timer.
			Main.getInstance().lobbyTimer.getTimer().addPlayer(player);
		}
		else
		{
			// Code will reach here if the timer wasn't started because there's not enough players waiting.
			Main.getInstance().lobbyTimer.getTimer().addPlayer(player);

			int currentPlayersNeededToStart = (minPlayersNeededToStart - playersOnline);
			
			if (currentPlayersNeededToStart == 1)
			{
				Main.getInstance().lobbyTimer.getTimer().setTitle("1 more player needed to start the game!");
			}
			else
			{
				Main.getInstance().lobbyTimer.getTimer().setTitle(currentPlayersNeededToStart + " more players needed to start the game!");
			}
		}
	}
}
