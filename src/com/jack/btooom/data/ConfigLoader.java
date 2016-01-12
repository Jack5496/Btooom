package com.jack.btooom.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import com.jack.btooom.Core;
import com.jack.btooom.heads.Heads;

public class ConfigLoader {

	public static void loadAllConfigs() {
		loadConfig();
		loadMessages();
		loadSignConfigs();

		loadHeadList();
		loadBimTypeList();

		Core.getInstance().getBIMStore().loadAllConfigs();
	}

	public static void loadConfig() {
		File adr = Core.configfile;
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration temp = YamlConfiguration.loadConfiguration(adr);
				temp.addDefault("Decay_Tree", "true");
				temp.addDefault("Tree_Menu", "true");
				temp.addDefault("Tree_PvP", "false");
				temp.addDefault("Damage_All_Blocks_on_Explosion", "true");
				temp.addDefault("Only_Damage_Tree_on_Explosion", "false");
				temp.addDefault("Sapling_must_Set_Next_To_Log", "true");
				temp.addDefault("Debug_Mode", "false");
				temp.addDefault("Only_Walk_on_Log", "true");
				temp.addDefault("Create_Sign_On_Knot", "false");
				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		}
		YamlConfiguration ycf = YamlConfiguration.loadConfiguration(adr);
		for (String name : ycf.getKeys(false)) {

			try {
				if (name.equalsIgnoreCase("Decay_Tree")) {
					Core.configs.setDecay_tree(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Tree_Menu")) {
					Core.configs.setTree_menu(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Tree_PvP")) {
					Core.configs.setTree_Pvp(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Damage_All_Blocks_on_Explosion")) {
					Core.configs.setDamage_All_Blocks_on_Explosion(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Only_Damage_Tree_on_Explosion")) {
					Core.configs.setOnly_Damage_Tree_on_Explosion(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Only_Walk_on_Log")) {
					Core.configs.setOnly_Walk_on_Log(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Sapling_must_Set_Next_To_Log")) {
					Core.configs.setSapling_must_Set_Next_To_Log(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Debug_Mode")) {
					Core.configs.setDebug_Mode(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Create_Sign_On_Knot")) {
					Core.configs.setCreate_Sign_On_Knot(ycf.getString(name));
				}

			} catch (Exception ex) {
				ycf.set(name, "INVAILD");
			}
		}
	}

	public static void setConfigsInYML(String name, String value) {
		File adr = Core.configfile;
		if (adr.exists()) {
			try {
				YamlConfiguration temp = YamlConfiguration.loadConfiguration(adr);
				temp.set(name, value);
				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		} else {
			loadConfig();
		}
	}

	public static void loadSignConfigs() {
		File adr = Core.signsfile;
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration temp = YamlConfiguration.loadConfiguration(adr);
				temp.addDefault("Tree Text", "Tree");
				temp.addDefault("Tree Player Text", "T Player");
				temp.addDefault("To Root Text", "To Root");
				temp.addDefault("Total Tree's", "Roots");

				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		}
		YamlConfiguration ycf = YamlConfiguration.loadConfiguration(adr);
		for (String name : ycf.getKeys(false)) {

			try {
				if (name.equalsIgnoreCase("Tree Text")) {
					Core.signconfigs.set_tree_text(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Tree Player Text")) {
					Core.signconfigs.set_tree_player_text(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("To Root Text")) {
					Core.signconfigs.set_to_root_text(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Total Tree's")) {
					Core.signconfigs.set_roots_text(ycf.getString(name));
				}

			} catch (Exception ex) {
				ycf.set(name, "INVAILD");
			}
		}
	}

	public static void loadMessages() {
		File adr = Core.messagefile;
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration temp = YamlConfiguration.loadConfiguration(adr);
				temp.addDefault("Inform_Guild_on_Respawn", "true");
				temp.addDefault("Inform_Guild_on_PlayerJoin", "true");
				temp.addDefault("Inform_Player_of_Non_Tree_PvP", "true");
				temp.addDefault("Inform_Guild_on_Fire", "true");
				temp.addDefault("Inform_Guild_on_Enemy_Grief", "true");
				temp.addDefault("Inform_Guild_on_Explosion", "true");
				temp.addDefault("Inform_Guild_on_TreeGrow", "true");
				temp.addDefault("Inform_Guild_on_RootDestroy", "true");
				temp.addDefault("Inform_Guild_on_GuildJoin", "true");
				temp.addDefault("Inform_Guild_on_GuildLeave", "true");

				temp.addDefault("Inform_Player_on_RootPlace", "true");
				temp.addDefault("Inform_Player_on_Sapling_must_Set_Next_To_Log", "true");
				temp.addDefault("Inform_Player_on_Log_must_Set_Next_To_Log", "false");

				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		}
		YamlConfiguration ycf = YamlConfiguration.loadConfiguration(adr);
		for (String name : ycf.getKeys(false)) {

			try {
				if (name.equalsIgnoreCase("Inform_Guild_on_Respawn")) {
					Core.getMessenger().setInform_Guild_on_Respawn(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_PlayerJoin")) {
					Core.getMessenger().setInform_Guild_on_PlayerJoin(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Player_of_Non_Tree_PvP")) {
					Core.getMessenger().setInform_Player_of_Non_Tree_PvP(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_Fire")) {
					Core.getMessenger().setInform_Guild_on_Fire(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_Enemy_Grief")) {
					Core.getMessenger().setInform_Guild_on_Enemy_Grief(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_Explosion")) {
					Core.getMessenger().setInform_Guild_on_Explosion(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_TreeGrow")) {
					Core.getMessenger().setInform_Guild_on_TreeGrow(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_RootDestroy")) {
					Core.getMessenger().setInform_Guild_on_RootDestroy(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_GuildJoin")) {
					Core.getMessenger().setInform_Guild_on_GuildJoin(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Guild_on_GuildLeave")) {
					Core.getMessenger().setInform_Guild_on_GuildLeave(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Player_on_RootPlace")) {
					Core.getMessenger().setInform_Player_on_RootPlace(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Player_on_Sapling_must_Set_Next_To_Log")) {
					Core.getMessenger().setInform_Player_on_Sapling_must_Set_Next_To_Log(ycf.getString(name));
				}
				if (name.equalsIgnoreCase("Inform_Player_on_Log_must_Set_Next_To_Log")) {
					Core.getMessenger().setInform_Player_on_Log_must_Set_Next_To_Log(ycf.getString(name));
				}

			} catch (Exception ex) {
				ycf.set(name, "INVAILD");
			}
		}
	}

	public static YamlConfiguration loadBimTypeList() {
		File adr = Core.bimtype;
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

	public static void registerNewBIMType(String name) {
		YamlConfiguration list = loadBimTypeList();
		
		System.out.println("ConfigLoader Test: "+name);

		list.set(name, "");

		saveList(list, Core.bimtype);
	}

	public static void unregisterNewBIMType(String name) {
		YamlConfiguration list = loadBimTypeList();

		list.set(name, null);

		saveList(list, Core.bimtype);
	}

	public static boolean isBIMName(String name) {
		return loadBimTypeList().contains(name);
	}

	public static List<String> getBIMList() {
		return new ArrayList(loadBimTypeList().getKeys(false));
	}

	public static YamlConfiguration loadHeadList() {
		File adr = Core.headsfile;
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

	private static String displayNameTag = "displayName";
	private static String loreTag = "lore";
	private static String urlTag = "url";

	/**
	 * Adding Skull BIM to exsisting BIM File
	 */
	public static void registerNewBIMHead(String name, String url, String displayName, List<String> lores) {
		YamlConfiguration list = loadHeadList();

		list.set(name + "." + urlTag, url);

		list.set(name + "." + displayNameTag, displayName);
		list.set(name + "." + loreTag, lores);

		saveList(list, Core.headsfile);
	}

	public static void unregisterNewBIMHead(String name) {
		YamlConfiguration list = loadHeadList();

		list.set(name, null);

		saveList(list, Core.headsfile);
	}

	public static void saveList(YamlConfiguration list, File addr) {
		try {
			list.options().copyDefaults(true);
			list.save(addr);
		} catch (IOException e) {

		}
	}

	public static String getURL(String name) {
		YamlConfiguration list = loadHeadList();
		String url = list.getString(name + "." + urlTag);
		return url;
	}

	public static ItemStack getNewBIMItem(String name) {
		String id = Core.getInstance().getBIMStore().createID();
		return getBIMItem(name, id);
	}

	public static ItemStack getExsistingBIMItem(String id) {
		String headName = Core.getInstance().getBIMStore().getBIMHeadName(id);
		return getBIMItem(headName, id);
	}

	private static ItemStack getBIMItem(String name, String id) {
		YamlConfiguration list = loadHeadList();
		ItemStack item = new ItemStack(Material.COBBLESTONE);

		String url = getURL(name);
		item = Heads.getHead(url, id);
		String display = list.getString(name + "." + displayNameTag);
		List<String> lores = new ArrayList<String>();
		lores.add(id);
		lores.addAll(list.getStringList(name + "." + loreTag));
		item = setItemProperties(item, display, lores);
		return item;
	}

	public static ItemStack setItemProperties(ItemStack item, String display, List<String> lores) {
		item = setDisplayName(item, display);
		item = setLores(item, lores);
		return item;
	}

	public static ItemStack setDisplayName(ItemStack item, String displayName) {
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(displayName);
		item.setItemMeta(im);
		return item;
	}

	public static ItemStack setLores(ItemStack item, List<String> lores) {
		ItemMeta im = item.getItemMeta();
		im.setLore(lores);
		item.setItemMeta(im);
		return item;
	}

	public static List<String> getLores(ItemStack item) {
		if (item == null)
			return null;
		ItemMeta im = item.getItemMeta();
		if (im == null)
			return null;
		return im.getLore();
	}

}
