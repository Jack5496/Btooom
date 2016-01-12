package com.jack.btooom.filemanager;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.jack.btooom.Core;

public class PlayersData {

	public static void createPlayerData(Player p) {
		File adr = new File(Core.getInstance().getPlugindirDataPlayers() + "/" + p.getName() + ".yml");
		if (!adr.exists()) {
			try {
				adr.createNewFile();
				YamlConfiguration temp = YamlConfiguration.loadConfiguration(adr);
				temp.addDefault("Language", "Default");
				temp.addDefault("Blocks", 0);
				temp.addDefault("Beam", "None");

				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				// System.out.println(dateFormat.format(date)); //2014/08/06
				// 15:59:48
				temp.addDefault("First Join", dateFormat.format(date));

				date = new Date();
				// System.out.println(dateFormat.format(date)); //2014/08/06
				// 15:59:48
				temp.addDefault("Last Join", dateFormat.format(date));

				temp.options().copyDefaults(true);
				temp.save(adr);
			} catch (IOException ex) {

			}
		}
		YamlConfiguration ycf = YamlConfiguration.loadConfiguration(adr);
	}

	public static void savePlayer(Player p) {
		if (p != null) {
			YamlConfiguration ycf = getPlayerConfigFile(p);

			ycf.options().copyDefaults(true);
			try {
				ycf.save(getPlayerFile(p));
			} catch (IOException e) {
				Bukkit.broadcastMessage("Error by saving Player");
			}
		}

	}

	public static void savePlayerLanguage(Player p, String language) {
		YamlConfiguration ycf = getPlayerConfigFile(p);
		File adr = getPlayerFile(p);

		ycf.set("Language", language);
		ycf.options().copyDefaults(true);
		try {
			ycf.save(adr);
		} catch (IOException e) {
			Bukkit.broadcastMessage("Error by saving Player Language");
		}
	}
	
	/**
	 * Get Player Language
	 */

	public static String getPlayerLanguage(Player p) {
		return getPlayerInformation(p, "Language");
	}

	public static YamlConfiguration getPlayerConfigFile(Player p) {
		File adr = getPlayerFile(p);
		if (!adr.exists()) {
			createPlayerData(p);
		}
		return YamlConfiguration.loadConfiguration(adr);
	}

	public static File getPlayerFile(Player p) {
		return new File(Core.getInstance().getPlugindirDataPlayers() + "/" + p.getName() + ".yml");
	}

	/**
	 * GetInformation from Player
	 * 
	 * @param p
	 *            Player
	 * @param search
	 *            String
	 * @return
	 */
	public static String getPlayerInformation(Player p, String search) {
		YamlConfiguration ycf = getPlayerConfigFile(p);
		return ycf.getString(search);
	}

	/**
	 * get Players First Join
	 */

	public static String getFirstJoin(Player p) {
		return getPlayerInformation(p, "First Join");
	}
	
	public static void savePlayerBeam(Player p, String beam) {
		YamlConfiguration ycf = getPlayerConfigFile(p);
		File adr = getPlayerFile(p);

		ycf.set("Beam", beam);
		ycf.options().copyDefaults(true);
		try {
			ycf.save(adr);
		} catch (IOException e) {
			Bukkit.broadcastMessage("Error by saving Player Language");
		}
	}
	
	public static String getBeamType(Player p) {
		return getPlayerInformation(p, "Beam");
	}
	

	

}
