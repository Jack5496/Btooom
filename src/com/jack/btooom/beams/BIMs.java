package com.jack.btooom.beams;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

import com.jack.btooom.Core;
import com.jack.btooom.API.AddonAPI;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.addons.AddonLoader;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.util.BIMThrowAndHit;

public class BIMs {

	public final static String activ = "on";
	public final static String inactiv = "off";

	public static List<String> getBIMList() {
		return ConfigLoader.getBIMList();
	}
	
	public static void rightClickAir(PlayerInteractEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack itemInHand = event.getPlayer().getItemInHand();
		if (bimStore.isBIM(itemInHand)) {
			String typ = bimStore.getBIMType(itemInHand);
			
			AddonLoader.getBimClass(typ).rightClickAir(event);
		}
	}

	public static void rightClickBlock(PlayerInteractEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack itemInHand = event.getPlayer().getItemInHand();
		Block clicked = event.getClickedBlock();
		if (bimStore.isBIM(clicked)) {
			String typ = bimStore.getBIMType(clicked);
			AddonLoader.getBimClass(typ).rightClickBIM(event);
		} else {
			if (bimStore.isBIM(itemInHand)) {
				boolean container = false;

				BlockState containerState = clicked.getState();
				InventoryHolder containerTyp = null;

				if (containerState instanceof InventoryHolder) {

					container = true;
					containerTyp = (InventoryHolder) clicked.getState();
				}

				Bukkit.broadcastMessage("is BIM");
				Player p = event.getPlayer();

				boolean sneak = p.isSneaking();

				if ((sneak && container) || !container) {
					Bukkit.broadcastMessage("sneak and cont or not cont");

					String typ = bimStore.getBIMType(itemInHand);
					AddonLoader.getBimClass(typ).rightClickBlock(event);
//					
				} else {
					Bukkit.broadcastMessage("must be container");

					if (container) {
						p.openInventory(containerTyp.getInventory());
					}

					//chest wont close else later and droppers wont open
					if (clicked.getType() == Material.CHEST || clicked.getType() == Material.TRAPPED_CHEST) {
						event.setCancelled(true);
					}
				}

			}
		}
	}

	public static void leftClickAir(PlayerInteractEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack itemInHand = event.getPlayer().getItemInHand();
		if (bimStore.isBIM(itemInHand)) {
			String typ = bimStore.getBIMType(itemInHand);
			AddonLoader.getBimClass(typ).leftClickAir(event);
//			
		}
	}

	public static void leftClickBlock(PlayerInteractEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		Block clicked = event.getClickedBlock();
		if (bimStore.isBIM(clicked)) {
			String typ = bimStore.getBIMType(clicked);
			AddonLoader.getBimClass(typ).leftClickBIM(event);
//			
		}
	}

	public static void onHit(ProjectileHitEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		if ((event.getEntity() instanceof Snowball)) {
			Entity snowball = event.getEntity();

			BlockIterator iterator = new BlockIterator(event.getEntity().getWorld(),
					event.getEntity().getLocation().toVector(), event.getEntity().getVelocity().normalize(), 0.0D, 4);
			Block hitBlock = null;

			while (iterator.hasNext()) {
				hitBlock = iterator.next();

				if (hitBlock.getTypeId() != 0) {
					break;
				}
			}

			Snowball sn = (Snowball) snowball;
			String id = sn.getCustomName();

			if (bimStore.isBIM(id)) {

				Location land = BIMThrowAndHit.getLandingLocation(hitBlock.getLocation(), snowball.getLocation());

				Player p = (Player) event.getEntity().getShooter();
				ItemStack bim = ConfigLoader.getExsistingBIMItem(id);
				Block clicked = hitBlock;
				BlockFace face = BIMThrowAndHit.getRelativ6NeighBlockFace(clicked.getLocation(), land);

				Vector v = event.getEntity().getVelocity();
				
				Block b = clicked.getRelative(face);

				bimStore.BIMplace(b, bim, face, v, p);

				String typ = bimStore.getBIMType(id);
				AddonLoader.getBimClass(typ).onHitGround(id);
//				
			}
		}

	}

	public static void dropBIM(ItemSpawnEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack bim = event.getEntity().getItemStack();
		if (bimStore.isBIM(bim)) {
			String typ = bimStore.getBIMType(bim);
			AddonLoader.getBimClass(typ).dropBIM(event);
		}
	}

	public static void itemDespawn(ItemDespawnEvent event) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		String id = bimStore.getBIMIdentity(event.getEntity());

		if (bimStore.isBIM(id)) {
			bimStore.destroyBIM(id);
		}
	}

}
