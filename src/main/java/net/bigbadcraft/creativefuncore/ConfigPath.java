package main.java.net.bigbadcraft.creativefuncore;

public enum ConfigPath {
	
	CHAT_CONTROL_ENABLE("chat-control.enable-functionality"),
	CHAT_CONTROL_MESSAGE("chat-control.warn-message"),
	CHAT_CONTROL_WORDS("chat-control.blacklisted-words"),
	VOTE_LISTENER_TIMEDCMDS("vote-listener.timed-commands")
	;
	
	private final String p;
	ConfigPath(String path) {
		p = path;
	}
	
	@Override
	public String toString() {
		return p;
	}
	
}
