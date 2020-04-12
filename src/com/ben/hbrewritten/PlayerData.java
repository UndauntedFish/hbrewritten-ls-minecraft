package com.ben.hbrewritten;

import org.bukkit.entity.Player;

import com.ben.hbrewritten.enums.Rank;

public class PlayerData
{
	private Player player;
	
	private int points;
	private Rank rank;
	
	public PlayerData(Player player)
	{
		this.player = player;
		points = Queries.getPointsFromDB(this.player);
		rank = Rank.setRankFromPoints(points);
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public Rank getRank()
	{
		return rank;
	}
}
