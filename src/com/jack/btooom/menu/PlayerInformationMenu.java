package com.jack.btooom.menu;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.filemanager.PlayersData;

public class PlayerInformationMenu {
	public static IconMenuAPI createPlayerInformationMenu(final Player p) {
		IconMenuAPI playerinformationmenu = new IconMenu(p,
				CreateMenu.findMiddle(ChatColor.DARK_PURPLE + "    " + p.getName()), 9,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {

						Player clicker = event.getPlayer();

						switch (event.getName()) {
						case "Back":
							CreateMenu.getCreateMenuInstance().getMainMenu(clicker).open(clicker);
							event.setWillClose(false);
							break;
						case "Beam Type":
							String beamType = PlayersData.getBeamType(p);
							if (beamType.equalsIgnoreCase("None")) {
								if (clicker.equals(p)) {
									CreateMenu.getCreateMenuInstance().createBeamChooseMenu(clicker).open(clicker);
								} else {
									Bukkit.broadcastMessage("You cant edit others tree Tpye");
								}
							} else {
								Bukkit.broadcastMessage("This will be updated for more Information");
							}
							event.setWillClose(false);
							break;

						case "Languages":
							CreateMenu.getCreateMenuInstance().createLanguageMenu(clicker).open(clicker);
							event.setWillClose(false);
							break;
						default:
							event.setWillClose(false);
							break;
						}
					}
				}).setOption(8, new ItemStack(Material.REDSTONE_BLOCK), "Back", "to Main Menu").setOption(6,
						new ItemStack(Material.ENCHANTED_BOOK), "Languages", PlayersData.getPlayerLanguage(p));

		playerinformationmenu.setOption(1, new ItemStack(Material.SAPLING), "Beam Type", PlayersData.getBeamType(p));

		int amount_of_player = 1;
		
		playerinformationmenu.setOption(4, new ItemStack(Material.LOG), "Placed Blocks", "" + amount_of_player);

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

		
		playerinformationmenu.setOption(5, new ItemStack(Material.WATCH), "First Join", PlayersData.getFirstJoin(p));
		return playerinformationmenu;
	}

	
}
