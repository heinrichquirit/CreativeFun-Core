package main.java.net.bigbadcraft.creativefuncore;

public enum ConfigPath {
	
	CHAT_CONTROL_ENABLE("chat-control.enable-functionality"),
	CHAT_CONTROL_MESSAGE("chat-control.warn-message"),
	CHAT_CONTROL_WORDS("chat-control.blacklisted-words"),
	VOTE_LISTENER_TIMEDCMDS("vote-listener.timed-commands"),
	YF_NOTIFICATION_SOUND("your-friends.notification-sound"),
	YF_SCOREBOARD_COLOUR("your-friends.show-scoreboard"),
	YF_NOTIFY_JOIN("your-friends.notify-on-join"),
	YF_NOTIFY_LEAVE("your-friends.notify-on-leave"),
	YF_SHOW_FRIENDS("your-friends.show-friends"),
	YF_FRIEND_LIMIT("your-friends.friend-limit");
	
	private final String p;
	ConfigPath(String path) {
		p = path;
	}
	
	@Override
	public String toString() {
		return p;
	}
	
}
