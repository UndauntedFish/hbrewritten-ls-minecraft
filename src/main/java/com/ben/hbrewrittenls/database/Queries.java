package com.ben.hbrewrittenls.database;

import com.ben.hbrewrittenls.Main;
import com.ben.hbrewrittenls.enums.ClassData;
import com.ben.hbrewrittenls.enums.ColorUtils;
import com.ben.hbrewrittenls.enums.Rank;
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
        AsyncUpdate update = new AsyncUpdate(Main.getInstance().getHikari(), setActiveClass);
        update.setString(1, myClass);
        update.setString(2, uuid.toString());
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