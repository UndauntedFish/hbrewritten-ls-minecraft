package com.ben.hbrewritten.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class CustomChatFormatListener implements Listener
{
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e)
	{	 
		//e.setFormat("%s » %s"); 
		/*
		 *  If the player's actual name is including the format <points> | Herobrine | <name>
		 *  Then do all of the chat formatting here instead of in the rankmanager.
		 */
	}
}
