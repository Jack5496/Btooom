package com.jack.btooom.data;

import org.bukkit.Bukkit;

import com.jack.btooom.API.Btooom;

public class Configurations {
	
	Btooom core;
	Messages messages;
	
	
	public Configurations(Btooom core) {
		this.core = core;
	}
	
	private static boolean decay_tree = true;
	public boolean getDecay_tree() {
		return this.decay_tree;
	}
	public void setDecay_tree(String s) {
		if(s.equalsIgnoreCase("true")){
			this.decay_tree = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.decay_tree = false;
		}
	}
	
	private static boolean tree_menu = true;
	public boolean getTree_menu() {
		return this.tree_menu;
	}
	public void setTree_menu(String s) {
		if(s.equalsIgnoreCase("true")){
			this.tree_menu = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.tree_menu = false;
		}
	}

		
	private static boolean tree_pvp = false;
	public boolean getTree_Pvp() {
		return this.tree_pvp;
	}
	public void setTree_Pvp(String s) {
		if(s.equalsIgnoreCase("true")){
			this.tree_pvp = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.tree_pvp = false;
		}
	}
	
	private static boolean Damage_All_Blocks_on_Explosion = false;
	public boolean getDamage_All_Blocks_on_Explosion() {
		return this.Damage_All_Blocks_on_Explosion;
	}
	public void setDamage_All_Blocks_on_Explosion(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Damage_All_Blocks_on_Explosion = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Damage_All_Blocks_on_Explosion = false;
		}
	}
	
	private static boolean Only_Damage_Tree_on_Explosion = false;
	public boolean getOnly_Damage_Tree_on_Explosion() {
		return this.Only_Damage_Tree_on_Explosion;
	}
	public void setOnly_Damage_Tree_on_Explosion(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Only_Damage_Tree_on_Explosion = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Only_Damage_Tree_on_Explosion = false;
		}
	}
	
	private static boolean Only_Walk_on_Log = false;
	public boolean getOnly_Walk_on_Log() {
		return this.Only_Walk_on_Log;
	}
	public void setOnly_Walk_on_Log(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Only_Walk_on_Log = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Only_Walk_on_Log = false;
		}
	}
	
	private static boolean Sapling_must_Set_Next_To_Log = true;
	public boolean getSapling_must_Set_Next_To_Log() {
		return this.Sapling_must_Set_Next_To_Log;
	}
	public void setSapling_must_Set_Next_To_Log(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Sapling_must_Set_Next_To_Log = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Sapling_must_Set_Next_To_Log = false;
		}
	}
	
	
	private static boolean debug_mode = false;
	public boolean getDebug_Mode() {
		return this.debug_mode;
	}
	public void setDebug_Mode(String s) {
		if(s.equalsIgnoreCase("true")){
			this.debug_mode = true;
		}
		else if(s.equalsIgnoreCase("false")){
			this.debug_mode = false;
		}
	}
	
	private static boolean Create_Sign_On_Knot = false;
	public boolean getCreate_Sign_On_Knot() {
		return this.Create_Sign_On_Knot;
	}
	public void setCreate_Sign_On_Knot(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Create_Sign_On_Knot = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Create_Sign_On_Knot = false;
		}
	}
	
}
