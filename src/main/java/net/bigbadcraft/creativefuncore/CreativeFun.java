package main.java.net.bigbadcraft.creativefuncore;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import main.java.net.bigbadcraft.creativefuncore.chatcontrol.ChatControlListener;
import main.java.net.bigbadcraft.creativefuncore.yourfriends.FriendManager;
import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class CreativeFun extends JavaPlugin {

	public static Chat chat = null;
	
	/* YourFriends Config Handlers */
	public ConfigHandler friendsCfg;
	public ConfigHandler pendingCfg;
	
	private FriendManager friendMang;
	
	private List<Listener> listeners = new ArrayList<Listener>();
	private List<ConfigHandler> cfgHandlers = new ArrayList<ConfigHandler>();
	
	public void onEnable() {
		saveDefaultConfig();
		loadConfigs();
		
		friendMang = new FriendManager(this);
		
		if (Bukkit.getPluginManager().getPlugin("Vault") != null) {
			setupChat();
			Util.log(Level.INFO, "Hooked into Vault's chat system!");
		}
		
		loadListeners();
	}
	
	private void loadListeners() {
		listeners.add(new ChatControlListener(this));
		
		/* Once all added, iterate and register */
		for (Listener l : listeners) {
			getServer().getPluginManager().registerEvents(l, this);
		}
	}
	
	private void loadConfigs() {
		friendsCfg = new ConfigHandler(this, "friends.yml");
		pendingCfg = new ConfigHandler(this, "pendingfriends.yml");
		
		cfgHandlers.add(friendsCfg);
		cfgHandlers.add(pendingCfg);
		
		for (ConfigHandler cfgs : cfgHandlers) {
			cfgs.load();
		}
	}
	
	private boolean setupChat() {
        RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (chatProvider != null) {
            chat = chatProvider.getProvider();
        }
        return (chat != null);
    }
	
	public FriendManager getFriendManager() {
		return friendMang;
	}
	
}
