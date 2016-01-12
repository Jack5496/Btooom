package com.jack.btooom.languages;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jack.btooom.Core;
import com.jack.btooom.filemanager.PlayersData;


public class Languages {

	public static File english = new File(Core.getInstance().getPlugindirDataLanguages()+"/English.yml");
	
	public static void initLanguage(){
		if(!english.exists()){
			createDefaultLanguage(english);
		}
	}

	/**
	 * INGAME PLAYER MESSAGES
	 */
	
	public static String getLeavingTreeMessage(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Leaving Tree");
	}
	
	public static String getFoundBackTreeMessage(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Tree Found");
	}
	
	public static String getYouMustFirstLeaveYourTree(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Must Leave Tree");
	}
	
	public static String getUsage(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Usage");
	}
	
	public static String getCantInvite2PeopleAtSameTime(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Cant Invite now");
	}
	
	public static String getPlayerIsNotOnline(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Player not Online");
	}
	
	public static String getInviteSend(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Invite Send");
	}
	
	public static String getInviteRecieved(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Invite Recieved");
	}
	
	public static String getInviteAccepted(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Invite Accepted");
	}
	
	public static String getYouLeftTree(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "You left Tree");
	}
	
	public static String getInformationTreeJoined(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "You Joined Tree");
	}
	
	public static String getInstallAddon(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Install Addon");
	}
	
	public static String getNewPlayerJoined(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "New Player Joined");
		return message.replaceAll("<Player>", args);
	}
	
	public static String getYouCantPickThatUp(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Cant Pickup");
		return message.replaceAll("<Item>", args);
	}
	
	public static String getCantDropIt(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Cant Drop");
		return message.replaceAll("<Item>", args);
	}
	
	public static String getPvpDisabled(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "PvP Disabled");
		return message.replaceAll("<Player>", args);		
	}
	
	public static String getRootDestroy(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Root Destroy");
		return message.replaceAll("<Player>", args);
	}
	
	public static String getRootPlacedMessage(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Root Placed");
	}
	
	public static String getSetSaplingNextToLog(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Sapling next to Log");		
	}
	
	public static String getCreativeModeAndNotChained(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Creative Placed");			
	}
	
	public static String getBlockIsNotChained(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Not Chained");	
	}
	
	public static String getYouAlreadyGotThisSapling(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Got Already Sapling");	
	}
	
	
	
	
	
	/**
	 * INGAME GUILD MESSAGES
	 */
	
	
	public static String getGuildMessage(String s, Player p, String args, String args2){
		if(s.equalsIgnoreCase("<GuildMemberOnline>")){
			return getGuildJoinMessage(p, args);
		}
		if(s.equalsIgnoreCase("<GuildNewMember>")){
			return getGuildNewMemberMessage(p);
		}
		if(s.equalsIgnoreCase("<GuildMemberLeftTree>")){
			return getGuildMemberLeftTree(p, args);
		}
		if(s.equalsIgnoreCase("<GuildMemberRespawn>")){
			return getGuildGuildMemberRespawn(p, args);
		}
		if(s.equalsIgnoreCase("<GuildTreeGrowEvent>")){
			return getGuildTreeGrowEventMessage(p, args);
		}
		if(s.equalsIgnoreCase("<GuildFirDamage>")){
			return getGuildFireDamageMessage(p, args);
		}
		if(s.equalsIgnoreCase("<GuildExplosionDamage>")){
			return getGuildExplosionDamageMessage(p, args);
		}
		if(s.equalsIgnoreCase("<GuildTreeDestroy>")){
			return getGuildTreeDestroy(p, args, args2);
		}
		if(s.equalsIgnoreCase("<GuildRootDestroy>")){
			return getGuildRootDestroy(p,args, args2);
		}
		else return "Probelm @ getGuildMessage :"+s;
	}
	
	public static String getGuildMessage(String s, Player p, String args){
		return getGuildMessage(s,p,args,"");
	}
	
	public static String getGuildMemberLeftTree(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Member Left Tree");
		return message.replaceAll("<Player>", args);
	}
	
	public static String getGuildGuildMemberRespawn(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Member Respawn");
		return message.replaceAll("<Location>", args);
	}
	
	public static String getGuildJoinMessage(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Member Online");
		return message.replaceAll("<Player>", args);
	}
	
	public static String getGuildTreeGrowEventMessage(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Tree Grow Event");
		return message.replaceAll("<Position>", args);
	}
	
	public static String getGuildFireDamageMessage(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Fire Damage");
		return message.replaceAll("<Position>", args);
	}
	
	public static String getGuildExplosionDamageMessage(Player p, String args){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Explosion Damage");
		return message.replaceAll("<Amount>", args);
	}
	
	public static String getGuildTreeDestroy(Player p, String args, String args2){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Tree Destroy");
		message = message.replaceAll("<Player>", args);
		return message.replaceAll("<Position>", args2);
	}
	
	public static String getGuildRootDestroy(Player p, String args, String args2){
		File adr = getLanguageForPlayer(p);
		String message = getMessageFromSearch(adr, "Guild Root Destroy");
		message = message.replaceAll("<Player>", args);
		return message.replaceAll("<Position>", args2);
	}
	
	public static String getGuildNewMemberMessage(Player p){
		File adr = getLanguageForPlayer(p);
		return getMessageFromSearch(adr, "Guild New Member");
	}
	
	
	
	
	/**
	 * INGAME MENU MESSAGES AND TEXT
	 */
	
	public static String getSwitchLanguageMessage(File adr){
		return getMessageFromSearch(adr, "Switch Language");
	}
	
	public static String getCommandDescriptionMessage(File adr, String command){
		return getMessageFromSearch(adr, command);	
	}
	
	public static File getLanguageForPlayer(Player p){
		String name = PlayersData.getPlayerLanguage(p);
		if(name.equalsIgnoreCase("Default")){
			name = "English";
		}
		File back = new File(Core.getInstance().getPlugindirDataLanguages()+"/"+name+".yml");
		if(back.exists()){
			return back;
		}
		else{
			return english;
		}
	}
	
	public static String getMessageFromSearch(File adr, String search){
		if(!adr.exists()){
			createDefaultLanguage(adr);
		}
		YamlConfiguration temp = YamlConfiguration
				.loadConfiguration(adr);
		if(temp.get(search)==null){
			temp.addDefault(search, "Update Language: "+adr.getName());
			temp.options().copyDefaults(true);
			try {
				temp.save(adr);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return ChatColor.translateAlternateColorCodes('&', ""+temp.get(search));
	}
	
	public static void createDefaultLanguage(File adr){
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration temp = YamlConfiguration
						.loadConfiguration(adr);
				temp.addDefault("#Colors#", "You can use Colors &A,&B,&C,...");
				temp.addDefault("# #", "");
				temp.addDefault("#Player Information#", "All Information for a Single Player");
				temp.addDefault("#  #", "");
				temp.addDefault("Switch Language", "'Switch Language'");	
				temp.addDefault("Leaving Tree", "&6[TreeSpirit]&F - You are leaving your Tree!");	
				temp.addDefault("Tree Found", "&6[TreeSpirit]&F - Found back to your Tree!");
				temp.addDefault("Must Leave Tree", "&6[TreeSpirit]&F - Leave Tree first!");	
				temp.addDefault("Usage", "Usage");
				temp.addDefault("Cant Invite now", "&6[TreeSpirit]&F - Wait to Invite!");	
				temp.addDefault("Player not Online", "&6[TreeSpirit]&F - This Player is not Online!");
				temp.addDefault("Invite Send", "&6[TreeSpirit]&F - Invite Send!");	
				temp.addDefault("Invite Recieved", "&6[TreeSpirit]&F - Invite Recieved!");
				temp.addDefault("Invite Accepted", "&6[TreeSpirit]&F - Invite Accepted!");	
				temp.addDefault("You left Tree", "&6[TreeSpirit]&F - You left the Tree!");
				temp.addDefault("You Joined Tree", "&6[TreeSpirit]&F - You Joined the Tree!");	
				temp.addDefault("Install Addon", "&6[TreeSpirit]&F - Install the Addon!");
				temp.addDefault("New Player Joined", "&6[TreeSpirit]&F - New Player <Player> Joined!");	
				temp.addDefault("Cant Pickup", "&6[TreeSpirit]&F - You cant Pickup <Item>!");
				temp.addDefault("Cant Drop", "&6[TreeSpirit]&F - You cant Drop <Item>!");	
				temp.addDefault("PvP Disabled", "&6[TreeSpirit]&F - PvP is Disabled, you cant hurt <Player>!");
				temp.addDefault("Root Destroy", "&6[TreeSpirit]&F - Root was destroyed by <Player>!");	
				temp.addDefault("Root Placed", "&6[TreeSpirit]&F - Root Placed!");
				temp.addDefault("Sapling next to Log", "&6[TreeSpirit]&F - Place the Sapling next to Log!");	
				temp.addDefault("Creative Placed", "&6[TreeSpirit]&c - Unchained Creative Block!");
				temp.addDefault("Not Chained", "&6[TreeSpirit]&c - This Block is not Chained");	
				temp.addDefault("Got Already Sapling", "&6[TreeSpirit]&F - You Got Already a Sapling!");
				temp.addDefault("#   #", "");
				temp.addDefault("#Guild Information#", "All Information for a Guild/Tree");
				temp.addDefault("#     #", "");
				temp.addDefault("Guild Member Left Tree", "&6[Tree]&F - Player <Player> left the Tree!");	
				temp.addDefault("Guild Member Respawn", "&6[Tree]&F - Guild Member respawned at <Location>!");	
				temp.addDefault("Guild Member Online", "&6[Tree]&F - Guild Member <Player> is now Online!");	
				temp.addDefault("Guild Tree Grow Event", "&6[Tree]&F - A Srpout grew up at <Position>");	
				temp.addDefault("Guild Fire Damage", "&6[Tree]&F - Fire at <Position>!");	
				temp.addDefault("Guild Explosion Damage", "&6[Tree]&F - An Explosion destroyed <Amount> Blocks!");	
				temp.addDefault("Guild Tree Destroy", "&6[Tree]&F - Player <Player> is destroying me at <Position>!");	
				temp.addDefault("Guild Root Destroy", "&6[Tree]&F - Player <Player> destoyed the Root at <Position>!");	
				temp.addDefault("Guild Explosion Damage", "&6[Tree]&F - An Explosion destroyed <Amount> Blocks!");	
				temp.addDefault("Guild Tree Destroy", "&6[Tree]&F - Player <Player> is destroying me at <Position>!");	
				temp.addDefault("Guild New Member", "&6[Tree]&F - A new Player joined the Tree!");	
				
				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		}
	}
}
