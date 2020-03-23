package com.ben.hbrewritten.listeners;

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
		
		if (!Main.getInstance().lobbyTimer.wasStarted()) // ADD && Bukkit.getOnlinePlayers().size() => 3 for final release
		{
			Main.getInstance().lobbyTimer.startTimer();
		}
		Main.getInstance().lobbyTimer.getTimer().addPlayer(player);
		
	}
}
