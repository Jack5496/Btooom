package com.jack.btooom.events;

import java.util.Arrays;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.block.Dropper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.util.LocationDestroy;

import de.slikey.effectlib.effect.BleedEffect;
import de.slikey.effectlib.effect.HelixEffect;

public class Events implements Listener {

	/*
	 * ToDO
	 */

	


	/*
	 * Done
	 */
	
	@EventHandler
	public void EntityChangeBlockEvent(EntityChangeBlockEvent event) {
		event.setCancelled(Core.getInstance().getBIMStore().isBIM(event.getBlock()));
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		LocationDestroy.destroyBlockWithBIM(event.getBlock().getLocation());
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent evt) {
	    if (evt instanceof PlayerDeathEvent) {
	        PlayerDeathEvent event = (PlayerDeathEvent)evt;
	        Player dead = event.getEntity();
	        String name = dead.getKiller().getCustomName();
	        Bukkit.broadcastMessage(ChatColor.GOLD + " " + dead.getDisplayName() + " " + ChatColor.GREEN + "was killed by" + ChatColor.GOLD + " " + name);
//	        if(plugin.inGame == true) {
//	            dead.kickPlayer("You have been killed and you cannot rejoin untill the current game is over.");
//	        }
	    }
	}
	
	@EventHandler
	public void BlockPistonExtendEvent(BlockPistonExtendEvent event) {
		if (event.getBlocks().size() > 0) {
			boolean isSkull = LocationDestroy.destroyBlockWithBIM(event.getBlocks().get(0).getLocation());
			event.setCancelled(isSkull);
		}
	}

	@EventHandler
	public void InventoryPickupItemEvent(InventoryPickupItemEvent event) {
		if (Core.getInstance().getBIMStore().isBIM(event.getItem())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void explode(EntityExplodeEvent event) {
		for (Block b : event.blockList()) {
			LocationDestroy.destroyBlockWithBIM(b.getLocation());
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerInteract(PlayerInteractEvent event) {

		if (event.getAction() == Action.RIGHT_CLICK_AIR) {
			BIMs.rightClickAir(event);
		}
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			BIMs.rightClickBlock(event);
		}
		if (event.getAction() == Action.LEFT_CLICK_AIR) {
			BIMs.leftClickAir(event);
		}
		if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
			BIMs.leftClickBlock(event);
		}
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void PickupItem(PlayerPickupItemEvent event) {
		if (Core.getInstance().getBIMStore().isBIM(event.getItem()))
			Core.getInstance().getBIMStore().BIMPickup(event);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDispense(BlockDispenseEvent event) {
		ItemStack item = event.getItem();

		if (Core.getInstance().getBIMStore().isBIM(item)) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onItemDespawn(ItemDespawnEvent event) {
		BIMs.itemDespawn(event);
	}

	/**
	 * ItemSpawnEvent is also called when player drops item
	 */
	@EventHandler(priority = EventPriority.MONITOR)
	public void onItemSpawn(ItemSpawnEvent event) {
		BIMs.dropBIM(event);
	}

	private static String containerChest = "container.chest";

	private static List<InventoryType> allowed = Arrays.asList(InventoryType.CHEST, InventoryType.DISPENSER,
			InventoryType.DROPPER, InventoryType.PLAYER);

	@EventHandler
	public void onItemMove(InventoryClickEvent event) {

		ItemStack bim = event.getCurrentItem();
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();

		if (bimStore.isBIM(bim)) {
			Inventory top = event.getView().getTopInventory();
			Inventory bottom = event.getView().getBottomInventory();
			InventoryType topType = top.getType();
			InventoryHolder topHolder = top.getHolder();

			if (allowed.contains(topType)) {

				boolean topClicked = event.getRawSlot() < event.getInventory().getSize();

				Location loc = null;

				switch (topType) {
				case CHEST:
					if (!top.getTitle().equals(containerChest)) {
						event.setCancelled(true);
						return;
					}
					loc = ((Chest) topHolder).getLocation();
					break;
				case DISPENSER:
					loc = ((Dispenser) topHolder).getLocation();
					break;
				case DROPPER:
					loc = ((Dropper) topHolder).getLocation();
					break;
				case PLAYER:
					return;
				}

				if (loc == null)
					return;

				Player p = (Player) event.getWhoClicked();

				if (topClicked) {
					bimStore.BIMChangeInventory(top, bottom, p, bim, loc);
				} else {
					bimStore.BIMChangeInventory(bottom, top, p, bim, loc);
				}

				event.setCancelled(true);
				return;
			} else {
				Bukkit.broadcastMessage("Not allowed Container: " + topType);
				event.setCancelled(true);
				return;
			}

		}
		event.setCancelled(false);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onHit(ProjectileHitEvent event) {
		BIMs.onHit(event);
	}



}