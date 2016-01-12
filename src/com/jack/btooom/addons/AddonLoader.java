package com.jack.btooom.addons;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import com.google.common.io.Files;
import com.jack.btooom.Core;
import com.jack.btooom.API.AddonAPI;
import com.jack.btooom.beams.BIM;
import com.jack.btooom.data.ConfigLoader;

public class AddonLoader {

	private static String slash = File.separator;

	/** Plugin Ordner */
	public static File plugindiraddon = new File(Core.plugindir + slash + "Addons");
	/** Data Ordner */

	private static PluginManager manager = Core.getInstance().getServer().getPluginManager();
	
	static List<Class<? extends BIM>> classes = new ArrayList<Class<? extends BIM>>();

	public static List<Plugin> addons = new ArrayList<Plugin>();
	
	public static boolean enableNewAddon(Plugin addon) {
		if (InRightFolder(addon)) {
//			Core.getInstance().logger.info("[" + addon.getName() + "] hooked in");
			registerNewAddon(addon);
			addBIMClasses(getPlugin(addon).getAllBIMs());
			return true;
		} else {
			Core.getInstance().logger.info("[" + addon.getName() + "] Error: Put me in Btooom/Addons !");
			Core.getInstance().getServer().getPluginManager().disablePlugin(addon);
			return false;
		}
	}
	
	public static void registerNewAddon(Plugin addon){
		if(!addons.contains(addon)) addons.add(addon);
	}
	
	public static List<Plugin> getAllRegisteredAddons(){
		return new ArrayList<Plugin>(addons);
	}
	
	public static List<String> getAllRegisteredAddonsNames(){
		List<String> back = new ArrayList<String>();
		for(Plugin addon : getAllRegisteredAddons()){
			back.add(addon.getName());
		}
		return back;
	}
	
	public static Plugin getAddonByName(String name){
		for(Plugin addon : getAllRegisteredAddons()){
			if(addon.getName().equals(name)) return addon;
		}
		return null;
	}
	

	
	private static boolean InRightFolder(Plugin addon) {
		File addonFolder = Core.getInstance().getPlugindirAddons();
		File thisFolder = addon.getDataFolder().getParentFile();
		return thisFolder.compareTo(addonFolder) == 0;
	}
	
	public static boolean hasBIMType(String type) {
		boolean back = false;
		for (Class<? extends BIM> bimClass : classes) {
			try {
				if (bimClass.newInstance().getType().equals(type))
					back = true;
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return back;
	}

	public static BIM getBimClass(String type) {
		for (Class<? extends BIM> bimClass : classes) {
			try {
				if (bimClass.newInstance().getType().equals(type)) {
					return bimClass.newInstance();
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new BIM();
	}
	
	public static void addBIMClasses(List<Class<? extends BIM>> toAdd){
		classes.addAll(toAdd);
		
		for (Class<? extends BIM> bimClass : classes) {
			try {
				bimClass.newInstance().loadBIM(Core.getInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static void removeBIMClasses(List<Class<? extends BIM>> toRem){
		classes.removeAll(toRem);
		
		for (Class<? extends BIM> bimClass : classes) {
			try {
				bimClass.newInstance().unloadBIM(Core.getInstance());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static List<String> enableAllAddons() {
		checkIfPluginDirAddonExist();
		
		List<String> enabledAddons = new ArrayList<String>();

		File[] listOfAddons = plugindiraddon.listFiles();

		for (File addon : listOfAddons) {
			Bukkit.broadcastMessage("Load Addon: "+addon.getName());
			String result = enableAddon(addon);
			if(result!=null) enabledAddons.add(result);
		}
		return enabledAddons;
	}

	public static List<List<String>> reloadAddons() {
		List<String> disabled = disableAllAddons();
		List<String> enabled = enableAllAddons();
		
		List<List<String>> group = new ArrayList<List<String>>(2);
		group.add(disabled);
		group.add(enabled);
		return group;
	}

	public static String enableAddon(File addon) {
		try {
			return enableAddon(manager.loadPlugin(addon));
		} catch (UnknownDependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidPluginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidDescriptionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String enableAddon(Plugin addon) {
		try {
			manager.enablePlugin(addon);
			addBIMClasses(getPlugin(addon).getAllBIMs());
			return addon.getName();
		} catch (UnknownDependencyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static AddonAPI getPlugin(Plugin addon) {
		return (AddonAPI) addon;
	}

	public static List<String> disableAllAddons() {
		checkIfPluginDirAddonExist();
		
		List<String> disabled = new ArrayList<String>();

		List<Plugin> copyEnabledAddons = new ArrayList<Plugin>(addons);
		for (Plugin addon : copyEnabledAddons) {
			String result = disableAddon(addon);
			if(result!=null) disabled.add(result);
		}
		
		return disabled;
	}

	public static String disableAddon(Plugin addon) {
		if (manager.isPluginEnabled(addon)) {
			getPlugin(addon).saveInformationsForDisable();
			removeBIMClasses(getPlugin(addon).getAllBIMs());
			try {
				manager.disablePlugin(addon);
				return addon.getName();
			} catch (UnknownDependencyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		} else {
			return null;
		}
	}

	public static void checkIfPluginDirAddonExist() {
		if (!plugindiraddon.exists()) {
			plugindiraddon.mkdir();
		}
	}

}