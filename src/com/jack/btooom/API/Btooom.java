package com.jack.btooom.API;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.jack.btooom.beams.BIM;
import com.jack.btooom.data.Configurations;

import de.slikey.effectlib.EffectManager;

public interface Btooom {

	void loadFiles() throws IOException;
	Configurations getConfigurations();
	FileManagerAPI getFileManager();
	CreateMenuAPI getCreateMenu();
	File getPluginDir();
	File getPluginDirData();
	File getPlugindirDataPlayers();
	File getPlugindirDataLanguages();
	BIMStoreAPI getBIMStore();
	ItemStack getExsistingBIMItem(String id);
	void destroyBlockWithBIM(Location l);
	boolean enableNewAddon(Plugin addon);
	void addonRegisterNewBIMType(String type);
	void addonUnRegisterNewBIMType(String type);
	void addonRegisterNewBIMHead(String type, String displayName, String url, List<String> lores);
	void addonUnRegisterNewBIMHead(String type);
	File getPlugindirAddons();

}
