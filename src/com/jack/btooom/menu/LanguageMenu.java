package com.jack.btooom.menu;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.Core;
import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.filemanager.PlayersData;
import com.jack.btooom.languages.Languages;

public class LanguageMenu {
	
	public static IconMenuAPI createPlayerLanguageMenu(Player p) {
//		File[] listOfFiles = Core.getInstance().getPlugindirDataLanguages()
//				.listFiles();
//		Bukkit.broadcastMessage(ChatColor.GOLD+"[TreeSpirit]"+ChatColor.WHITE+" Languages : "+ChatColor.GOLD+listOfFiles.length);
		return createPlayerLanguageMenuPage(1,p);
	}

	private static IconMenuAPI createPlayerLanguageMenuPage(final int page, Player p) {

		int size = 36;

		IconMenuAPI playerlanguagemenu = new IconMenu(p,
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
							createPlayerLanguageMenuPage(page+1,p).open(p);
							event.setWillClose(false);
							break;
						case "Previous":
							createPlayerLanguageMenuPage(page-1,p).open(p);
							event.setWillClose(false);
							break;
						default:
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

		File[] listOfFiles = Core.getInstance().getPlugindirDataLanguages()
				.listFiles();
		if (listOfFiles.length > 27 * page) {
			playerlanguagemenu.setOption(size - 4, new ItemStack(
					Material.DIAMOND, page + 1), "Next", "");
		}
		if (page > 1) {
			playerlanguagemenu.setOption(size - 6, new ItemStack(
					Material.DIAMOND, page - 1), "Previous", "");
		}

		for (int j = 0, i = (page - 1)*27; i < (page * 27) - 1; i++) {
			if ((i < listOfFiles.length)) {
				File f = listOfFiles[i];
				if (f != null) {
					String fileName = FilenameUtils
							.removeExtension(f.getName());
					if (!fileName.equalsIgnoreCase("null")) {
						playerlanguagemenu.setOption(j, new ItemStack(
								Material.BOOK), fileName, Languages
								.getSwitchLanguageMessage(f));
						j++;
					}
				}
			}
		}

		return playerlanguagemenu;
	}
}
