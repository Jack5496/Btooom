package com.jack.btooom.menu;

import java.util.Arrays;


import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.jack.btooom.Core;
import com.jack.btooom.API.IconMenuAPI;

 
public class IconMenu implements Listener, IconMenuAPI{
 
    private String name;
    private int size;
    private OptionClickEventHandler handler;
    private Player p;
    private IconMenu instance;
    private int hashcode;
    private Inventory inventory;
   
    private String[] optionNames;
    private ItemStack[] optionIcons;
    IconMenu me = this;
    
    public IconMenu(String name, int size, OptionClickEventHandler handler) {
    	this.p = null;
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.instance = this;
        this.hashcode = this.hashCode();
        Core.getInstance().getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }
   
    public IconMenu(Player p,String name, int size, OptionClickEventHandler handler) {
    	this.p = p;
        this.name = name;
        this.size = size;
        this.handler = handler;
        this.optionNames = new String[size];
        this.optionIcons = new ItemStack[size];
        this.instance = this;
        this.hashcode = this.hashCode();
        Core.getInstance().getServer().getPluginManager().registerEvents(this, Core.getInstance());
    }

	/* (non-Javadoc)
	 * @see com.jack.treespirit.menu.IconMenuAPI#setOption(int, org.bukkit.inventory.ItemStack, java.lang.String, java.lang.String)
	 */
    @Override
	public IconMenuAPI setOption(int position, ItemStack icon, String name, String... info) {
        optionNames[position] = name;
        optionIcons[position] = setItemNameAndLore(icon, name, info);
        return this;
    }
   
    /* (non-Javadoc)
	 * @see com.jack.treespirit.menu.IconMenuAPI#open(org.bukkit.entity.Player)
	 */
    @Override
	public void open(Player player) {
        inventory = Bukkit.createInventory(player, size, name);
        for (int i = 0; i < optionIcons.length; i++) {
            if (optionIcons[i] != null) {
                inventory.setItem(i, optionIcons[i]);
            }
        }
        player.openInventory(inventory);
    }
    
    public void updateAllIcons(){
    	for (int i = 0; i < optionIcons.length; i++) {
    		updateIcon(i);
        }
    }
    
    public void updateIcon(int i){
    	 if (optionIcons[i] != null) {
             inventory.setItem(i, optionIcons[i]);
         }
    }
   
    /* (non-Javadoc)
	 * @see com.jack.treespirit.menu.IconMenuAPI#destroy()
	 */
    @Override
	public void destroy() {
        HandlerList.unregisterAll(this);
        handler = null;
        optionNames = null;
        optionIcons = null;
        me = null;
    }
   
    @EventHandler(priority=EventPriority.MONITOR)
    void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals(name)) {
            event.setCancelled(true);
            int slot = event.getRawSlot();
            if (slot >= 0 && slot < size && optionNames[slot] != null) {
                OptionClickEvent e = new OptionClickEvent((Player)event.getWhoClicked(), slot, optionNames[slot]);
                handler.onOptionClick(e);
                if (e.willClose()) {
                    final Player p = (Player)event.getWhoClicked();
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Core.getInstance(), new Runnable() {
                        public void run() {
                            p.closeInventory();
                        }
                    }, 1);
                }
                if (e.willDestroy()) {
                    destroy();
                }
            }
        }
    }
    
    @EventHandler(priority=EventPriority.MONITOR)
    public void onCloseMenu(InventoryCloseEvent e){
    	if (e.getInventory().getTitle().equals(name)) {
    		if(p==null){
    			return;
    		}
    		if(e.getPlayer().equals(p)){
		    	destroy();
    		}
    	}
    }
    
    
    public interface OptionClickEventHandler {
        public void onOptionClick(OptionClickEvent event);       
    }
    
    public class OptionClickEvent {
        private Player player;
        private int position;
        private String name;
        private boolean close;
        private boolean destroy;
       
        public OptionClickEvent(Player player, int position, String name) {
            this.player = player;
            this.position = position;
            this.name = name;
            this.close = true;
            this.destroy = false;
        }
       
        public Player getPlayer() {
            return player;
        }
       
        public int getPosition() {
            return position;
        }
       
        public String getName() {
            return name;
        }
        
        public IconMenu getInstance(){
        	return instance;
        }
       
        public boolean willClose() {
            return close;
        }
       
        public boolean willDestroy() {
            return destroy;
        }
       
        public void setWillClose(boolean close) {
            this.close = close;
        }
       
        public void setWillDestroy(boolean destroy) {
            this.destroy = destroy;
        }
    }
   
    private ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore) {
    	ItemMeta im = item.getItemMeta();
            im.setDisplayName(name);
            im.setLore(Arrays.asList(lore));
        item.setItemMeta(im);
        return item;
    }
   
}