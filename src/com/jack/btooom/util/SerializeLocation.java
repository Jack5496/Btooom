package com.jack.btooom.util;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class SerializeLocation {

	private static String split = ":";

	public static String toString(Location l) {
		String world = ""+l.getWorld().getName();
		String x = ""+l.getBlockX();
		String y = ""+l.getBlockY();
		String z = ""+l.getBlockZ();
		
		return world+split+x+split+y+split+z;
	}
	
	public static Location fromString(String s){
		String[] splits = s.split(split);

		String world = splits[0];
		int x = Integer.parseInt(splits[1]);
		int y = Integer.parseInt(splits[2]);
		int z = Integer.parseInt(splits[3]);
		
		return new Location(Bukkit.getWorld(world), x,y,z);
	}
}
