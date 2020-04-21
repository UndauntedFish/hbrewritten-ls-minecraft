package com.ben.hbrewrittenls.database;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.PlayerData;
import com.ben.hbrewrittenls.enums.ClassData;
import com.ben.hbrewrittenls.enums.ColorUtils;
import com.ben.hbrewrittenls.enums.Rank;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Queries
{
    /* These are the queries used among all methods in this class */
    private static String numOccurences = "SELECT COUNT(uuid) AS numOccurences " +
                                          "FROM player " +
                                          "WHERE uuid = ?";

    private static String insertIntoPlayer = "INSERT INTO player(uuid) " +
                                             "VALUES(?)";

    private static String insertIntoStats = "INSERT INTO hbstats(uuid) " +
                                              "VALUES(?)";

    private static String insertIntoPrem = "INSERT INTO hb_prem_classes(uuid) " +
                                           "VALUES(?)";

    private static String setActiveClass = "UPDATE hbstats " +
                                           "SET active_class = ? " +
                                           "WHERE uuid = ?";

    private static String getActiveClass = "SELECT active_class " +
                                           "FROM hbstats " +
                                           "WHERE uuid = ?";

    private static String getPoints = "SELECT points " +
                                      "FROM hbstats " +
                                      "WHERE uuid = ?";

    private static String isHerobrine = "SELECT is_herobrine " +
                                        "FROM hbstats " +
                                        "WHERE uuid = ?";

    private static String safeRemoveTokens = "UPDATE hbstats " +
                                             "SET tokens = tokens - ? " +
                                             "WHERE uuid = ? " +
                                             "AND tokens >= ?";

    private static String addTokens = "UPDATE hbstats " +
                                      "SET tokens = tokens + ? " +
                                      "WHERE uuid = ? ";

    private static String setHerobrine = "UPDATE hbstats " +
                                         "SET is_herobrine = ? " +
                                         "WHERE uuid = ?";

    private static String getTokens = "SELECT tokens " +
                                      "FROM hbstats " +
                                      "WHERE uuid = ?";

    // Gets the player's current amount of tokens from the database
    public static int getTokens(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(Main.getInstance().getHikari(), getTokens);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getInt("tokens");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -42; // This means the player doesn't exist in the db. THIS SHOULD NEVER HAPPEN.
    }

    // Marks the player to be herobrine for next round, and deducts passcost from the player's token amount.
    public static void applyHBPass(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        PlayerData pd = Main.getInstance().playerDataMap.get(uuid);
        int passTokenCost = Main.getInstance().getConfig().getInt("passcost");
        int currentTokens = pd.getTokens();

        // If the player doesn't have enough tokens to buy a hb pass, return/do nothing.
        if (currentTokens - passTokenCost < 0)
        {
            player.sendMessage(ChatColor.RED + "" + (passTokenCost - currentTokens) + " more tokens needed for a Herobrine Pass, keep grindin!");
            player.sendMessage(ChatColor.RED + "  Your tokens: " + currentTokens);
            player.sendMessage(ChatColor.RED + "  Required tokens: " + passTokenCost);
            return;
        }

        // Deducts passTokenCost from the player's token balance.
        AsyncUpdate update1 = new AsyncUpdate(Main.getInstance().getHikari(), safeRemoveTokens);
        update1.setString(1, Integer.toString(passTokenCost));
        update1.setString(2, uuid.toString());
        update1.setString(3, Integer.toString(passTokenCost));
        update1.execute();
        pd.setTokens(pd.getTokens() - passTokenCost);

        // Sets is_herobrine to true for the player
        AsyncUpdate update2 = new AsyncUpdate(Main.getInstance().getHikari(), setHerobrine);
        update2.setString(1, "true");
        update2.setString(2, uuid.toString());
        update2.execute();
        pd.setHerobrine(true);
    }

    // Unmarks the player as herobrine for next round, and refunds passcost to the player's token amount.
    public static void removeHBPass(UUID uuid)
    {
        Player player = Bukkit.getPlayer(uuid);
        PlayerData pd = Main.getInstance().playerDataMap.get(uuid);
        int passTokenCost = Main.getInstance().getConfig().getInt("passcost");

        // Adds back (refunds) passTokenCost to the player's token balance.
        AsyncUpdate update1 = new AsyncUpdate(Main.getInstance().getHikari(), addTokens);
        update1.setString(1, Integer.toString(passTokenCost));
        update1.setString(2, uuid.toString());
        update1.execute();
        pd.setTokens(pd.getTokens() - passTokenCost);

        // Sets is_herobrine to false for the player
        AsyncUpdate update2 = new AsyncUpdate(Main.getInstance().getHikari(), setHerobrine);
        update2.setString(1, "false");
        update2.setString(2, uuid.toString());
        update2.execute();
        pd.setHerobrine(false);
    }

    // Returns the player's "is_herobrine" entry as is in the database.
    // If it is true, that means the user used a herobrine pass.
    public static boolean isHerobrine(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(Main.getInstance().getHikari(), isHerobrine);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getBoolean("is_herobrine");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
    // Gets the player's current amount of points from the database
    public static int getPoints(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(Main.getInstance().getHikari(), getPoints);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getInt("points");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return -42; // This means the player doesn't exist in the db. THIS SHOULD NEVER HAPPEN.
    }

    // Gets the player's current active class from the database.
    public static String getActiveClass(UUID uuid)
    {
        AsyncQuery query = new AsyncQuery(Main.getInstance().getHikari(), getActiveClass);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                return rs.getString("active_class");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    // Sets the player's active class to whatever is specified in the myClass arg of this method.
    public static void setActiveClass(UUID uuid, String myClass)
    {
        SyncUpdate update = new SyncUpdate(setActiveClass);
        try
        {
            update.preparedStatement.setString(1, myClass);
            update.preparedStatement.setString(2, uuid.toString());
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        update.execute();
    }

    // Adds a player to the database if they are not in it, but does nothing if they are already in it.
    public static void safeAddPlayerToDB(UUID uuid)
    {
        // Checks how many times the player appears in the database.
        AsyncQuery query = new AsyncQuery(Main.getInstance().getHikari(), numOccurences);
        query.setString(1, uuid.toString());
        ResultSet rs = query.execute();
        try
        {
            while (rs.next())
            {
                if (rs.getInt("numOccurences") > 0)
                {
                    // Player appears in the database, so no need to add them again.
                    return;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Adds the player to the player table
        AsyncUpdate updatePlayer = new AsyncUpdate(Main.getInstance().getHikari(), insertIntoPlayer);
        updatePlayer.setString(1, uuid.toString());
        updatePlayer.execute();

        // Adds the player to the hbstats table
        AsyncUpdate updateStats = new AsyncUpdate(Main.getInstance().getHikari(), insertIntoStats);
        updateStats.setString(1, uuid.toString());
        updateStats.execute();

        // Adds the player to the hb_prem_classes table
        AsyncUpdate updatePrem = new AsyncUpdate(Main.getInstance().getHikari(), insertIntoPrem);
        updatePrem.setString(1, uuid.toString());
        updatePrem.execute();
    }
}