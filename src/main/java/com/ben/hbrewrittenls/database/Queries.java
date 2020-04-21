package com.ben.hbrewrittenls.database;

import com.ben.hbrewrittenls.Main;

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