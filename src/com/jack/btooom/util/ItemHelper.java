package com.jack.btooom.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;

public class ItemHelper {

	public static void getOneItemLess(Player p, ItemStack item) {
		getOneItemLess(p.getInventory(), item);
	}

	public static void getOneItemLess(Inventory inv, ItemStack item) {
		int amount = item.getAmount();
		if (amount > 1) {
			item.setAmount(amount - 1);
			return;
		}
		inv.remove(item);
		return;
	}
	
	public static void getOneBIMLess(Inventory inv, String id){
		ItemStack item = ConfigLoader.getExsistingBIMItem(id);
		getOneItemLess(inv, item);
	}

	public static boolean pickUpItem(Player p, ItemStack item) {
		Inventory inv = p.getInventory();
		return pickUpItem(inv, item);
	}

	public static boolean pickUpItem(Inventory inv, ItemStack item) {
		if (hasFreeSpaceInInventory(inv)) {
			int pos = emptyItemSlot(inv);
			inv.setItem(pos, item);

			return true;
		} else {
			return false;
		}
	}

	public static boolean hasFreeSpaceInInventory(Inventory inv) {
		if (emptyItemSlot(inv) != -1)
			return true;
		return false;
	}
	
	public static int getPlaceNumberOfItemStackInInventory(Inventory inv, String id) {
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack[] inventory = inv.getContents();
		int max = inventory.length;
		for (int i = 0; i < max; i++) {
			ItemStack lookAt = inventory[i];
			if (bimStore.isBIM(lookAt)) {
				if (bimStore.getBIMIdentity(lookAt).equals(id)) {
					return i;
				}
			}
		}
		return -1;
	}
	
	public static ItemStack getBIMifAvaible(Inventory inv){
		
		BIMStoreAPI bimStore = Core.getInstance().getBIMStore();
		
		ItemStack[] inventory = inv.getContents();
		int max = inventory.length;
		for (int i = 0; i < max; i++) {
			ItemStack lookAt = inventory[i];
			if (bimStore.isBIM(lookAt)) {
				return lookAt;
			}
		}
		return null;
	}

	private static int emptyItemSlot(Inventory inv) {
		for (int i = 0; i < inv.getSize(); i++) {
			if (inv.getItem(i) == null) {
				return i;
			}
		}
		return -1;
	}

	public static void setPlayersHand(Player p, ItemStack item) {
		p.setItemInHand(item);
		p.updateInventory();
	}

}
