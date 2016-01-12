package com.jack.btooom.ce;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.jack.btooom.Core;
import com.jack.btooom.addons.AddonLoader;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.filemanager.PlayersData;
import com.jack.btooom.languages.Languages;
import com.jack.btooom.menu.CreateMenu;

public class BtooomComands implements CommandExecutor {

	public Core core;
	public static Location start;
	public static Location end;

	public static Location nt_begin;

	public BtooomComands(Core c) {
		this.core = c;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

		try {
			if (args[0] != null) {

				if (args[0].equalsIgnoreCase("help")) {
					sendHelpMessage(sender, cmd, s, args);
					return true;
				}
				if (args[0].equalsIgnoreCase("find")) {
					findGetLocationFromID(sender, cmd, s, args);
					return true;
				}
				if (args[0].equalsIgnoreCase("reload")) {
					sendReloadedMessage(sender, AddonLoader.reloadAddons());
					return true;
				}
				if (args[0].equalsIgnoreCase("unload")) {
					sendLoadMessage(sender, false, args);
					return true;
				}
				if (args[0].equalsIgnoreCase("load")) {
					sendLoadMessage(sender, true, args);
					return true;
				}

				if (sender instanceof Player) {
					Player p = (Player) sender;
					sender.sendMessage(Languages.getUsage(p) + " /btooom help");
				}
				return true;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (Core.getInstance().getConfigurations().getTree_menu()) {
					if (PlayersData.getPlayerLanguage(p) == null) {

					}
					if (!PlayersData.getPlayerLanguage(p).equalsIgnoreCase("Default")) {
						CreateMenu.getCreateMenuInstance().getMainMenu(p).open(p);
					} else {
						CreateMenu.getCreateMenuInstance().createLanguageMenu(p).open(p);
					}
				} else {
					p.sendMessage(Languages.getUsage(p) + " /btooom help");
				}
				return true;
			}
		}
		return false;
	}

	public void sendReloadedMessage(CommandSender sender, List<List<String>> list) {
		List<String> disabled = list.get(0);
		List<String> enabled = list.get(1);

		String message = Core.getInstance().pluginPrefix + "Disabled: " + disabled.size() + " and Enabled: "
				+ enabled.size();
		sendMessage(sender, message);
	}

	public void sendLoadMessage(CommandSender sender, boolean enable, String[] args) {
		String addonName = args[1];
		String result = null;
		if(AddonLoader.getAllRegisteredAddonsNames().contains(addonName)){
			if(enable){
				result = AddonLoader.enableAddon(AddonLoader.getAddonByName(addonName));
			}
			else{
				result = AddonLoader.disableAddon(AddonLoader.getAddonByName(addonName));				
			}
		}
		if(result==null) sendMessage(sender, Core.getInstance().pluginPrefix+"PluginName not found");
		if(enable){
			sendMessage(sender, Core.getInstance().pluginPrefix+addonName+" enabled");
		}
		else{
			sendMessage(sender, Core.getInstance().pluginPrefix+addonName+" disabled");
		}
	}

	public void sendMessage(CommandSender sender, String message) {
		if (sender instanceof Player) {
			Player p = (Player) sender;
			p.sendMessage(message);
			return;
		}
		if (sender instanceof ConsoleCommandSender) {
			ConsoleCommandSender console = (ConsoleCommandSender) sender;
			console.sendMessage(message);
		}
	}

	public boolean findGetLocationFromID(CommandSender sender, Command cmd, String s, String[] args) {
		if (args[1] != null) {
			String id = args[1];
			Location loc = Core.getInstance().getBIMStore().getBIMLocation(id);
			if (loc == null) {
				sender.sendMessage("BTOOOMCOMMANDS:Sure BIM ID?");
			} else {
				sender.sendMessage("BTOOOMCOMMANDS:BIM is at: " + loc.getBlockX() + " | " + loc.getBlockY() + " | "
						+ loc.getBlockZ());
			}
		} else {
			sender.sendMessage("BTOOOMCOMMANDS:Pls ID");
		}
		return true;

	}

	public boolean sendHelpMessage(CommandSender sender, Command cmd, String s, String[] args) {
		String blank = "" + ChatColor.GOLD + "  ||  " + ChatColor.WHITE + "";

		sender.sendMessage(ChatColor.GOLD + Core.getInstance().pluginPrefix + ChatColor.RED + "- Commands ");
		sender.sendMessage(ChatColor.GREEN + " - /btooom help" + blank + "Shows Help Menü");

		return true;
	}

}
