package com.ben.hbrewritten.lobbytimer;

import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

import com.ben.hbrewritten.Main;

public class Timer
{
	private BossBar bossbar = Bukkit.createBossBar(null, BarColor.GREEN, BarStyle.SOLID);
	private double time = Main.getInstance().getConfig().getDouble("time"); // in seconds
	private boolean timerStarted = false;
	
	
	// Starts the timer. Returns TRUE if the timer is finished. FALSE if it never finishes.
	public void startTimer()
	{	
		if (time <= 0.0)
		{
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "[HBR] The timer's duration (as set in the config), must be greater than 0.0!");
			return;
		}
				
		// runnable iteration counter
		AtomicInteger currentIteration = new AtomicInteger(1);
		AtomicInteger processID = new AtomicInteger();
		int maxIterations = (int) time - 1; // 59 for 60s, 29 for 30s, etc.
		bossbar.setProgress(1.0 / time); // 1.0/60.0 for 60s, 1.0/30.0 for 30s, etc.
		
		int counter = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), new Runnable() 
		{
			public void run()
			{
				/* 
				 * This try catch makes sure the bar progress does not go over 1.0.
				 * 
				 * If it tries to do so, it catches the error and sets progress 
				 * exactly to 1.0 and start the game.
				 */
				try
				{
					// Increments the progress bar by a fixed amount each second
					if (currentIteration.get() != 0)
					{
						int timeLeft = ((int) time - (currentIteration.get() + 1));
						if (timeLeft == 1)
						{
							bossbar.setTitle(timeLeft + " second until the game starts!");
						}
						else if (timeLeft < (int) time && timeLeft > 1)
						{
							bossbar.setTitle(timeLeft + " seconds until the game starts!");
						}
						else
						{
							bossbar.setTitle(ChatColor.GREEN + "Starting game...");
							sendPlayersToGame();
						}
						
						//+ (1.0/60.0) is 60 second timer, + (1.0/30.0) is 30 second timer, etc.
						bossbar.setProgress(bossbar.getProgress() + (1.0 / time));
					}
				} catch (IllegalArgumentException e)
				{
					// Program will be here if the bossbar's progress is set to a value > 1.0.
					// We will cancel that attempt to set it to > 1.0, and manually set it to 1.0
					// The players are ready to be sent to the game.
					bossbar.setProgress(1.0);
					sendPlayersToGame();
					Bukkit.getScheduler().cancelTask(processID.get());
				}
				
				int currentIt = currentIteration.incrementAndGet();
				if (currentIt > maxIterations)
				{
					Bukkit.getScheduler().cancelTask(processID.get());
				}
			}
		}, 0L, 20L); // 1 sec = 20 ticks, hence 20L
		processID.set(counter);
		
		// If code reaches here, that means the timer is finished and the players are ready to be sent to the game
	}
	
	/*
	 *  Sends all players to the gameserver.
	 *  
	 *  
	 *   
	 */
	private void sendPlayersToGame()
	{
		
		
	}
	
	public BossBar getTimer()
	{
		return bossbar;
	}
	
	public boolean wasStarted()
	{
		return timerStarted;
	}
}
