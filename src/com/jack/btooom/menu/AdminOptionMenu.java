package com.jack.btooom.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.API.IconMenuAPI;


public class AdminOptionMenu {
	public static IconMenuAPI createAdminOptionMenu(Player p) {
		IconMenuAPI adminOptionMenu = new IconMenu(p,CreateMenu.findMiddle(ChatColor.DARK_PURPLE
				+ "    Admin Menu"), 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {

				switch (event.getName()) {
				case "Back":
					CreateMenu.getCreateMenuInstance().getMainMenu(event.getPlayer()).open(event.getPlayer());
					event.setWillClose(false);
					break;
				case "Tree Options":
					CreateMenu.getCreateMenuInstance().createAdminOptionTreeMenu(event.getPlayer()).open(event.getPlayer());
					event.setWillClose(false);
					break;
				case "Generel Commands":
					CreateMenu.getCreateMenuInstance().createAdminOptionGenerelCommands(event.getPlayer()).open(event.getPlayer());
					event.setWillClose(false);
					break;
				default:
					event.setWillClose(false);
					break;
				}
			}
		}).setOption(8,
				new ItemStack(Material.REDSTONE), "Back", "to Main Menu")
				.setOption(1, new ItemStack(Material.SAPLING), "Tree Options",
						"Configure Common Options")
				.setOption(3, new ItemStack(Material.SIGN), "Scoreboard Options",
						"Configure Scoreboard Options")
				.setOption(5, new ItemStack(Material.WATCH), "Generel Commands",
						"Time, Weather,..."); // <-- ";" ACHTUNG
		return adminOptionMenu;
	}
}
