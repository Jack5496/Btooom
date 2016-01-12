package com.jack.btooom.ce;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.Core;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.data.BIMStore;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.filemanager.PlayersData;
import com.jack.btooom.languages.Languages;

public class BIMChoose {
	public static boolean execute(CommandSender sender, String name) {

		Player p = (Player) sender;

		try {
			if (name != null) {
				if (ConfigLoader.isBIMName(name)) {
					if (PlayersData.getBeamType(p).equalsIgnoreCase("None")) {

						if (ConfigLoader.isBIMName(name)) {

							for (int i = 0; i < 8; i++) {
								Core.getInstance().getBIMStore().givePlayerNewBIM(p, name);
							}
							PlayersData.savePlayerBeam(p, name);

						}
						return true;
					} else {
						p.sendMessage(Languages.getYouMustFirstLeaveYourTree(p));
						return false;
					}
				} else {
					if (name.equals("None")) {
						PlayersData.savePlayerBeam(p, "None");
						p.sendMessage("Need to Remove all Beams reset all");
					}
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			p.sendMessage(Languages.getUsage(p) + " /tree import help");
		}
		return true;
	}

}
