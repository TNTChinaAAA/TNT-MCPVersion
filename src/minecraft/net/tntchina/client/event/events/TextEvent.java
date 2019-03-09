package net.tntchina.client.event.events;

import net.tntchina.client.event.Event;

public class TextEvent extends Event {
	
	private String str;
	
	public TextEvent(String str) {
		this.str = str;
	}

	public String getStr() {
		return this.str;
	}

	public void setStr(String str) {
		this.str = str;
	}
}