package com.jack.btooom.menu;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.jack.btooom.Core;
import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.beams.BIMs;
import com.jack.btooom.ce.BIMChoose;
import com.jack.btooom.data.ConfigLoader;
import com.jack.btooom.filemanager.PlayersData;
import com.jack.btooom.languages.Languages;


public class BIMChooseMenu {
		
	public static IconMenuAPI createBIMChooseMenu(Player p) {
		return createBIMChooseMenu(1,p);
	}

	private static IconMenuAPI createBIMChooseMenu(final int page, Player p) {
		int size = 36;

		IconMenuAPI createBIMChooseMenu = new IconMenu(p,
				CreateMenu.findMiddle(ChatColor.DARK_PURPLE
						+ "    Languages - " + page + " -"), size,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {

						Player p = event.getPlayer();
						File[] listOfFiles = Core.getInstance()
								.getPlugindirDataLanguages().listFiles();

						switch (event.getName()) {
						case "Back":
							PlayerInformationMenu.createPlayerInformationMenu(p).open(p);
							event.setWillClose(false);
							break;
						case "Next":
							createBIMChooseMenu(page+1,p).open(p);
							event.setWillClose(false);
							break;
						case "Previous":
							createBIMChooseMenu(page-1,p).open(p);
							event.setWillClose(false);
							break;
						default:
							Core.getInstance().getBIMStore().givePlayerNewBIM(p, event.getName());
							event.setWillClose(false);
							break;
						}
						for (File f : listOfFiles) {
							String fileName = FilenameUtils.removeExtension(f
									.getName());
							if (event.getName().equalsIgnoreCase(fileName)) {
								PlayersData.savePlayerLanguage(p, fileName);
							}
						}

					}
				}).setOption(size - 1, new ItemStack(Material.REDSTONE_BLOCK),
				"Back", "to Main Menu");

		List<String> beams = BIMs.getBIMList();
		if (beams.size() > 27 * page) {
			createBIMChooseMenu.setOption(size - 4, new ItemStack(
					Material.DIAMOND, page + 1), "Next", "");
		}
		if (page > 1) {
			createBIMChooseMenu.setOption(size - 6, new ItemStack(
					Material.DIAMOND, page - 1), "Previous", "");
		}

		for (int i = (page - 1)*27; i < (page * 27) - 1; i++) {
			if ((i < beams.size())) {
				String bim = beams.get(i);
				ItemStack item = ConfigLoader.getNewBIMItem(bim);
				ItemMeta im = item.getItemMeta();
				List<String> lores = im.getLore();
				String lore = lores != null ? lores.get(1) : "";
				createBIMChooseMenu.setOption(i, item, bim, lore);
			}
		}

		return createBIMChooseMenu;
	}
}
