package com.ben.hbrewritten.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import com.ben.hbrewritten.Main;

public class DatabaseListener implements Listener
{	
	private static String uuid;
	
	public static void addPlayerToDB(Player player)
	{
		uuid = player.getUniqueId().toString();
		
		/* Setting first login date: No longer needed since DMBS records this by default.
		 * This is here for reference on how to get the current timestamp in MariaDB format:
		 * 
		 * 	Timestamp currentTime = new Timestamp(System.currentTimeMillis());
		 * 	first_login = currentTime.toString();
		 */
		
		if (!Database.isInDb(uuid))
		{
			// Inserts the player's data into the db if they never logged on before.
			String sql1 = "INSERT INTO player(uuid) ";
				  sql1 += "VALUES(?)";
			
			String sql2 = "INSERT INTO hbstats(uuid) ";
				  sql2 += "VALUES(?)";
			
			String sql3 = "INSERT INTO hb_prem_classes(uuid) ";
				  sql3 += "VALUES(?)";
			
			try
			{
				PreparedStatement ps1 = Main.db.connection.prepareStatement(sql1);
				ps1.setString(1, uuid);
				ps1.executeUpdate();
				
				PreparedStatement ps2 = Main.db.connection.prepareStatement(sql2);
				ps2.setString(1, uuid);
				ps2.executeUpdate();
				
				PreparedStatement ps3 = Main.db.connection.prepareStatement(sql3);
				ps3.setString(1, uuid);
				ps3.executeUpdate();
			} catch (SQLException e1)
			{
				e1.printStackTrace();
			}
		}
	}
}
