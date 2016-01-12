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
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import com.jack.btooom.Core;
import com.jack.btooom.beams.BIM;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.heads.Heads;
import com.jack.btooom.threads.BIMTimeController;
import com.jack.btooom.util.BIMThrowAndHit;
import com.jack.btooom.util.ItemHelper;
import com.jack.btooom.util.SerializeLocation;
import com.jack.btooom.util.UtilRandom;

public interface AddonAPI {
	List<Class<? extends BIM>> getAllBIMs();
	File getFileDest();
	void saveInformationsForDisable();
}