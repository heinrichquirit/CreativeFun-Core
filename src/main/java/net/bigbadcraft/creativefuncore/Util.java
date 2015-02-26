package main.java.net.bigbadcraft.creativefuncore;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Util {

	public static void makeFile(File file) {
		if (!file.exists()) {
			try {
				file.createNewFile();
				log(Level.INFO, "Successfully generated " + file.getName()); 
			} catch (IOException e) {
				log(Level.SEVERE, "Failed to generate " + file.getName());
				// Print so they know
				e.printStackTrace();
			}
		}
	}
	
	public static void log(Level level, String message) {
		Bukkit.getLogger().log(level, "[CreativeFun-Core] " + message);
	}
	
	@SuppressWarnings("deprecation")
	public static Player getPlayer(UUID uuid) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getUniqueId().equals(uuid)) {
				return p;
			}
		}
		return null;
	}
	
	// Get UUID from String
	public static UUID getUUIDFrom(String uuid) {
		return null;
	}
	
	
	// FileConfiguration getters
	
	public static String getString(FileConfiguration cfg, ConfigPath path) {
		return cfg.getString(path.toString());
	}
	
	public static List<String> _getStringList(FileConfiguration cfg, String path) {
		return cfg.getStringList(path);
	}
	
	public static List<String> getStringList(FileConfiguration cfg, ConfigPath path) {
		return cfg.getStringList(path.toString());
	}
	
	public static int getInt(FileConfiguration cfg, ConfigPath path) {
		return cfg.getInt(path.toString());
	}
	
	public static boolean getBoolean(FileConfiguration cfg, ConfigPath path) {
		return cfg.getBoolean(path.toString());
	}
	
}
