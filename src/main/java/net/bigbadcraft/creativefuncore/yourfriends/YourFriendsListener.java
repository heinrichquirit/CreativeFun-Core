package main.java.net.bigbadcraft.creativefuncore.yourfriends;

import java.util.ArrayList;
import java.util.List;

import main.java.net.bigbadcraft.creativefuncore.ConfigPath;
import main.java.net.bigbadcraft.creativefuncore.CreativeFun;
import main.java.net.bigbadcraft.creativefuncore.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class YourFriendsListener implements Listener {

	private CreativeFun p;

	public YourFriendsListener(CreativeFun plugin) {
		p = plugin;
	}

	// USE UUIDS
	
	/* Register player's name to file */
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onJoin(PlayerJoinEvent e) {
		String pID = e.getPlayer().getUniqueId().toString();
		if (!p.friendsCfg.getCfg().contains(pID)) {
			p.friendsCfg.reload();
			p.friendsCfg.getCfg().set(pID, new ArrayList<String>());
			p.friendsCfg.save();
		}
		if (!p.pendingCfg.getCfg().contains(pID)) {
			p.pendingCfg.reload();
			p.pendingCfg.getCfg().set(pID, new ArrayList<String>());
			p.pendingCfg.save();
		}
	}

	/* Notify friends on join */
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
	public void onFriendJoin(PlayerJoinEvent e) {
		if (Util.getBoolean(p.getConfig(), ConfigPath.YF_NOTIFY_JOIN) == false) {
			return;
		}
		Player player = e.getPlayer();
		for (Player players : Bukkit.getOnlinePlayers()) {
			List<String> list = Util._getStringList(p.friendsCfg.getCfg(), players.getUniqueId().toString());
			if (list.contains(player.getName())) {
				p.getFriendManager().notificationPing(players);
				p.getFriendManager().makeMessage(players, "Your friend "
						+ player.getName() + " has joined the server.");
			}
		}
	}

}
