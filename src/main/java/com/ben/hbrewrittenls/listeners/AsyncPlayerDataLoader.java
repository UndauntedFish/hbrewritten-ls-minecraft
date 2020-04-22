package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import com.ben.hbrewrittenls.database.Queries;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.sql.SQLException;
import java.util.UUID;

public class AsyncPlayerDataLoader implements Listener
{
    /*
     * This listener fires right after the player double clicks the server to join,
     * but before they actually spawn in.
     */
    @EventHandler
    public void onPlayerJoin(AsyncPlayerPreLoginEvent e) throws SQLException
    {
        UUID uuid = e.getUniqueId();

        // Adds player to the database if they new, but does nothing if they are already in it.
        Queries.safeAddPlayerToDB(uuid);

        // Building up the player's PlayerData object
        PlayerData pd = new PlayerData(uuid);

        int points = Queries.getPoints(uuid);
        pd.setPoints(points);

        String activeClass = Queries.getActiveClass(uuid);
        pd.setActiveClass(activeClass);

        int tokens = Queries.getTokens(uuid);
        pd.setTokens(tokens);

        // Add is_herobrine here. Check for int and return boolean in Queries.is_herobrine.

        // Adding the PlayerData object to the main class's hashmap.
        if (Main.getInstance().playerDataMap.containsKey(uuid))
        {
            Main.getInstance().playerDataMap.replace(uuid, pd);
        }
        else
        {
            Main.getInstance().playerDataMap.put(uuid, pd);
        }

        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "[HBRgs] Player data successfully loaded.");
    }
}
