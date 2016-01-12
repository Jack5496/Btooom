package com.jack.btooom.util;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.threads.BIMTimeController;

import de.slikey.effectlib.effect.DnaEffect;
import de.slikey.effectlib.effect.EarthEffect;
import de.slikey.effectlib.effect.ExplodeEffect;
import de.slikey.effectlib.effect.HelixEffect;
import de.slikey.effectlib.effect.SmokeEffect;
import de.slikey.effectlib.effect.TextEffect;
import de.slikey.effectlib.effect.VortexEffect;

public class LocationDestroy {

	public static boolean destroyBlockWithBIM(Location loc) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();

		Block b = loc.getBlock();
		Material type = b.getType();

		if (type == Material.SKULL) {
			if (bimStore.isBIM(b)) {
				bimStore.destroyBIM(bimStore.getBIMIdentity(b));
				// Skull wird gleich mit zerstört
				return true;
			}
		}
		if (type != Material.AIR) {
			BlockState state = b.getState();
			if (state instanceof InventoryHolder) {
				Inventory inv = ((InventoryHolder) state).getInventory();

				boolean ret = false;

				ItemStack bim = ItemHelper.getBIMifAvaible(inv);
				while (bim != null) {
					ret = true;
					bimStore.destroyBIM(bimStore.getBIMIdentity(bim));
				}

				return false;
			}
		}

		b.setType(Material.AIR);
		return false;
	}

	

}
