package net.tntchina.client.event.events;

import net.tntchina.client.event.Event;

public class CreateDisplayEvent extends Event {
	
	private int height, width;

	public CreateDisplayEvent(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public int getHeight() {
		return this.height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return this.width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
}
