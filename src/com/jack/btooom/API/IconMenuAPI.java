package com.jack.btooom.API;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IconMenuAPI {

	/**
	 * Adjust an IconMenu Options
	 * @param position int Position where Item should be shown
	 * @param icon Itemstack which Item should stand there 
	 * @param name Display Name
	 * @param info Description Name
	 * @return
	 */
	public abstract IconMenuAPI setOption(int position, ItemStack icon,
			String name, String... info);

	/**
	 * Opens a Menu for a Player
	 * @param player Player which will see Menu
	 */
	public abstract void open(Player player);

	/**
	 * Destroys the Menu, Items can be took out
	 */
	public abstract void destroy();

}