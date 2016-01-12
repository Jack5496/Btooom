package com.jack.btooom.data;

import com.jack.btooom.API.Btooom;

public class Messages {
	Btooom core;
		
		
	public Messages(Btooom core) {
		this.core = core;
	}
	
	private static boolean Inform_Guild_on_Respawn = true;
	public boolean getInform_Guild_on_Respawn() {
		return this.Inform_Guild_on_Respawn;
	}
	public void setInform_Guild_on_Respawn(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_Respawn = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_Respawn = false;
		}
	}
	
	private static boolean Inform_Guild_on_PlayerJoin = true;
	public boolean getInform_Guild_on_PlayerJoin() {
		return this.Inform_Guild_on_PlayerJoin;
	}
	public void setInform_Guild_on_PlayerJoin(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_PlayerJoin = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_PlayerJoin = false;
		}
	}
	
	private static boolean Inform_Player_of_Non_Tree_PvP = true;
	public boolean getInform_Player_of_Non_Tree_PvP() {
		return this.Inform_Player_of_Non_Tree_PvP;
	}
	public void setInform_Player_of_Non_Tree_PvP(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Player_of_Non_Tree_PvP = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Player_of_Non_Tree_PvP = false;
		}
	}
	
	private static boolean Inform_Guild_on_Fire = true;
	public boolean getInform_Guild_on_Fire() {
		return this.Inform_Guild_on_Fire;
	}
	public void setInform_Guild_on_Fire(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_Fire = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_Fire = false;
		}
	}
	
	private static boolean Inform_Guild_on_Enemy_Grief = true;
	public boolean getInform_Guild_on_Enemy_Grief() {
		return this.Inform_Guild_on_Enemy_Grief;
	}
	public void setInform_Guild_on_Enemy_Grief(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_Enemy_Grief = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_Enemy_Grief = false;
		}
	}
	
	private static boolean Inform_Guild_on_Explosion = true;
	public boolean getInform_Guild_on_Explosion() {
		return this.Inform_Guild_on_Explosion;
	}
	public void setInform_Guild_on_Explosion(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_Explosion = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_Explosion = false;
		}
	}
	
	private static boolean Inform_Guild_on_TreeGrow = true;
	public boolean getInform_Guild_on_TreeGrow() {
		return this.Inform_Guild_on_TreeGrow;
	}
	public void setInform_Guild_on_TreeGrow(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_TreeGrow = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_TreeGrow = false;
		}
	}
	
	private static boolean Inform_Guild_on_RootDestroy = true;
	public boolean getInform_Guild_on_RootDestroy() {
		return this.Inform_Guild_on_RootDestroy;
	}
	public void setInform_Guild_on_RootDestroy(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_RootDestroy = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_RootDestroy = false;
		}
	}
	
	private static boolean Inform_Guild_on_GuildJoin = true;
	public boolean getInform_Guild_on_GuildJoin() {
		return this.Inform_Guild_on_GuildJoin;
	}
	public void setInform_Guild_on_GuildJoin(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_GuildJoin = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_GuildJoin = false;
		}
	}
	
	private static boolean Inform_Guild_on_GuildLeave = true;
	public boolean getInform_Guild_on_GuildLeave() {
		return this.Inform_Guild_on_GuildLeave;
	}
	public void setInform_Guild_on_GuildLeave(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Guild_on_GuildLeave = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Guild_on_GuildLeave = false;
		}
	}
	
	
	
	
	
	private static boolean Inform_Player_on_RootPlace = true;
	public boolean getInform_Player_on_RootPlace() {
		return this.Inform_Guild_on_RootDestroy;
	}
	public void setInform_Player_on_RootPlace(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Player_on_RootPlace = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Player_on_RootPlace = false;
		}
	}
	
	private static boolean Inform_Player_on_Sapling_must_Set_Next_To_Log = true;
	public boolean getInform_Player_on_Sapling_must_Set_Next_To_Log() {
		return this.Inform_Player_on_Sapling_must_Set_Next_To_Log;
	}
	public void setInform_Player_on_Sapling_must_Set_Next_To_Log(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Player_on_Sapling_must_Set_Next_To_Log = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Player_on_Sapling_must_Set_Next_To_Log = false;
		}
	}
	
	private static boolean Inform_Player_on_Log_must_Set_Next_To_Log = true;
	public boolean getInform_Player_on_Log_must_Set_Next_To_Log() {
		return this.Inform_Player_on_Log_must_Set_Next_To_Log;
	}
	public void setInform_Player_on_Log_must_Set_Next_To_Log(String s) {
		if(s.equalsIgnoreCase("true")){
			this.Inform_Player_on_Log_must_Set_Next_To_Log = true;
		}
		if(s.equalsIgnoreCase("false")){
			this.Inform_Player_on_Log_must_Set_Next_To_Log = false;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
