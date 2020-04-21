package com.ben.hbrewrittenls.database;

import com.ben.hbrewrittenls.Main;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class AsyncUpdate extends BaseFields
{
    private PreparedStatement preparedStatement;
    private String sql;

    private CompletableFuture<Integer> result;

    public AsyncUpdate(String sql) throws IllegalArgumentException
    {
        if (connection.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        this.sql = sql;
        try
        {
            this.preparedStatement = connection.prepareStatement(sql);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        this.result = new CompletableFuture<>();
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

    // Executes the query. Will pause the class it got called in until the async task is done.
    public int execute()
    {
        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    result.complete(preparedStatement.executeUpdate());
                    preparedStatement.close();
                }
                catch (SQLException e)
                {
                    e.printStackTrace();
                }
            }
        });

        int updateResult = 0;
        try
        {
            updateResult = result.get().intValue();
        }
        catch (InterruptedException | ExecutionException e)
        {
            e.printStackTrace();
        }

        return updateResult;
    }
}
