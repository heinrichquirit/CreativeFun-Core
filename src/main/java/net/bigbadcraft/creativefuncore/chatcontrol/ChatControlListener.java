package main.java.net.bigbadcraft.creativefuncore.chatcontrol;

import java.util.List;

import main.java.net.bigbadcraft.creativefuncore.ConfigPath;
import main.java.net.bigbadcraft.creativefuncore.CreativeFun;
import main.java.net.bigbadcraft.creativefuncore.Util;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatControlListener implements Listener {
	
	private CreativeFun plugin;
	private FileConfiguration cfg;
	
	public ChatControlListener(CreativeFun plugin) {
		this.plugin = plugin;
		cfg = this.plugin.getConfig();
	}
	
	@EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
	public void onChat(AsyncPlayerChatEvent e) {
		if (Util.getBoolean(cfg, ConfigPath.CHAT_CONTROL_ENABLE)) {
			Player p = e.getPlayer();
			List<String> swears = Util.getStringList(cfg, ConfigPath.CHAT_CONTROL_WORDS);
			for (String swear : swears) {
				if (e.getMessage().contains(swear)) {
					p.sendMessage(ChatColor.translateAlternateColorCodes('&',
							Util.getString(cfg, ConfigPath.CHAT_CONTROL_MESSAGE)));
					e.setCancelled(true);
					return;
				}
			} 
			String message = ("" + e.getMessage().charAt(0)).toUpperCase() + e.getMessage().substring(1);
			String[] formatArgs = e.getFormat().split(" ");
			// The message variable of the format
			String messageVar = formatArgs[formatArgs.length - 1];
			e.setFormat(e.getFormat().replace(messageVar, message));
		}
	}

}
