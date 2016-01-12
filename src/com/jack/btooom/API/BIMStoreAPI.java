package com.jack.btooom.API;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.jack.btooom.Core;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.heads.Heads;
import com.jack.btooom.threads.BIMTimeController;
import com.jack.btooom.util.BIMThrowAndHit;
import com.jack.btooom.util.ItemHelper;
import com.jack.btooom.util.SerializeLocation;
import com.jack.btooom.util.UtilRandom;

public interface BIMStoreAPI {
	public void loadAllConfigs();

	public void removeUnusedBIMs();

	public String createID();

	public void BIMplace(PlayerInteractEvent event);
	public void BIMplace(Block b, ItemStack item, BlockFace face, Vector v, Player p);

	public void BIMbreak(PlayerInteractEvent event);
	public void BIMbreak(Block b, Player p);

	public void BIMGivePlayer(ItemStack item, String id, Player p);

	public void BIMChangeInventory(Inventory from, Inventory to, Player p, ItemStack bim, Location loc);

	public boolean BIMThrow(ItemStack item, Player p);
	public void BIMDropLikeThrow(ItemSpawnEvent event);

	public void BIMDropLikeDrop(ItemSpawnEvent event);
	public void BIMDropLikeDrop(Item entity);

	public void BIMPickup(PlayerPickupItemEvent event);
	public void BIMPickup(Entity pickup, Player p);

	public void givePlayerNewBIM(Player p, String typ);

	public void registerBIM(ItemStack item, Player p, String typ);

	public void destroyBIM(String id);

	public void removeBIMFromInventory(Inventory inv, String id);

	public void unregisterBIM(String id);

	public String getBIMStoreTyp(String id);
	public void setBIMStoreTyp(String id, String storedAs);

	public void setBIMStoreTyp(String id, Player p);
	public void setBIMStoreTyp(String id, Block b);
	public void setBIMStoreTyp(String id, Entity item);
	public void setBIMStoreTyp(String id, Location loc);

	public Location getBIMLocation(String id);
	
	public Block getBIMStoredAsBlockBlock(String id);
	public Player getBIMStorePlayerHoldPlayer(String id);
	public Entity getBIMStoredAsEntityEntity(String id);
	public Location getBIMStoredAsChestHoldLocation(String id);
	public Inventory getBIMStoredAsChestHold(String id);

	public void setBIMOwner(String id, String owner);
	public void setBIMOwner(ItemStack item, String owner);
	public void setBIMOwner(Block b, String owner);

	public Player getBIMOwner(String id);
	public String getBIMOwner(ItemStack item);
	public String getBIMOwner(Block b);

	public boolean isBIMOwner(Player p, String id);
	public boolean isBIMOwner(Player p, ItemStack item);
	public boolean isBIMOwner(Player p, Block b);

	public boolean isBIM(String id);
	public boolean isBIM(Block b);
	public boolean isBIM(ItemStack item);
	public boolean isBIM(Entity item);

	public void setBIMActive(ItemStack item, String activ);
	public void setBIMActive(Block b, String activ);

	public String getBIMActive(ItemStack item);
	public String getBIMActive(Block b);
	public String getBIMActive(String id);

	public void activateBIM(String id);
	public void activateBIM(ItemStack item);
	public void activateBIM(Block b);

	public void deactivateBIM(String id);
	public void deactivateBIM(ItemStack item);
	public void deactivateBIM(Block b);

	public boolean isBIMActiv(String id);
	public boolean isBIMActiv(ItemStack item);
	public boolean isBIMActiv(Block b);

	public boolean isBIMInactiv(String id);
	public boolean isBIMInactiv(ItemStack item);
	public boolean isBIMInactiv(Block b);

	public void setBIMType(ItemStack item, String typ);
	public void setBIMType(Block b, String typ);
	
	public String getBIMType(String id);
	public String getBIMType(ItemStack item);
	public String getBIMType(Block b);

	public String getBIMIdentity(Block b);
	public String getBIMIdentity(ItemStack item);
	public String getBIMIdentity(Entity item);

	public void setBIMHeadName(String id, String headName);
	public void setBIMHeadName(ItemStack item, String headName);
	public void setBIMHeadName(Block b, String headName);

	public String getBIMHeadName(String id);
	public String getBIMHeadName(ItemStack item);
	public String getBIMHeadName(Block b);

	public void updateApearence(String id);
	public void updateApearence(Block b);
	public void updateApearence(ItemStack item);
	public void updateChestHoldApearence(String id);
	public void updateInventoryHoldApearence(Inventory inv, String id);

	public boolean isSameBIM(ItemStack a, ItemStack b);

}