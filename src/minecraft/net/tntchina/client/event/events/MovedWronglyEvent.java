package net.tntchina.client.event.events;

import net.tntchina.client.event.Event;

public class MovedWronglyEvent extends Event {
	
	private boolean e;
	
	public MovedWronglyEvent(boolean e) {
		this.e = e;
	}
	
	public void setState(boolean state) {
		this.e = state;
	}
	
	public boolean getState() {
		return this.e;
	}
}
