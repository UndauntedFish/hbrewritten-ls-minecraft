package com.ben.hbrewritten.enums;

import org.bukkit.ChatColor;

public enum Rank
{
	SPIRIT       (0, ColorUtils.fromRGB(128, 128, 128) + "Spirit"),
	DEFENDER     (100, ColorUtils.fromRGB(0, 179, 179) + "Defender"),
	CASPER       (500, ChatColor.AQUA + "Casper"),
	BANSHEE      (1500, ChatColor.LIGHT_PURPLE + "Banshee"),
	SOULSEEKER   (2500, ChatColor.GOLD + "Soulseeker"),
	HAUNTER      (4000, ChatColor.YELLOW + "Haunter"),
	POSSESSED    (5000, ChatColor.RED + "Possessed"),
	WRAITH       (7500, ChatColor.BLUE + "Wraith"),
	PHANTOM      (10000, ChatColor.GREEN + "Phantom"),
	SKINWALKER   (15000, ChatColor.YELLOW + "Skinwalker"),
	MYSTERIA     (25000, ChatColor.LIGHT_PURPLE + "Mysteria"),
	WIDOWER      (35000, ChatColor.DARK_GRAY + "Widower"),
	PARANORMAL   (45000, ChatColor.BLUE + "Paranormal"),
	SPECTRAL     (60000, ColorUtils.fromRGB(0, 179, 0) + "Spectral"),
	MYTHICAL     (80000, ChatColor.RED + "Mythical"),
	GHOSTBUSTER  (100000, ChatColor.WHITE + "" + ChatColor.BOLD + "Ghostbuster"),
	SPOOKY       (150000, ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Spooky"),
	ETHEREAL     (200000, ChatColor.GREEN + "" + ChatColor.BOLD + "Ethereal"),
	DEMONHUNTER  (250000, ChatColor.RED + "" + ChatColor.BOLD + "DemonHunter"),
	DIVINE       (300000, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Divine"),
	DEATHBRINGER (-1, ChatColor.BOLD + "" + ChatColor.DARK_RED + "Death Bringer");
	
	private final int POINTSREQUIRED;
	private final String DISPLAYNAME;
	
	private Rank(int pointsRequired, String displayName)
	{
		this.POINTSREQUIRED = pointsRequired;
		this.DISPLAYNAME = displayName;
	}
	
	public int getPointsRequired()
	{
		return POINTSREQUIRED;
	}
	
	public String getDisplayName()
	{
		return DISPLAYNAME;
	}
	
	public static Rank setRankFromPoints(int points)
	{
		Rank[] ranks = Rank.values();
		
		for (int i = 0; i < ranks.length; i++)
		{
			if (points == ranks[i].getPointsRequired())
			{
				return ranks[i];
			}
			
			// If ranks has next element
			if (!ranks[i + 1].equals(null))
			{
				if (points > ranks[i].getPointsRequired() && points < ranks[i + 1].getPointsRequired())
				{
					return ranks[i];
				}
			}
			else
			{
				return Rank.DEATHBRINGER;
			}
		}
		
		return null;
	}
	
}
