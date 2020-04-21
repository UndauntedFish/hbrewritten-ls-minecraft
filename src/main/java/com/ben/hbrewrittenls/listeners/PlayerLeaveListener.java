package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import com.ben.hbrewrittenls.database.Queries;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerLeaveListener implements Listener
{
	@EventHandler
	public void onKick(PlayerKickEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		PlayerData pd = Main.getInstance().playerDataMap.get(uuid);

		if (pd.isHerobrine())
		{
			Queries.removeHBPass(uuid);
			pd.setHerobrine(false);
		}
		
		Main.getInstance().playerDataMap.remove(pd);
		e.setLeaveMessage(null);
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent e)
	{
		Player player = e.getPlayer();
		UUID uuid = player.getUniqueId();
		PlayerData pd = Main.getInstance().playerDataMap.get(uuid);

		if (pd.isHerobrine())
		{
			Queries.removeHBPass(uuid);
			pd.setHerobrine(false);
		}

		Main.getInstance().playerDataMap.remove(pd);
		e.setQuitMessage(null);
	}
}
