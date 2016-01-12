package com.jack.btooom.heads;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import com.jack.btooom.Core;
import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.util.UtilRandom;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntitySkull;

public class Heads {

	public static ItemStack getHead(String url) {
		return getHead(url, url);
	}

	public static ItemStack getHead(String url, String id) {

		UUID uuid = UUID.randomUUID();

		ItemStack item = getSkull(id, url, uuid);

		return item;
	}

	/**
	 * Get ItemStack head
	 * 
	 * @param name
	 * @param url
	 * @param uuid
	 * @return
	 */
	private static ItemStack getSkull(String name, String url, UUID uuid) {

		GameProfile profile = new GameProfile(uuid, (String) null);
		PropertyMap propertyMap = profile.getProperties();

		if (propertyMap == null)
			throw new IllegalStateException("Profile doesn't contain a property map!");

		char[] encoded = Base64Coder.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
		propertyMap.put("textures", new Property("Value", new String(encoded)));

		ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		ItemMeta headMeta = itemStack.getItemMeta();

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);
		} catch (NoSuchFieldException | SecurityException e) {
			Bukkit.getLogger().log(Level.SEVERE, "No such method exception during reflection.", e);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			Bukkit.getLogger().log(Level.SEVERE, "Unable to use reflection.", e);
		}

		itemStack.setItemMeta(headMeta);
		return itemStack;
	}

	// Real Method
	private static GameProfile getNonPlayerProfile(String skinURL, boolean randomName, String name) {
		GameProfile newSkinProfile = new GameProfile(UUID.randomUUID(),
				randomName ? UtilRandom.getRandomString(16) : name);
		newSkinProfile.getProperties().put("textures",
				new Property("textures", Base64Coder.encodeString("{textures:{SKIN:{url:\"" + skinURL + "\"}}}")));
		return newSkinProfile;
	}

	// Example Usage
	private static void setSkullWithNonPlayerProfile(String skinURL, boolean randomName, Block skull, String name) {
		if (skull.getType() != Material.SKULL)
			throw new IllegalArgumentException("Block must be a skull.");
		TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle()
				.getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
		skullTile.setGameProfile(getNonPlayerProfile(skinURL, randomName, name));

		skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
	}
	
	public static void setSkullRotationAndFacing(Block skull, int rotation, int facing) {
		setSkullRotation(skull, rotation);
		setSkullFacing(skull, facing);
	}

	public static void setSkullFacing(Block skull, int i) {
		if (skull.getType() != Material.SKULL)
			return;
		TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle()
				.getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
		skullTile.setRotation(i);
		skull.getWorld().refreshChunk(skull.getChunk().getX(), skull.getChunk().getZ());
	}

	public static int getSkullFacing(Block skull) {
		if (skull.getType() != Material.SKULL)
			return 0;
		TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle()
				.getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
		return skullTile.getRotation();
	}

	public static void setSkullRotation(Block block, int i) {
		if (block.getType() != Material.SKULL)
			return;

		Skull skull = (Skull) block.getState();
		skull.setRawData((byte) i);
		skull.update();
	}

	public static int getSkullRotation(Block block) {
		if (block.getType() != Material.SKULL)
			return 0;

		Skull skull = (Skull) block.getState();
		return skull.getRawData();
	}

	static final double part = (2.0 / 9.0);

	private static int getSkullFacing(double x, double z) {
		double degree = (Math.atan2(x, z) + Math.PI) / Math.PI * 8; // gets the
																	// degree in
																	// intervall
																	// from 0-15
																	// where
																	// 16=0
		return 8 - ((int) (degree + 0.5)); // add 0.5 because want to find out
											// right start after cast
		// return 8-x becuase its inverted
	}

	private static int getRotationFromBlockFace(BlockFace face) {
		switch (face) {
		case UP:
			return 1;
		case DOWN:
			return 1;
		case NORTH:
			return 2;
		case SOUTH:
			return 3;
		case WEST:
			return 4;
		case EAST:
			return 5;
		default:
			break;
		}
		return 1;
	}

	public static void setSkullRotationAutomatic(Block block, BlockFace face, Vector playerLocationDirection) {
		int rotation = getRotationFromBlockFace(face);
		setSkullRotation(block, rotation);

		if (rotation == 1) {
			int facing = getSkullFacing(playerLocationDirection.getX(), playerLocationDirection.getZ());
			setSkullFacing(block, facing);
		}
	}
	
	public static String getSkullID(Block skull) {
		if (skull.getType() != Material.SKULL)
			throw new IllegalArgumentException("Block must be a skull.");
		TileEntitySkull skullTile = (TileEntitySkull) ((CraftWorld) skull.getWorld()).getHandle()
				.getTileEntity(new BlockPosition(skull.getX(), skull.getY(), skull.getZ()));
		return skullTile.getGameProfile().getName();
	}

	/**
	 * Set a Skull Block
	 * 
	 * @param b
	 *            Block position
	 * @param activ
	 *            If Skull is activ
	 * @param type
	 *            Type of Skull
	 * @param headName
	 *            HeadName
	 * @param owner
	 *            Who placed block
	 */
	public static void setSkullBlock(Block b, ItemStack item) {
		String id = Core.getInstance().getBIMStore().getBIMIdentity(item);

		int rotation = getSkullRotation(b);
		int facing = getSkullFacing(b);

		setSkullBlock(b, id);

		setSkullRotation(b, rotation);
		setSkullFacing(b, facing);
	}

	public static void setSkullBlock(Block b, String id) {
		String url = ConfigLoader.getURL(Core.getInstance().getBIMStore().getBIMHeadName(id));
		b.setType(Material.SKULL);
		setSkullWithNonPlayerProfile(url, false, b, id);
	}

}