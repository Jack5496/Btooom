package com.jack.btooom.beams;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.API.Btooom;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.util.BIMThrowAndHit;
import com.jack.btooom.util.LocationDestroy;
import com.jack.btooom.util.ItemHelper;

public class BIM {
	
	private final static String type = "Default-ID-NAME";
	
	private final static String displayName = "Default Display";
	private final static String skinURL = "http://textures.minecraft.net/texture/4d5b4d48c4b48a3ebaa9eb10cfb449d8314857ec227601cbe5b53d2c2972a";
	private final static String lore = "Im a default Lore";
	private final static int explosionRadius = 5;
	
	public String getType(){
		return type;
	}
	
	public String getDisplayName(){
		return displayName;
	}
	
	public String getSkinURL(){
		return skinURL;
	}
	
	public String getLore(){
		return lore;
	}
	
	public int getRadius(){
		return explosionRadius;
	}
	
	public void loadBIM(Btooom btooom){
		List<String> lores = new ArrayList<String>();
		lores.add(lore);
		btooom.addonRegisterNewBIMHead(type, displayName, skinURL, lores);
	}
	
	public void unloadBIM(Btooom btooom){
		List<String> lores = new ArrayList<String>();
		lores.add(lore);
		btooom.addonUnRegisterNewBIMHead(type);
	}

	public void rightClickAir(PlayerInteractEvent event) {
		event.setCancelled(true);
	}

	public void rightClickBlock(PlayerInteractEvent event) {
		event.setCancelled(true);
	}

	public void rightClickBIM(PlayerInteractEvent event) {
		event.setCancelled(true);
	}

	public void leftClickBIM(PlayerInteractEvent event) {
		event.setCancelled(true);
	}

	public void leftClickAir(PlayerInteractEvent event) {
		event.setCancelled(true);
	}

	public boolean isResetedBomb(Block b) {
		return false;
	}

	public String getInitHeadName() {
		return type;
	}

	public void resetBomb(Block b) {

	}

	public void resetBomb(ItemStack item) {

	}

	public boolean timeIsZero(String time) {
		return false;
	}

	public void countBombDown(String id) {

	}

	public void detonateBIM(Location loc) {

	}

	public void onHitGround(String id) {

	}

	public void dropBIM(ItemSpawnEvent event) {

	}

	public void updateAfterSecond(String id) {

	}

}
