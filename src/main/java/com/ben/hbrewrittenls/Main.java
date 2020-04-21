package com.ben.hbrewrittenls;

import com.ben.hbrewrittenls.GUIs.InventoryClickListener;
import com.ben.hbrewrittenls.GUIs.RightclickListener;
import com.ben.hbrewrittenls.database.BaseFields;
import com.ben.hbrewrittenls.listeners.*;
import com.ben.hbrewrittenls.lobbytimer.BossbarTimer;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
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

    // Lobby timer. Accessed by the PlayerJoinListener to add players to the bossbar timer.
    public BossbarTimer lobbyTimer;

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
        try
        {
            BaseFields.connection = this.hikari.getConnection();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        // Lobbytimer
        lobbyTimer = new BossbarTimer();

        // EventHandlers
        Bukkit.getPluginManager().registerEvents(new AsyncPlayerDataLoader(), this);
        Bukkit.getPluginManager().registerEvents(new CustomChatFormatListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(), this);
        Bukkit.getPluginManager().registerEvents(new RightclickListener(), this);
        Bukkit.getPluginManager().registerEvents(new HerobrinePassListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerLeaveListener(), this);

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
