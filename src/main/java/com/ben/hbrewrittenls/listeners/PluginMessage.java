package com.ben.hbrewrittenls.listeners;

import com.ben.hbrewrittenls.Main;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginMessage implements PluginMessageListener, Listener
{

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message)
	{
		if (!channel.equalsIgnoreCase("BungeeCord"))
		{
			return;
		}
		
		//ByteArrayDataInput input = ByteStreams.newDataInput(message);
		//String subchannel = input.readUTF();
	}
	
	// Sends specified player from their current server to another specified server.
	@SuppressWarnings("unlikely-arg-type")
	public static void connect(Player player, String server)
	{
		ByteArrayDataOutput output = ByteStreams.newDataOutput();
		output.writeUTF("Connect");
		output.writeUTF(server);
		
		player.sendPluginMessage(Main.getInstance(), "BungeeCord", output.toByteArray());
	}
}
