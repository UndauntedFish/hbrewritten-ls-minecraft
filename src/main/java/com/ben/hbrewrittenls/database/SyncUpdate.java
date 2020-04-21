package com.ben.hbrewrittenls.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

public class SyncUpdate extends BaseFields
{
    public PreparedStatement preparedStatement;
    private String sql;
    private HashMap<Integer, String> sqlArgs;

    private int result;

    public SyncUpdate(String sql) throws IllegalArgumentException
    {
        if (connection.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource for BaseFields!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for SyncUpdate!");
            throw new IllegalArgumentException();
        }

        this.sql = sql;
        this.sqlArgs = new HashMap<>();
        try
        {
            this.preparedStatement = connection.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        this.result = 0;
    }

    public void setString(int index, String input)
    {
        try
        {
            preparedStatement.setString(index, input);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    // Executes the update.
    public int execute()
    {
        try
        {
            result = preparedStatement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return result;
    }
}
