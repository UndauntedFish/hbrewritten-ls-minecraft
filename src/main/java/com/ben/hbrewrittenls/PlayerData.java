package com.ben.hbrewrittenls;

import com.ben.hbrewrittenls.enums.Rank;

import java.util.UUID;

public class PlayerData
{
	private UUID uuid;
	
	private int points;
	private Rank rank;
	private boolean hasDataLoaded;
	
	public PlayerData(UUID uuid)
	{
		this.uuid = uuid;
		this.rank = Rank.setRankFromPoints(points);
		this.hasDataLoaded = false;
	}
	
	public UUID getUUID()
	{
		return uuid;
	}
	
	public int getPoints()
	{
		return points;
	}
	
	public void setPoints(int points)
	{
		this.points = points;
	}
	
	public void incrementPointsBy(int addedPoints)
	{
		this.points += points;
	}
	
	public Rank getRank()
	{
		return rank;
	}

	public void setRank(Rank rank)
	{
		this.rank = rank;
	}

	public void setDataLoaded(boolean hasDataLoaded)
	{
		this.hasDataLoaded = hasDataLoaded;
	}

	public boolean hasDataLoaded()
	{
		return hasDataLoaded;
	}
}
