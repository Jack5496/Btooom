package com.jack.btooom.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.API.IconMenuAPI;


public class AdminOptionGenerelCommands {

	public static IconMenuAPI createAdminOptionGenerelCommands(Player p) {
		IconMenuAPI adminOptionGenerelCommands = new IconMenu(p,CreateMenu.findMiddle(ChatColor.DARK_PURPLE
				+ "    Generel Commands"), 9, new IconMenu.OptionClickEventHandler() {
			@Override
			public void onOptionClick(IconMenu.OptionClickEvent event) {

				Player p = event.getPlayer();
				
				switch (event.getName()) {
				case "Back":
					CreateMenu.getCreateMenuInstance().createAdminOptionMenu(p).open(p);
					event.setWillClose(false);
					break;
				case "Day & Sun":
					p.getWorld().setTime(6000);
					p.getWorld().setThunderDuration(0);
					p.getWorld().setStorm(false);
					event.setWillClose(false);
					break;
				default:
					event.setWillClose(false);
					break;
				}
			}
		}).setOption(8,
				new ItemStack(Material.REDSTONE), "Back", "to Main Menu")
				.setOption(1, new ItemStack(Material.WATCH), "Day & Sun",
						"Makes this Day wonderfull"); // <-- ";" ACHTUNG
		return adminOptionGenerelCommands;
	}
}
