package com.jack.btooom.data;

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
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.heads.Heads;
import com.jack.btooom.threads.BIMTimeController;
import com.jack.btooom.util.ItemHelper;
import com.jack.btooom.util.SerializeLocation;
import com.jack.btooom.util.UtilRandom;
import com.jack.btooom.util.BIMThrowAndHit;

public class BIMStore implements BIMStoreAPI{

	public void loadAllConfigs() {
		loadPlayerBIMsList();
		removeUnusedBIMs();
	}

	private YamlConfiguration loadPlayerBIMsList() {
		File adr = Core.bimfile;
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration list = YamlConfiguration.loadConfiguration(adr);

				list.options().copyDefaults(true);
				list.save(adr);
			} catch (IOException ex) {

			}
		}
		return YamlConfiguration.loadConfiguration(adr);
	}

	private int maxLiveHoursOfBIM = 24;
	private String lastUse = "lastUse";
	private boolean updateLastTimeUse = false;

	private void updateLastUseTime(String id) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + lastUse, new Date());
		saveFile(list);
	}

	private Date getLastUseTime(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (Date) list.get(id + "." + lastUse);
	}

	public void removeUnusedBIMs() {
		YamlConfiguration list = loadPlayerBIMsList();
		Date now = new Date();
		List<String> toDelete = new ArrayList<String>();
		for (String key : list.getKeys(false)) {
			String id = list.getString(key);
			Date item = getLastUseTime(id);
			if (item != null) {
				Calendar help = Calendar.getInstance();
				help.setTime(item);
				help.add(Calendar.HOUR_OF_DAY, maxLiveHoursOfBIM);
				item = help.getTime();
				if (now.after(item)) {
					toDelete.add(id);
				}
			}
		}
		for (String id : toDelete) {
			list.set(id, null);
		}
	}

	// activ + split + type + split + headName + split + owner;
	private String activTyp = "activ";
	private String typTyp = "typ";
	private String headNameTyp = "headName";
	private String ownerTyp = "owner";

	private String storeTyp = "storedAs";
	private final String storedAsBlock = "block";
	private final String storedAsPlayerHold = "player";

	private final String storedAsChestHold = "chest";

	private final String storedAsEntity = "entity";

	/**
	 * Adding Skull BIM to exsisting BIM File
	 */
	private void addToBIMFile(String id, String activ, String typ, String headName, String owner) {
		setBIMActiv(id, activ);
		setBIMTyp(id, typ);
		setBIMHeadName(id, headName);
		setBIMOwner(id, owner);
	}

	private int length = 16;

	public String createID() {
		return UtilRandom.getRandomString(length);
	}

	/**
	 * ******************PLACE AND BREAK BLOCK******************
	 */

	/**
	 * Place a BIM from Players Hand onto Players Facing Block
	 * @param event PlayerInteractEvent
	 */
	public void BIMplace(PlayerInteractEvent event) {
		event.setCancelled(true);

		Player p = event.getPlayer();
		ItemStack bim = p.getItemInHand();
		Block clicked = event.getClickedBlock();
		BlockFace face = event.getBlockFace();
		Vector v = p.getLocation().getDirection();

		Block b = clicked.getRelative(face);

		BIMplace(b, bim, face, v, p);
	}

	/**
	 * Place a BIM from Players Hand onto Players Facing Block
	 * @param b Block where skull will be
	 * @param item Item which Player is holding
	 * @param face BlockFace where Skull is being to be set
	 * @param v Vector to calc the rotation of the Skull
	 * @param p Player which will Place BIM
	 */
	public void BIMplace(Block b, ItemStack item, BlockFace face, Vector v, Player p) {
		// Place Block from Item
		Heads.setSkullBlock(b, item);
		Heads.setSkullRotationAutomatic(b, face, v);
		ItemHelper.getOneItemLess(p, item);

		// Change data in BIMFile
		String id = getBIMIdentity(item);
		resetStoreTypAndStoredAsValues(id);
		setBIMStoreTyp(id, b);
	}

	public void BIMbreak(PlayerInteractEvent event) {
		event.setCancelled(true);
		Player p = event.getPlayer();
		Block b = event.getClickedBlock();
		BIMbreak(b, p);
	}

	public void BIMbreak(Block b, Player p) {
		String id = Heads.getSkullID(b);
		ItemStack item = ConfigLoader.getExsistingBIMItem(id);
		b.setType(Material.AIR);

		BIMGivePlayer(item, id, p);
	}

	public void BIMGivePlayer(ItemStack item, String id, Player p) {
		if (ItemHelper.pickUpItem(p, item)) {
			resetStoreTypAndStoredAsValues(id);
			setBIMStoreTyp(id, p);
		}
	}

	public void BIMChangeInventory(Inventory from, Inventory to, Player p, ItemStack bim, Location loc) {
		Bukkit.broadcastMessage("Change BIM from: " + from.getType() + " to: " + to.getType());

		if (ItemHelper.hasFreeSpaceInInventory(to)) {
			String id = getBIMIdentity(bim);

			boolean toContainer = !p.getInventory().equals(to);
			Bukkit.broadcastMessage("Into Container?: " + toContainer);

			resetStoreTypAndStoredAsValues(id);

			ItemHelper.getOneItemLess(from, bim);
			ItemHelper.pickUpItem(to, ConfigLoader.getExsistingBIMItem(id));

			if (toContainer) {
				setBIMStoreTyp(id, loc);
			} else {

				setBIMStoreTyp(id, p);
			}
		} else {
			return;
		}

	}

	public boolean BIMThrow(ItemStack item, Player p) {
		String id = getBIMIdentity(item);
		ItemHelper.getOneItemLess(p, item);
		if (p.getItemInHand().getType() == Material.AIR) {
			Entity entity = BIMThrowAndHit.throwNormal(id, p);
			entity.setCustomName(id);

			resetStoreTypAndStoredAsValues(id);
			setBIMStoreTyp(id, entity);
			return true;
		}
		else{
			p.setItemInHand(ConfigLoader.getExsistingBIMItem(id));
			return false;
		}
	}

	public void BIMDropLikeThrow(ItemSpawnEvent event) {
		event.setCancelled(true);
		ItemStack item = event.getEntity().getItemStack();
		String id = getBIMIdentity(item);

		Player thrower = null;
		try {
			thrower = (Player) event.getEntity().getNearbyEntities(0, 0, 0).get(0);
		} catch (IndexOutOfBoundsException e) {

		}

		Entity entity = BIMThrowAndHit.throwLikeDrop(id, thrower);

		resetStoreTypAndStoredAsValues(id);
		setBIMStoreTyp(id, entity);
	}

	public void BIMDropLikeDrop(ItemSpawnEvent event) {
		event.setCancelled(false);

		BIMDropLikeDrop(event.getEntity());
	}

	public void BIMDropLikeDrop(Item entity) {
		ItemStack item = entity.getItemStack();

		String id = getBIMIdentity(item);
		entity.setCustomName(id);

		resetStoreTypAndStoredAsValues(id);
		setBIMStoreTyp(id, entity);
	}

	public void BIMPickup(PlayerPickupItemEvent event) {
		event.setCancelled(true);
		BIMPickup(event.getItem(), event.getPlayer());
	}

	public void BIMPickup(Entity pickup, Player p) {
		String id = getBIMIdentity(pickup);
		ItemStack item = ConfigLoader.getExsistingBIMItem(id);

		if (ItemHelper.pickUpItem(p, item)) {
			pickup.remove();
			resetStoreTypAndStoredAsValues(id);
			setBIMStoreTyp(id, p);
		}
	}

	public void givePlayerNewBIM(Player p, String typ) {
		ItemStack bim = ConfigLoader.getNewBIMItem(typ);

		registerBIM(bim, p, typ);
		p.getInventory().addItem(bim);
	}

	public void registerBIM(ItemStack item, Player p, String typ) {
		String id = getBIMIdentity(item);
		addToBIMFile(id, BIMs.inactiv, typ, typ, p.getUniqueId().toString());

		if (updateLastTimeUse) {
			updateLastUseTime(id);
		}

		setBIMStoreTyp(id, p);
	}

	public void destroyBIM(String id) {
		String storeTyp = getBIMStoreTyp(id);

		switch (storeTyp) {
		case storedAsBlock:
			getBIMStoredAsBlockBlock(id).setType(Material.AIR);
			break;
		case storedAsPlayerHold:
			Player p = getBIMStorePlayerHoldPlayer(id);
			removeBIMFromInventory(p.getInventory(), id);
			break;
		case storedAsChestHold:
			Inventory inv = getBIMStoredAsChestHold(id);
			removeBIMFromInventory(inv, id);
			break;
		case storedAsEntity:
			Entity entity = getBIMStoredAsEntityEntity(id);
			entity.remove();
			break;
		}

		// unregisterBIM in all Case
		unregisterBIM(id);
	}

	public void removeBIMFromInventory(Inventory inv, String id) {
		int place = ItemHelper.getPlaceNumberOfItemStackInInventory(inv, id);
		if (place != -1) {
			Bukkit.broadcastMessage("BIMStore: set AIR");
			inv.setItem(place, new ItemStack(Material.AIR));
		}
	}

	public Location getBIMLocation(String id) {
		String storeTyp = getBIMStoreTyp(id);

		switch (storeTyp) {
		case storedAsBlock:
			return getBIMStoredAsBlockBlock(id).getLocation();
		case storedAsPlayerHold:
			return getBIMStorePlayerHoldPlayer(id).getLocation();
		case storedAsChestHold:
			return getBIMStoredAsChestHoldLocation(id);
		case storedAsEntity:
			Entity entity = getBIMStoredAsEntityEntity(id);
			if (entity == null)
				return null;
			return entity.getLocation();
		}
		return null;
	}

	public void unregisterBIM(String id) {
		BIMTimeController.removeActivBIMID(id);

		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id, null);
		saveFile(list);
	}

	/**
	 * *****************GETTER AND SETTER************************************
	 */

	private void saveFile(YamlConfiguration list) {
		try {
			list.options().copyDefaults(true);
			list.save(Core.bimfile);
		} catch (IOException e) {

		}
	}

	private void resetStoreTypAndStoredAsValues(String id) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + storeTyp, null);

		list.set(id + "." + storedAsBlock, null);
		list.set(id + "." + storedAsPlayerHold, null);
		list.set(id + "." + storedAsChestHold, null);
		list.set(id + "." + storedAsEntity, null);

		if (updateLastTimeUse) {
			list.set(id + "." + lastUse, new Date());
		}

		saveFile(list);
	}

	/**
	 * ************Storetyp**********
	 */

	public void setBIMStoreTyp(String id, String storedAs) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + storeTyp, storedAs);
		saveFile(list);
	}

	public String getBIMStoreTyp(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (String) list.get(id + "." + storeTyp);
	}

	public void setBIMStoreTyp(String id, Player p) {
		setBIMStoreTyp(id, storedAsPlayerHold);

		YamlConfiguration list = loadPlayerBIMsList();
		list.set(id + "." + storedAsPlayerHold, p.getUniqueId().toString());
		saveFile(list);
	}

	public void setBIMStoreTyp(String id, Block b) {
		setBIMStoreTyp(id, storedAsBlock);

		YamlConfiguration list = loadPlayerBIMsList();
		list.set(id + "." + storedAsBlock, SerializeLocation.toString(b.getLocation()));
		saveFile(list);
	}

	public void setBIMStoreTyp(String id, Entity item) {
		setBIMStoreTyp(id, storedAsEntity);

		YamlConfiguration list = loadPlayerBIMsList();
		list.set(id + "." + storedAsEntity, item.getEntityId());
		saveFile(list);
	}

	public void setBIMStoreTyp(String id, Location loc) {
		setBIMStoreTyp(id, storedAsChestHold);

		YamlConfiguration list = loadPlayerBIMsList();
		list.set(id + "." + storedAsChestHold, SerializeLocation.toString(loc));
		saveFile(list);
	}

	/*
	 * *************BlockStoreTyp**********
	 */

	public String getBIMStoreBlockLocationAsString(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return list.getString(id + "." + storedAsBlock);
	}

	public Block getBIMStoredAsBlockBlock(String id) {
		String locAsString = getBIMStoreBlockLocationAsString(id);
		Location loc = SerializeLocation.fromString(locAsString);
		return loc.getBlock();
	}

	/*
	 * ************PlayerStoreTyp**********
	 */

	private String getBIMStorePlayerHoldString(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return list.getString(id + "." + storedAsPlayerHold);
	}

	public Player getBIMStorePlayerHoldPlayer(String id) {
		return Bukkit.getPlayer(UUID.fromString(getBIMStorePlayerHoldString(id)));
	}

	/*
	 * ***********EntityStoreTyp***********
	 */

	private String getBIMStoredAsItemString(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return list.getString(id + "." + storedAsEntity);
	}

	public Entity getBIMStoredAsEntityEntity(String id) {
		String sid = getBIMStoredAsItemString(id);
		Player p = getBIMOwner(id);
		int eid = Integer.parseInt(sid);
		List<Entity> ents = p.getWorld().getEntities();
		for (Entity ent : ents) {
			if (ent.getEntityId() == eid) {
				return ent;
			}
		}
		return null;
	}

	/*
	 * **********InventoryHolder************
	 */

	public String getBIMStoreChestHoldLocationAsString(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return list.getString(id + "." + storedAsChestHold);
	}

	public Location getBIMStoredAsChestHoldLocation(String id) {
		String locAsString = getBIMStoreChestHoldLocationAsString(id);
		return SerializeLocation.fromString(locAsString);
	}

	public Inventory getBIMStoredAsChestHold(String id) {
		Location loc = getBIMStoredAsChestHoldLocation(id);
		BlockState state = loc.getBlock().getState();

		if (state instanceof InventoryHolder) {
			return ((InventoryHolder) state).getInventory();
		} else {
			return null;
		}

	}

	/**
	 * ***********OWNER***********
	 */

	public void setBIMOwner(String id, String owner) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + ownerTyp, owner);
		saveFile(list);
	}

	private String getBIMOwnerUUID(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (String) list.get(id + "." + ownerTyp);
	}

	public Player getBIMOwner(String id) {
		UUID uuid = UUID.fromString(getBIMOwnerUUID(id));
		return Bukkit.getPlayer(uuid);
	}

	public void setBIMOwner(ItemStack item, String owner) {
		setBIMOwner(getBIMIdentity(item), owner);
	}

	public String getBIMOwner(ItemStack item) {
		return getBIMOwnerUUID(getBIMIdentity(item));
	}

	public void setBIMOwner(Block b, String owner) {
		setBIMOwner(getBIMIdentity(b), owner);
	}

	public String getBIMOwner(Block b) {
		return getBIMOwnerUUID(getBIMIdentity(b));
	}

	/*
	 * ***************isOWner***********
	 */

	public boolean isBIMOwner(Player p, String id) {
		return getBIMOwnerUUID(id).equals(p.getUniqueId().toString());
	}

	public boolean isBIMOwner(Player p, ItemStack item) {
		return getBIMOwnerUUID(getBIMIdentity(item)).equals(p.getUniqueId().toString());
	}

	public boolean isBIMOwner(Player p, Block b) {
		return getBIMOwnerUUID(getBIMIdentity(b)).equals(p.getUniqueId().toString());
	}

	/**
	 * ****************isBIM****************
	 */

	public boolean isBIM(String id) {
		if (id == null)
			return false;
		return loadPlayerBIMsList().contains(id);
	}

	public boolean isBIM(Block b) {
		return isBIM(getBIMIdentity(b));
	}

	public boolean isBIM(ItemStack item) {
		return isBIM(getBIMIdentity(item));
	}

	public boolean isBIM(Entity item) {
		return isBIM(getBIMIdentity(item));
	}

	/**
	 * ***********ACTIV***********
	 */

	private void setBIMActiv(String id, String activ) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + activTyp, activ);
		saveFile(list);

		if (activ.equals(BIMs.activ))
			BIMTimeController.addActivBIMID(id);
		if (activ.equals(BIMs.inactiv))
			BIMTimeController.removeActivBIMID(id);
	}

	public String getBIMActive(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (String) list.get(id + "." + activTyp);
	}

	public void setBIMActive(ItemStack item, String activ) {
		setBIMActiv(getBIMIdentity(item), activ);
	}

	public String getBIMActive(ItemStack item) {
		return getBIMActive(getBIMIdentity(item));
	}

	public void setBIMActive(Block b, String activ) {
		setBIMActiv(getBIMIdentity(b), activ);
	}

	public String getBIMActive(Block b) {
		return getBIMActive(getBIMIdentity(b));
	}

	/*
	 * ************Activate**********
	 */

	public void activateBIM(String id) {
		setBIMActiv(id, BIMs.activ);
	}

	public void activateBIM(ItemStack item) {
		activateBIM(getBIMIdentity(item));
	}

	public void activateBIM(Block b) {
		activateBIM(getBIMIdentity(b));
	}

	/*
	 * ***********Deactivate**********
	 */

	public void deactivateBIM(String id) {
		setBIMActiv(id, BIMs.inactiv);
	}

	public void deactivateBIM(ItemStack item) {
		deactivateBIM(getBIMIdentity(item));
	}

	public void deactivateBIM(Block b) {
		deactivateBIM(getBIMIdentity(b));
	}

	/*
	 * ************isActiv*********
	 */

	public boolean isBIMActiv(String id) {
		return getBIMActive(id).equals(BIMs.activ);
	}

	public boolean isBIMActiv(ItemStack item) {
		return isBIMActiv(getBIMIdentity(item));
	}

	public boolean isBIMActiv(Block b) {
		return isBIMActiv(getBIMIdentity(b));
	}

	/*
	 * ***********isInactiv*********
	 */

	public boolean isBIMInactiv(String id) {
		return getBIMActive(id).equals(BIMs.inactiv);
	}

	public boolean isBIMInactiv(ItemStack item) {
		return isBIMInactiv(getBIMIdentity(item));
	}

	public boolean isBIMInactiv(Block b) {
		return isBIMInactiv(getBIMIdentity(b));
	}

	/**
	 * ***********TYP***********
	 */

	private void setBIMTyp(String id, String typ) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + typTyp, typ);
		saveFile(list);
	}

	public String getBIMType(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (String) list.get(id + "." + typTyp);
	}

	public void setBIMType(ItemStack item, String typ) {
		setBIMTyp(getBIMIdentity(item), typ);
	}

	public String getBIMType(ItemStack item) {
		return getBIMType(getBIMIdentity(item));
	}

	public void setBIMType(Block b, String typ) {
		setBIMTyp(getBIMIdentity(b), typ);
	}

	public String getBIMType(Block b) {
		return getBIMType(getBIMIdentity(b));
	}

	/**
	 * ****************Identity****************
	 */

	public String getBIMIdentity(Block b) {
		if (b.getType() != Material.SKULL)
			return null;
		return Heads.getSkullID(b);
	}

	public String getBIMIdentity(ItemStack item) {
		List<String> lores = ConfigLoader.getLores(item);
		if (lores == null)
			return null;
		return lores.get(0);
	}

	public String getBIMIdentity(Entity item) {
		return item.getCustomName();
	}

	/**
	 * ****************HeadName****************
	 */

	private void setBIMHeadNameWithApearence(String id, String headName) {
		setBIMHeadName(id, headName);
		updateApearence(id);
	}

	public void setBIMHeadName(String id, String headName) {
		YamlConfiguration list = loadPlayerBIMsList();

		list.set(id + "." + headNameTyp, headName);
		saveFile(list);
	}

	public String getBIMHeadName(String id) {
		YamlConfiguration list = loadPlayerBIMsList();
		return (String) list.get(id + "." + headNameTyp);
	}

	public void setBIMHeadName(ItemStack item, String headName) {
		setBIMHeadNameWithApearence(getBIMIdentity(item), headName);
	}

	public void setBIMHeadName(Block b, String headName) {
		setBIMHeadNameWithApearence(getBIMIdentity(b), headName);
	}

	public String getBIMHeadName(ItemStack item) {
		return getBIMHeadName(getBIMIdentity(item));
	}

	public String getBIMHeadName(Block b) {
		return getBIMHeadName(getBIMIdentity(b));
	}

	/*
	 * **************UpdateItemAndBlock**********
	 */

	public void updateApearence(String id) {
		String storeTyp = getBIMStoreTyp(id);

		switch (storeTyp) {
		case storedAsBlock:
			updateBlockApearence(id);
			break;
		case storedAsPlayerHold:
			updatePlayerHoldApearence(id);
			break;
		case storedAsChestHold:
			updateChestHoldApearence(id);
			break;
		case storedAsEntity:
			updateBIMEntity(id);
			break;
		}
	}

	public void updateApearence(Block b) {
		updateApearence(getBIMIdentity(b));
	}

	public void updateApearence(ItemStack item) {
		updateApearence(getBIMIdentity(item));
	}

	public void updateChestHoldApearence(String id) {
		Inventory inv = getBIMStoredAsChestHold(id);
		updateInventoryHoldApearence(inv, id);
	}

	private void updateBlockApearence(String id) {
		String stringLocation = getBIMStoreBlockLocationAsString(id);
		Location loc = SerializeLocation.fromString(stringLocation);
		Block b = loc.getBlock();

		int rotation = Heads.getSkullRotation(b);
		int facing = Heads.getSkullFacing(b);

		Heads.setSkullBlock(b, id);

		Heads.setSkullRotationAndFacing(b, rotation, facing);
	}

	private void updatePlayerHoldApearence(String id) {
		Player p = getBIMStorePlayerHoldPlayer(id);
		updatePlayerHoldApearence(p, id);
	}

	public boolean isSameBIM(ItemStack a, ItemStack b) {
		return getBIMIdentity(a).equals(getBIMIdentity(b));
	}

	private void updatePlayerHoldApearence(Player p, String id) {
		updateInventoryHoldApearence(p.getInventory(), id);
	}

	public void updateInventoryHoldApearence(Inventory inv, String id) {
		int number = ItemHelper.getPlaceNumberOfItemStackInInventory(inv, id);
		if (number == -1)
			return;

		ItemStack lookAt = inv.getItem(number);
		inv.setItem(number, updateBIMItem(lookAt));

	}

	private ItemStack updateBIMItem(ItemStack item) {
		String id = getBIMIdentity(item);
		ItemStack back = ConfigLoader.getExsistingBIMItem(id);
		back.setAmount(item.getAmount());
		return back;
	}

	private void updateBIMEntity(String id) {
		Entity item = getBIMStoredAsEntityEntity(id);
		if (!(item instanceof Snowball)) {
			Location loc = item.getLocation();
			Location clear = new Location(loc.getWorld(), loc.getBlockX() + 0.5, loc.getBlockY(),
					loc.getBlockZ() + 0.5);
			item.remove();
			Entity newItem = loc.getWorld().dropItem(clear, ConfigLoader.getExsistingBIMItem(id));
			newItem.setVelocity(newItem.getVelocity().zero());
			newItem.setFallDistance(0);
			setBIMStoreTyp(id, newItem);
		}
	}

}
