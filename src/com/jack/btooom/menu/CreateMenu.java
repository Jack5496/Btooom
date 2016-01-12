package com.jack.btooom.menu;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import com.jack.btooom.API.CreateMenuAPI;
import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.heads.Heads;
import com.jack.btooom.languages.Languages;

public class CreateMenu implements CreateMenuAPI {

	public CreateMenu() {
		instance = this;
		// BeamChooseMenu.initItemStacks();
	}

	private static CreateMenu instance;

	public static CreateMenu getCreateMenuInstance() {
		return instance;
	}

	public IconMenuAPI getMainMenu(Player p) {
		return createMainMenu(p);
	}

	private static HashMap<Integer, Object[]> addons = new HashMap<Integer, Object[]>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jack.treespirit.menu.CreateMenuAPI#SetIconMenu(com.jack.treespirit
	 * .API.IconMenuAPI, int)
	 */
	@Override
	public boolean SetIconMenu(IconMenu im, int i, ItemStack is, String name, String desc) {

		if (i < 9 && i > 4) {
			Bukkit.broadcastMessage("Activate Addon on slot: " + i);
			if (!addons.containsKey(new Integer(i))) {
				Bukkit.broadcastMessage("Slot vaild");
				Object[] addon = { im, is, name, desc };
				if (addon[0] != null) {
					Bukkit.broadcastMessage("Addon[0] not empty");
					if (addon[1] != null) {
						Bukkit.broadcastMessage("Addon[1] not empty");
						if (addon[2] != null) {
							Bukkit.broadcastMessage("Addon[2] not empty");
							if (addon[3] != null) {
								Bukkit.broadcastMessage("Addon[3] not empty");
								Bukkit.broadcastMessage(ChatColor.GOLD + "[TreeSpirit]" + ChatColor.WHITE
										+ " load Addon: " + ChatColor.WHITE + "[" + ChatColor.GREEN + name
										+ ChatColor.WHITE + "]");
								addons.put(new Integer(i), addon);
								return true;
							}
						}
					}
				}

				return false;
			} else {
				return false;
			}
		}
		return false;
	}

	@Override
	public IconMenuAPI createMainMenu(Player p) {
		IconMenuAPI mainMenu = new IconMenu(p, findMiddle(ChatColor.DARK_PURPLE + "    Tree Spirit Menu"), 9,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {

						Player p = event.getPlayer();

						switch (event.getPosition()) {
						case 0:
							createBeamChooseMenu(p).open(p);
							event.setWillClose(false);
							break;
						case 1:
							createCommandMenu(p).open(p);
							event.setWillClose(false);
							break;
						case 2:
							if (event.getName().equalsIgnoreCase("Admin Menu")) {
								if (p.isOp()) {
									createAdminOptionMenu(p).open(p);
								} else {
									p.sendMessage("You are not OP");
								}
							}
							event.setWillClose(false);
							event.setWillDestroy(true);
							break;
						case 3:
							PlayerInformationMenu.createPlayerInformationMenu(p).open(p);
							event.setWillClose(false);
							break;
						case 5:
							if (addons.containsKey(new Integer(5))) {
								((IconMenu) addons.get(5)[0]).open(p);
							}
							event.setWillClose(false);
							break;
						case 6:
							if (addons.containsKey(new Integer(6))) {
								((IconMenu) addons.get(6)[0]).open(p);
							}
							event.setWillClose(false);
							break;
						case 7:
							if (addons.containsKey(new Integer(7))) {
								((IconMenu) addons.get(7)[0]).open(p);
							}
							event.setWillClose(false);
							break;
						case 8:
							if (addons.containsKey(new Integer(8))) {
								((IconMenu) addons.get(8)[0]).open(p);
							}
							event.setWillClose(false);
							break;

						default:
							event.setWillClose(false);
							break;
						}
					}
				}).setOption(0,
						Heads.getHead(
								"http://textures.minecraft.net/texture/bc563b9b582409f41f0e0781a983afe3d8fe2fbf383c53a5d2743155564d68"),
						"BIM Choose", "Select your BIM type")
						.setOption(1, new ItemStack(Material.COMMAND, 1), "Commands", "Get more Information");

		if (p.isOp()) {
			mainMenu.setOption(2, new ItemStack(Material.BEDROCK, 1), "Admin Menu", "Let's make Changes");
		}

		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

		mainMenu.setOption(3, skull, "Player Menu", "Let's see what you are");
		mainMenu.setOption(4, new ItemStack(Material.BOOK), "LeaderBoard", "See who is best");

		for (Integer i : addons.keySet()) {
			try {
				Object[] addon = addons.get(i);
				mainMenu.setOption(i, (ItemStack) addon[1], (String) addon[2], (String) addon[3]);
			} catch (IndexOutOfBoundsException e) {
				break;
			}
		}

		return mainMenu;
	}

	public static IconMenuAPI createLanguageMenu(Player p) {
		return LanguageMenu.createPlayerLanguageMenu(p);
	}

	public static IconMenuAPI createCommandMenu(Player p) {
		return CommandMenu.createCommandMenu(p, Languages.getLanguageForPlayer(p));
	}

	public static IconMenuAPI createBeamChooseMenu(Player p) {
		return BIMChooseMenu.createBIMChooseMenu(p);
	}

	/**
	 * ADMIN OPTIONS
	 */

	public IconMenuAPI createAdminOptionMenu(Player p) {
		return AdminOptionMenu.createAdminOptionMenu(p);
	}

	public IconMenuAPI createAdminOptionGenerelCommands(Player p) {
		return AdminOptionGenerelCommands.createAdminOptionGenerelCommands(p);
	}

	public IconMenuAPI createAdminOptionTreeMenu(Player p) {
		return AdminOptionTreeMenu.createAdminOptionTreeMenu(p);
	}

	static String findMiddle(String s) {
		String back = "";
		int amount = 32 - s.length();
		for (int i = 0; i < amount / 2; i++) {
			back = back + " ";
		}
		back = back + s;
		return back;
	}
}
