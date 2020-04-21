package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.database.Queries;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.SQLException;

public class PlayerJoinListener implements Listener
{
    /*
     * This listener fires right after the player double clicks the server to join,
     * but before they actually spawn in.
     */
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) throws SQLException
    {
        //OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(e.getUniqueId());
        Queries.safeAddPlayerToDB(e.getUniqueId());
    }
}
