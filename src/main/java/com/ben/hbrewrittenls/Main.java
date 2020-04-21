package com.ben.hbrewrittenls;

import com.ben.hbrewrittenls.listeners.AsyncPlayerDataLoader;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin
{
    // Instance of this main class used throughout all classes. Accessed via Main.getInstance().
    private static Main instance;

    public HashMap<UUID, PlayerData> playerDataMap = new HashMap<>();

    // Key used in com.ben.hbrewritten.GUIs.ClassSelectionGUI to enable enchantment glows.
    public NamespacedKey key = new NamespacedKey(this, getDescription().getName());

    // Database setup class used to send queries. Used throughout all classes.
    private HikariDataSource hikari;
    private String host, database, username, password;
    private int port;


    @Override
    public void onEnable()
    {
        loadConfig();
        instance = this;

        // Hikari Database Connection Setup
        host = this.getConfig().getString("host");
        port = this.getConfig().getInt("port");
        database = this.getConfig().getString("database");
        username = this.getConfig().getString("username");
        password = this.getConfig().getString("password");

        hikari = new HikariDataSource();
        hikari.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlDataSource");
        hikari.addDataSourceProperty("serverName", host);
        hikari.addDataSourceProperty("port", port);
        hikari.addDataSourceProperty("databaseName", database);
        hikari.addDataSourceProperty("user", username);
        hikari.addDataSourceProperty("password", password);

        // EventHandlers
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);

    }

    @Override
    public void onDisable()
    {
        if (!hikari.equals(null))
        {
            hikari.close();
        }

        instance = null;
    }

    // Loads info from config.yml
    private void loadConfig()
    {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
    }


    // Gets an instance of the Main class to be used by other classes.
    public static Main getInstance()
    {
        return instance;
    }

    public HikariDataSource getHikari()
    {
        return hikari;
    }
}
