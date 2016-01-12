package com.jack.btooom.menu;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.jack.btooom.Core;
import com.jack.btooom.API.IconMenuAPI;
import com.jack.btooom.data.ConfigLoader;


public class AdminOptionTreeMenu {
	
	public static IconMenuAPI createAdminOptionTreeMenu(Player p) {
		final ItemStack greenwool = new ItemStack(Material.WOOL, 1,
				DyeColor.LIME.getData());

		final ItemStack redwool = new ItemStack(Material.WOOL, 1,
				DyeColor.RED.getData());

		IconMenuAPI adminOptionTreeMenu = new IconMenu(p,CreateMenu.findMiddle(ChatColor.DARK_PURPLE
				+ "    Admin Tree Menu"), 18,
				new IconMenu.OptionClickEventHandler() {
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {
						boolean on;
						
						switch (event.getName()) {
						case "Back":
							CreateMenu.getCreateMenuInstance().createAdminOptionMenu(event.getPlayer()).open(event.getPlayer());
							event.setWillClose(false);
							break;
						case "Debug Mode":
							on = Core.getInstance()
									.getConfigurations().getDebug_Mode();
							Core.getInstance().getConfigurations()
									.setDebug_Mode("" + !on);

							if (!on) {
								event.getInstance().setOption(event.getPosition(), new ItemStack(
										greenwool), "Debug Mode",
										"Show lot of useless Information");
							} else {
								event.getInstance().setOption(event.getPosition(), new ItemStack(
										redwool), "Debug Mode",
										"Hide lot of useless Information");
							}
							ConfigLoader.setConfigsInYML("Debug_Mode", ""
									+ !on);
							
							 
							event.setWillClose(false);
							
							
							break;
						case "Create Sign On Knot":
							on = Core.getInstance()
									.getConfigurations().getCreate_Sign_On_Knot();
							Core.getInstance().getConfigurations()
									.setCreate_Sign_On_Knot("" + !on);

							if (!on) {
								event.getInstance().setOption(1, new ItemStack(
										greenwool), "Create Sign On Knot",
										"Creates Automaticly a Sign on a new Knot");
							} else {
								event.getInstance().setOption(1, new ItemStack(
										redwool), "Create Sign On Knot",
										"Don't create a Sign on Knot");
							}
							ConfigLoader.setConfigsInYML("Create_Sign_On_Knot", ""
									+ !on);
							 
							event.setWillClose(false);
							break;
						case "Tree Pvp":
							on = Core.getInstance()
									.getConfigurations().getTree_Pvp();
							Core.getInstance().getConfigurations()
									.setTree_Pvp("" + !on);

							if (!on) {
								event.getInstance().setOption(2, new ItemStack(
										greenwool), "Tree Pvp",
										"Players can't hurt Players in same Tree");
							} else {
								event.getInstance().setOption(2, new ItemStack(
										redwool), "Tree Pvp",
										"Players can hurt Players in same Tree");
							}
							ConfigLoader.setConfigsInYML("Tree_Pvp", ""
									+ !on);
							 
							event.setWillClose(false);
							break;
						case "Block Explosion Damage":
							on = Core.getInstance()
									.getConfigurations().getDamage_All_Blocks_on_Explosion();
							Core.getInstance().getConfigurations()
									.setDamage_All_Blocks_on_Explosion("" + !on);

							if (!on) {
								event.getInstance().setOption(3, new ItemStack(
										greenwool), "Block Explosion Damage",
										"Explosion Block Damage enabled");								
							} else {
								event.getInstance().setOption(3, new ItemStack(
										redwool), "Block Explosion Damage",
										"Explosion Block Damage disbled");
								
								event.getInstance().setOption(4, new ItemStack(redwool),
										"Tree Explosion Damage", "In case of an Explosion, Tree Block's won't get Damage");
								ConfigLoader.setConfigsInYML("Only_Damage_Tree_on_Explosion", "false");
							}
							ConfigLoader.setConfigsInYML("Damage_All_Blocks_on_Explosion", ""
									+ !on);
							 
							event.setWillClose(false);
							break;
							
						case "Tree Explosion Damage":
							on = Core.getInstance()
									.getConfigurations().getOnly_Damage_Tree_on_Explosion();
							Core.getInstance().getConfigurations()
									.setOnly_Damage_Tree_on_Explosion("" + !on);

							if (!on) {
								event.getInstance().setOption(4, new ItemStack(
										greenwool), "Tree Explosion Damage",
										"In case of an Explosion, Tree Block's get Damage");								
							} else {
								event.getInstance().setOption(4, new ItemStack(
										redwool), "Tree Explosion Damage",
										"In case of an Explosion, Tree Block's don't get Damage");
							}
							ConfigLoader.setConfigsInYML("Only_Damage_Tree_on_Explosion", ""
									+ !on);
							 
							event.setWillClose(false);
							break;	
							
						case "Sapling must set next to Log":
							on = Core.getInstance()
									.getConfigurations().getSapling_must_Set_Next_To_Log();
							Core.getInstance().getConfigurations()
									.setSapling_must_Set_Next_To_Log("" + !on);

							if (!on) {
								event.getInstance().setOption(5, new ItemStack(
										greenwool), "Sapling must set next to Log",
										"Saplings must be set next to one of your Tree Block's");								
							} else {
								event.getInstance().setOption(5, new ItemStack(
										redwool), "Sapling must set next to Log",
										"Sapling can be set anywhere");
							}
							ConfigLoader.setConfigsInYML("Sapling_must_Set_Next_To_Log", ""
									+ !on);
							 
							event.setWillClose(false);
							break;	
							
						case "Decay Tree":
							on = Core.getInstance()
									.getConfigurations().getDecay_tree();
							Core.getInstance().getConfigurations()
									.setDecay_tree("" + !on);

							if (!on) {
								event.getInstance().setOption(6, new ItemStack(
										greenwool), "Decay Tree",
										"A Tree will die slowly");								
							} else {
								event.getInstance().setOption(6, new ItemStack(
										redwool), "Decay Tree",
										"A Tree will die instantly");
							}
							ConfigLoader.setConfigsInYML("Decay_tree", ""
									+ !on);
							 
							event.setWillClose(false);
							break;	
						
						default:
							event.setWillClose(false);
							break;
						}
						
						event.getInstance().updateIcon(event.getPosition());
					}
				}).setOption(17, new ItemStack(
				Material.REDSTONE), "Back", "to Admin Menu"); // <--
		// ";" ACHTUNG
		
		
		

		if (Core.getInstance().getConfigurations().getDebug_Mode()) {
			adminOptionTreeMenu.setOption(0, new ItemStack(greenwool),
					"Debug Mode", "Show's lot of useless Information");
		} else {
			adminOptionTreeMenu.setOption(0, new ItemStack(redwool),
					"Debug Mode", "Hide lot of useless Information");
		}
		
		if (Core.getInstance().getConfigurations().getCreate_Sign_On_Knot()) {
			adminOptionTreeMenu.setOption(1, new ItemStack(greenwool),
					"Create Sign On Knot", "Creates Automaticly a Sign on a new Knot");
		} else {
			adminOptionTreeMenu.setOption(1, new ItemStack(redwool),
					"Create Sign On Knot", "Don't create a Sign on Knot");
		}
		
		if (Core.getInstance().getConfigurations().getTree_Pvp()) {
			adminOptionTreeMenu.setOption(2, new ItemStack(greenwool),
					"Tree Pvp", "Players can't hurt Players in same Tree");
		} else {
			adminOptionTreeMenu.setOption(2, new ItemStack(redwool),
					"Tree Pvp", "Players can hurt Players in same Tree");
		}
		
		if (Core.getInstance().getConfigurations().getDamage_All_Blocks_on_Explosion()) {
			adminOptionTreeMenu.setOption(3, new ItemStack(greenwool),
					"Block Explosion Damage", "Explosion Block Damage enabled");
		} else {
			adminOptionTreeMenu.setOption(3, new ItemStack(redwool),
					"Block Explosion Damage", "Explosion Block Damage disabled");
		}
		
		if (Core.getInstance().getConfigurations().getOnly_Damage_Tree_on_Explosion()) {
			adminOptionTreeMenu.setOption(4, new ItemStack(greenwool),
					"Tree Explosion Damage", "In case of an Explosion, Tree Block's get Damage");
		} else {
			adminOptionTreeMenu.setOption(4, new ItemStack(redwool),
					"Tree Explosion Damage", "In case of an Explosion, Tree Block's don't get Damage");
		}
		
		if (Core.getInstance().getConfigurations().getSapling_must_Set_Next_To_Log()) {
			adminOptionTreeMenu.setOption(5, new ItemStack(greenwool),
					"Sapling must set next to Log", "Saplings must be set next to one of your Tree Block's");
		} else {
			adminOptionTreeMenu.setOption(5, new ItemStack(redwool),
					"Sapling must set next to Log", "Sapling can be set anywhere");
		}
		
		if (Core.getInstance().getConfigurations().getDecay_tree()) {
			adminOptionTreeMenu.setOption(6, new ItemStack(greenwool),
					"Decay Tree", "A Tree will die slowly");
		} else {
			adminOptionTreeMenu.setOption(6, new ItemStack(redwool),
					"Decay Tree", "A Tree will die instantly");
		}
		
		return adminOptionTreeMenu;
	}
}
