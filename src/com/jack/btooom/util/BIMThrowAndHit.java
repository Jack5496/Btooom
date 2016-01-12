package com.jack.btooom.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.util.Vector;

public class BIMThrowAndHit {

	public static Entity throwNormal(String id, Player p) {
		return throwEntity(id, p, 1.5);
	}

	public static Entity throwLikeDrop(String id, Player p) {
		return throwEntity(id, p, 0.3);
	}

	public static Entity throwEntity(String id, Player p, double multiply) {
		Snowball snowball = p.getWorld().spawn(p.getEyeLocation(), Snowball.class);
		snowball.setCustomName(id);
		snowball.setVelocity(p.getLocation().getDirection().multiply(multiply));
		snowball.setShooter(p);
		return snowball;
	}

	public static Location getLandingLocation(Location hitBlock, Location entity) {
		hitBlock = new Location(hitBlock.getWorld(), hitBlock.getBlockX(), hitBlock.getBlockY(), hitBlock.getBlockZ());
		entity = new Location(entity.getWorld(), entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
		

		List<Location> air = getAirLocations(get6Neigh(hitBlock));
		
		if(entity.distance(hitBlock)<=1 && entity.getBlock().getType()==Material.AIR) return entity;
		
		//hit perfect?
		for(Location loc : air){
			if(loc.distance(entity)==0) return loc;
		}
		//hit 1 axe wrong?
		for(Location loc : air){
			if(loc.distance(entity)<=1) return loc;
		}
		//hit 2 axe wrong? 
		for(Location loc : air){
			if(loc.distance(entity)<=1.5) return loc;
		}
//		for(Location loc : air){
//			if(loc.distance(entity)<=2) return loc;
//		}	
		Bukkit.broadcastMessage("BIMThrowAndHit:May Hit Water?");
		return entity;
		}
	
	private static List<Location> get6Neigh(Location loc){
		List<Location> back = new ArrayList<Location>();
		back.add(loc.clone().add(new Vector(-1,0,0)));
		back.add(loc.clone().add(new Vector(1,0,0)));
		back.add(loc.clone().add(new Vector(0,-1,0)));
		back.add(loc.clone().add(new Vector(0,1,0)));
		back.add(loc.clone().add(new Vector(0,0,-1)));
		back.add(loc.clone().add(new Vector(0,0,1)));
		return back;
	}
	
	private static List<Location> getAirLocations(List<Location> list){
		List<Location> back = new ArrayList<Location>();
		for(Location loc : list){
			if(loc.getBlock().getType()==Material.AIR) back.add(loc);
		}
		return back;
	}
	
	public static BlockFace getRelativ6NeighBlockFace(Location from, Location direction){
		from = new Location(from.getWorld(), from.getBlockX(), from.getBlockY(), from.getBlockZ());
		direction = new Location(direction.getWorld(), direction.getBlockX(), direction.getBlockY(), direction.getBlockZ());
		
		if(from.getBlockY()<direction.getBlockY()) return BlockFace.UP;
		if(from.getBlockY()>direction.getBlockY()) return BlockFace.DOWN;
		
		if(from.getBlockX()<direction.getBlockX()) return BlockFace.EAST;
		if(from.getBlockX()>direction.getBlockX()) return BlockFace.WEST;
		
		if(from.getBlockZ()<direction.getBlockZ()) return BlockFace.SOUTH;
		return BlockFace.NORTH;
		
		
	}
	


}
