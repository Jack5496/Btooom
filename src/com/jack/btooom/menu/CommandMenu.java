package com.jack.btooom.menu;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.languages.Languages;


public class CommandMenu {

	static String help = "/tree help";	
	static String leave = "/tree leave";
	static String invite = "/tree invite <Player>";
	static String size = "/tree size";
	static String homepage = "/tree homepage";
	static String import_c = "/tree import";
	static String filesize = "/tree filesize";
	static String back = "Back";
	static String join = "/tree join <Tree Owner>";
	
	
	public static IconMenuAPI createCommandMenu(Player p,File adr) {		
		final boolean[] kill = {false};
		
		IconMenuAPI commandMenu = new IconMenu(p,CreateMenu.findMiddle(ChatColor.DARK_PURPLE
				+ "    Command Menu"), 18,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {
						

						switch (event.getName()) {
						case "Back":
							Bukkit.broadcastMessage("Am i single?");
							CreateMenu.getCreateMenuInstance().getMainMenu(event.getPlayer()).open(event.getPlayer());
							kill[0] = true;
							break;
						default:
							event.setWillClose(false);
							break;
						}
					}
				})
				.setOption(0, new ItemStack(Material.WOOL, 1), help,
						Languages.getCommandDescriptionMessage(adr, help))
				.setOption(1, new ItemStack(Material.WOOL, 1), leave,
						Languages.getCommandDescriptionMessage(adr, leave))
				.setOption(2, new ItemStack(Material.WOOL, 1),
						invite,
						Languages.getCommandDescriptionMessage(adr, invite))
				.setOption(3, new ItemStack(Material.WOOL, 1),
						join, Languages.getCommandDescriptionMessage(adr, join))
				.setOption(4, new ItemStack(Material.WOOL, 1), size,
						Languages.getCommandDescriptionMessage(adr, size))
				.setOption(5, new ItemStack(Material.WOOL, 1),
						homepage, Languages.getCommandDescriptionMessage(adr, homepage))
				.setOption(6, new ItemStack(Material.WOOL, 1), import_c,
						Languages.getCommandDescriptionMessage(adr, import_c))
				.setOption(7, new ItemStack(Material.WOOL, 1),
						filesize,
						Languages.getCommandDescriptionMessage(adr, filesize))
				.setOption(17, new ItemStack(Material.REDSTONE), back,
						Languages.getCommandDescriptionMessage(adr, back)); // <--
		
		return commandMenu;
	}
}
