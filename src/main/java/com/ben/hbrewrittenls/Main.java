package com.ben.hbrewrittenls;

import com.ben.hbrewrittenls.GUIs.InventoryClickListener;
import com.ben.hbrewrittenls.GUIs.RightclickListener;
import com.ben.hbrewrittenls.database.BaseFields;
import com.ben.hbrewrittenls.listeners.*;
import com.ben.hbrewrittenls.lobbytimer.BossbarTimer;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
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
        registerEnchGlow();
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
        Bukkit.getPluginManager().registerEvents(new MiscListener(), this);


        // Bungee plugin messaging channel setup
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new PluginMessage());
    }

    @Override
    public void onDisable()
    {
        if (!hikari.equals(null))
        {
            hikari.close();
        }
        playerDataMap = null;
        instance = null;
    }

    // Loads info from config.yml
    private void loadConfig()
    {
        this.getConfig().options().copyDefaults();
        saveDefaultConfig();
    }

    // Sets up custom enchantment glow
    public void registerEnchGlow()
    {
        try
        {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        try
        {
            NamespacedKey key = new NamespacedKey(this, getDescription().getName());

            EnchGlow glow = new EnchGlow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException e)
        {

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    // Bungee Messaging Channel Setup
    public void onPluginMessageReceived(String channel, Player player, byte[] message)
    {
        if (!channel.equals("BungeeCord"))
        {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();
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
