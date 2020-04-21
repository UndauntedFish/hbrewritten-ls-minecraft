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

public class AsyncUpdate
{
    private HikariDataSource hikari;

    private String sql;
    private ArrayList<String> sqlArgs;

    private CompletableFuture<Integer> result;

    public AsyncUpdate(HikariDataSource hikari, String sql) throws IllegalArgumentException
    {
        if (hikari.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null datasource input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        if (sql.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] ERROR: Null sql input for AsyncUpdate!");
            throw new IllegalArgumentException();
        }

        this.hikari = hikari;
        this.sql = sql;
        this.sqlArgs = new ArrayList<>();
        this.result = new CompletableFuture<>();
    }

    public void setString(int index, String input)
    {
        if (index < 1 || input.equals(null))
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBRgs] No preparedStatement arguments found in AsyncUpdateQuery!");
            throw new IllegalArgumentException();
        }
        sqlArgs.add(index - 1, input);
    }

    private PreparedStatement generatePreparedStatement() throws SQLException
    {
        PreparedStatement ps = hikari.getConnection().prepareStatement(sql);

        for (int i = 0; i < sqlArgs.size(); i++)
        {
            ps.setString(i + 1, sqlArgs.remove(i));
        }
        sqlArgs = null;

        return ps;
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
                    PreparedStatement ps = generatePreparedStatement();
                    result.complete(ps.executeUpdate());
                    ps.close();
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
