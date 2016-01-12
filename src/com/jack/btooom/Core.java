package com.jack.btooom;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import com.jack.btooom.API.BIMStoreAPI;
import com.jack.btooom.API.Btooom;
import com.jack.btooom.API.CreateMenuAPI;
import com.jack.btooom.API.FileManagerAPI;
import com.jack.btooom.addons.AddonLoader;
import com.jack.btooom.beams.BIM;
import com.jack.btooom.ce.BtooomComands;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.data.Configurations;
import com.jack.btooom.data.Messages;
import com.jack.btooom.data.SignConfigurations;
import com.jack.btooom.events.Events;
import com.jack.btooom.filemanager.FileManager;
import com.jack.btooom.heads.Heads;
import com.jack.btooom.languages.Languages;
import com.jack.btooom.menu.CreateMenu;
import com.jack.btooom.threads.BIMTimeController;
import com.jack.btooom.util.LocationDestroy;
import com.mojang.authlib.GameProfile;

import de.slikey.effectlib.EffectLib;
import de.slikey.effectlib.EffectManager;

public class Core extends JavaPlugin implements Btooom {

	private static Core instance;

	private static String slash = File.separator;

	/** Plugin Ordner */
	public static File plugindir = new File("plugins" + slash + "Btooom");
	/** Data Ordner */
	public static File plugindirdata = new File(plugindir + slash + "Data");
	public static File plugindirdataplayers = new File(plugindirdata + slash + "Players");
	public static File plugindirdatalanguages = new File(plugindirdata + slash + "Languages");

	/**
	 * Speicherorte der Ordner
	 */
	public static File configfile = new File(plugindir + slash + "Config.yml");
	public static File messagefile = new File(plugindir + slash + "Messages.yml");
	public static File signsfile = new File(plugindir + slash + "Signs.yml");
	public static File bimtype = new File(plugindir + slash + "BIMTypes.yml");
	public static File bimfile = new File(plugindir + slash + "BIMs.yml");
	public static File headsfile = new File(plugindir + slash + "Heads.yml");

	public static SignConfigurations signconfigs = new SignConfigurations(instance);

	/** Ende Data Ordner */

	/**
	 * Command Excecuter
	 */
	private BtooomComands btooom_cmd = new BtooomComands(this);

	/**
	 * Speichert allerleilei SpielMechanische Configuration
	 */
	public static Configurations configs = new Configurations(instance);
	/**
	 * Speichert alle Message Configurationen
	 */
	private Messages messages = new Messages(this);

	/**
	 * Controller für die Threads, sodass man diese mein disablen stoppen kann
	 */

	public CreateMenuAPI cm;
	private static FileManagerAPI fm;

	/**
	 * Threads
	 */
	Thread bimTimeControllerThread;
	BIMTimeController bimTimeController;

	public final Logger logger = Logger.getLogger("Minecraft");

	static BIMStoreAPI bimStore;
	
	public final String pluginPrefix = "["+getName()+"] ";

	@Override
	public void onEnable() {

		bimStore = new BIMStore();

		instance = this; // Speichert eigene Instanz
		fm = new FileManager(); // Muss vor loadFiles();

		try {
			loadFiles(); // Läd alle Dateien, HashMap,PlayerMap,RootMap,...
		} catch (IOException e) {
			logger.info(pluginPrefix+"Error why loading Files");
			e.printStackTrace();
		}

		cm = new CreateMenu(); // Erstellt Menu ! Muss nach loadFiles();

		this.getServer().getPluginManager().registerEvents(new Events(), this); // Aktiviert
		// das
		// Plugin

		this.getCommand("btooom").setExecutor(btooom_cmd); // Activiert den
															// /tree
															// Command

		startAndInitThreads();

		AddonLoader.enableAllAddons();

		logger.info(pluginPrefix+"enabled!");

		// try {
		// org.mcstats.Metrics metrics = new org.mcstats.Metrics(this);
		// metrics.start();
		// System.out.println("Stats Submitted");
		// } catch (IOException e) { // Failed to submit the stats :-(
		// System.out.println("Error Submitting stats!");
		// }
	}

	private void startAndInitThreads() {
		bimTimeController = new BIMTimeController();
		bimTimeControllerThread = new Thread(bimTimeController);
		bimTimeControllerThread.start();
	}

	private void stopThreads() {
		bimTimeController.stopMe();
		bimTimeControllerThread.stop();
	}

	public static void checkIfPluginDirsExist() {
		if (!plugindir.exists()) {
			plugindir.mkdir();
		}

		// Estellt den Data Ordner
		if (!plugindirdata.exists()) {
			plugindirdata.mkdir();
		}

		// Estellt den Players Ordner in Data
		if (!plugindirdataplayers.exists()) {
			plugindirdataplayers.mkdir();
		}

		// Estellt den Language Ordner in Data
		if (!plugindirdatalanguages.exists()) {
			plugindirdatalanguages.mkdir();
			Languages.initLanguage();
		} else {
			Languages.initLanguage();
		}
	}

	@Override
	public void loadFiles() throws IOException {
		// Estellt den Directory Ordner
		checkIfPluginDirsExist();

		// Läd alle Spielmechanischen Configs
		ConfigLoader.loadAllConfigs();
	}

	@Override
	public void onDisable() {
		// Speicher alle HashMaps vor beenden nochmal

		stopThreads();

		getFileManager().saveAllData();
		saveConfig();

		for (BukkitTask task : getServer().getScheduler().getPendingTasks()) {
			int tid = task.getTaskId();
			getServer().getScheduler().cancelTask(tid);
		}

		AddonLoader.disableAllAddons();

		logger.info("[Btooom] disabled!");
	}

	/**
	 * Gibt den Singelton Core wieder
	 * 
	 * @return Core des Plugins
	 */
	public static Core getInstance() {
		return instance;
	}

	public void destroyBlockWithBIM(Location l) {
		LocationDestroy.destroyBlockWithBIM(l);
	}

	public ItemStack getExsistingBIMItem(String id) {
		return ConfigLoader.getExsistingBIMItem(id);
	}

	@Override
	public boolean enableNewAddon(Plugin addon) {
		return AddonLoader.enableNewAddon(addon);
	}

	@Override
	public void addonRegisterNewBIMType(String type) {
		ConfigLoader.registerNewBIMType(type);
	}
	
	@Override
	public void addonUnRegisterNewBIMType(String type) {
		ConfigLoader.unregisterNewBIMType(type);
	}

	@Override
	public void addonRegisterNewBIMHead(String type, String displayName, String url, List<String> lores) {
		ConfigLoader.registerNewBIMHead(type, url, displayName, lores);
	}

	@Override	
	public void addonUnRegisterNewBIMHead(String type) {
		ConfigLoader.unregisterNewBIMHead(type);
	}

	@Override
	public Configurations getConfigurations() {
		return instance.configs;
	}

	public static Messages getMessenger() {
		return instance.messages;
	}

	@Override
	public BIMStoreAPI getBIMStore() {
		return bimStore;
	}

	@Override
	public FileManagerAPI getFileManager() {
		return this.fm;
	}

	@Override
	public CreateMenuAPI getCreateMenu() {
		return this.cm;
	}

	@Override
	public File getPluginDir() {
		return this.plugindir;
	}

	@Override
	public File getPluginDirData() {
		return this.plugindirdata;
	}

	@Override
	public File getPlugindirDataPlayers() {
		return this.plugindirdataplayers;
	}

	@Override
	public File getPlugindirAddons() {
		return AddonLoader.plugindiraddon;
	}

	@Override
	public File getPlugindirDataLanguages() {
		return this.plugindirdatalanguages;
	}



	

}