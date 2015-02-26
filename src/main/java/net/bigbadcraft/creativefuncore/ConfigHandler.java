package main.java.net.bigbadcraft.creativefuncore;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigHandler {

	private Plugin plugin;
	private String fileName;
	private File file;
	private FileConfiguration configuration;
	
	public ConfigHandler(Plugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		file = new File(plugin.getDataFolder(), fileName);
		configuration = YamlConfiguration.loadConfiguration(file);
	}
	
	public void load() {
		file = new File(plugin.getDataFolder(), fileName);
		Util.makeFile(file);
		configuration = YamlConfiguration.loadConfiguration(file);
		reload();
	}
	
    public void reload() {
        if (file == null) {
        	file = new File(plugin.getDataFolder(), fileName);
        }
        configuration = YamlConfiguration.loadConfiguration(file);
     
        // Look for defaults in the jar
        Reader defConfigStream = null;
		try {
			defConfigStream = new InputStreamReader(plugin.getResource(fileName), "UTF8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (defConfigStream != null) {
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            configuration.setDefaults(defConfig);
        }
    }
	
	public void save() {
		if (configuration == null || file == null) return;
		try {
			configuration.save(file);
		} catch (IOException e) {
			e.printStackTrace();
			Util.log(Level.SEVERE, "Could not save configuration changes to " + file.getName());
		}
	}
	
	public File getFile() {
		return file;
	}
	
	public FileConfiguration getCfg() {
		return configuration;
	}
	
}
