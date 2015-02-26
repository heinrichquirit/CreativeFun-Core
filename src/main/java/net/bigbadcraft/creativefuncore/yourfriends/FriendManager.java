package main.java.net.bigbadcraft.creativefuncore.yourfriends;

import static org.bukkit.ChatColor.AQUA;
import static org.bukkit.ChatColor.DARK_AQUA;

import java.util.List;

import main.java.net.bigbadcraft.creativefuncore.ConfigPath;
import main.java.net.bigbadcraft.creativefuncore.CreativeFun;
import main.java.net.bigbadcraft.creativefuncore.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import com.google.common.base.Joiner;

public class FriendManager {

	private final String PREFIX = DARK_AQUA + "[YourFriends] ";
	
	private CreativeFun p;
	public FriendManager(CreativeFun plugin) {
		p = plugin;
	}
	
	// use uuids 
	public void showFriendRequests(Player player) {
		String requests = Joiner.on(", ").join(Util._getStringList(p.friendsCfg.getCfg(), player.getUniqueId().toString())) + ".";
		makeMessage(player, "Friend Requests: " + requests);
	}

	public void showFriends(Player player) {
		StringBuilder sb = new StringBuilder();
		List<String> list = p.friendsCfg.getCfg().getStringList(player.getUniqueId().toString());
		for (String players : list) {
			ChatColor color = Bukkit.getPlayer(players) != null ? ChatColor.GREEN
					: ChatColor.RED;
			sb.append(list.indexOf(players) == list.size() - 1 ? color
					+ players + ChatColor.WHITE + "." : color + players
					+ ChatColor.WHITE + ", ");
		}
		makeMessage(player, "Friends (" + list.size() + "): " + sb.toString());
	}

	public void deleteFriend(Player player, String to_delete) {
		String playerUUID = player.getUniqueId().toString();
		p.friendsCfg.reload();
		List<String> list = p.friendsCfg.getCfg().getStringList(playerUUID);
		list.remove(to_delete);
		p.friendsCfg.getCfg().set(playerUUID, list);
		List<String> list_two = p.friendsCfg.getCfg().getStringList(to_delete);
		list_two.remove(player.getName());
		p.friendsCfg.getCfg().set(to_delete, list_two);
		p.friendsCfg.save();
	}

	public boolean hasFriends(Player player) {
		return p.friendsCfg.getCfg().getStringList(player.getUniqueId().toString()).size() > 0;
	}

	public boolean areFriends(Player friend_one, String friend_two) {
		String friendOneId = friend_one.getUniqueId().toString();
		@SuppressWarnings("deprecation")
		String friendTwoId = Bukkit.getOfflinePlayer(friend_two).getUniqueId().toString();
		return p.friendsCfg.getCfg().getStringList(friendOneId)
				.contains(friendTwoId)
				&& p.friendsCfg.getCfg().getStringList(friendTwoId).contains(
						friendOneId);
	}

	public void acceptRequest(Player player, String to_accept) {
		String playerId = player.getUniqueId().toString();
		@SuppressWarnings("deprecation")
		String toAcceptId = Bukkit.getOfflinePlayer(to_accept).getUniqueId().toString();
		p.pendingCfg.reload();
		List<String> list = p.pendingCfg.getCfg().getStringList(playerId);
		list.remove(toAcceptId);
		p.pendingCfg.getCfg().set(playerId, list);
		p.pendingCfg.save();
		p.friendsCfg.reload();
		List<String> list_two = p.friendsCfg.getCfg().getStringList(playerId);
		list_two.add(toAcceptId);
		p.friendsCfg.getCfg().set(playerId, list_two);
		List<String> list_three = p.friendsCfg.getCfg().getStringList(toAcceptId);
		list_three.add(playerId);
		p.friendsCfg.getCfg().set(toAcceptId, list_three);
		p.friendsCfg.save();
	}

	public void rejectRequest(Player player, String to_reject) {
		String playerId = player.getUniqueId().toString();
		@SuppressWarnings("deprecation")
		String toRejectId = Bukkit.getOfflinePlayer(to_reject).getUniqueId().toString();
		p.pendingCfg.reload();
		List<String> list = p.pendingCfg.getCfg().getStringList(playerId);
		list.remove(toRejectId);
		p.pendingCfg.getCfg().set(playerId, list);
		p.pendingCfg.save();
	}

	public void sendRequest(Player player, Player target) {
		p.pendingCfg.reload();
		List<String> list = p.pendingCfg.getCfg().getStringList(target
				.getName());
		list.add(player.getName());
		p.pendingCfg.getCfg().set(target.getName(), list);
		p.pendingCfg.save();
	}

	public boolean hasTheirRequest(Player viewer, String requester) {
		return p.pendingCfg.getCfg().getStringList(viewer.getName())
				.contains(requester);
	}

	public boolean isValidRequest(Player player, String requester) {
		return p.pendingCfg.getCfg().getStringList(player.getName())
				.contains(requester);
	}

	public boolean hasFriendRequests(Player player) {
		return p.pendingCfg.getCfg().getStringList(player.getName())
				.size() > 0;
	}

	// Display online and offline status.
	public String getHelpMenu() {
		return DARK_AQUA + "----------(" + AQUA + "YourFriends"
				+ DARK_AQUA + ")----------\n" + AQUA + "Shortcut: "
				+ DARK_AQUA + "/yf\n" + AQUA
				+ "-/yourfriends add <player>" + DARK_AQUA
				+ " - Adds specified player.\n" + AQUA
				+ "-/yourfriends delete <player>" + DARK_AQUA
				+ " - Removes player from your friends list.\n" + AQUA
				+ "-/yourfriends accept <player>" + DARK_AQUA
				+ " - Accept player's friend request.\n" + AQUA
				+ "-/yourfriends reject <player>" + DARK_AQUA
				+ " - Reject player's friend request.\n" + AQUA
				+ "-/yourfriends requests" + DARK_AQUA
				+ " - List your current friend requests.\n" + AQUA
				+ "-/yourfriends list" + DARK_AQUA
				+ " - List your current friends.\n" + AQUA
				+ "-/yourfriends nudge <friend>" + DARK_AQUA
				+ " - Nudge your friend for attention.\n" + DARK_AQUA
				+ "---------------------------------";
	}

	public void notificationPing(Player player) {
		player.playSound(player.getLocation(),
				Sound.valueOf(Util.getString(p.getConfig(), ConfigPath.YF_NOTIFICATION_SOUND).toUpperCase()), 1F, 1F);
	}

	public void makeMessage(Player player, String error) {
		player.sendMessage(PREFIX + AQUA + error);
	}

	
	public void promptSyntax(Player player, String cmd_syntax) {
		player.sendMessage(PREFIX + AQUA
				+ "Incorrect syntax, usage: " + cmd_syntax);
	}

}
