package com.ben.hbrewritten.enums;

import org.bukkit.ChatColor;

public enum Ranks
{
	SPIRIT       (0, ChatColor.GRAY + "Spirit"),
	DEFENDER     (100, ChatColor.DARK_GRAY + "Defender"),
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
	SPECTRAL     (60000, ChatColor.GREEN + "Spectral"),
	MYTHICAL     (80000, ChatColor.RED + "Mythical"),
	GHOSTBUSTER  (100000, ChatColor.WHITE + "Ghostbuster"),
	SPOOKY       (150000, ChatColor.GOLD + "Spooky"),
	ETHEREAL     (200000, ChatColor.GREEN + "Ethereal"),
	DEMONHUNTER  (250000, ChatColor.RED + "DemonHunter"),
	DIVINE       (300000, ChatColor.DARK_PURPLE + "Divine"),
	DEATHBRINGER (-1, ChatColor.BOLD + "" + ChatColor.DARK_RED + "Death Bringer");
	
	private final int POINTSREQUIRED;
	private final String DISPLAYNAME;
	
	private Ranks(int pointsRequired, String displayName)
	{
		this.POINTSREQUIRED = pointsRequired;
		this.DISPLAYNAME = displayName;
	}
	
	public int getPointsRequired()
	{
		return POINTSREQUIRED;
	}
	
	public String getRank(int points)
	{
		return DISPLAYNAME;
	}
	
}
