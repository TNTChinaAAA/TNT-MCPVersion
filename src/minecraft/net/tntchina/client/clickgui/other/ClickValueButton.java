package net.tntchina.client.clickgui.other;

import net.tntchina.client.gui.Button;
import net.tntchina.client.gui.Label;
import net.tntchina.client.value.values.ClickValue;

public class ClickValueButton extends Button {
	
	private ClickValue value;
	
	public ClickValueButton(ClickValue value) {
		super(new Label(value.getName()));
		this.value = value;
	}
	
	public String getName() {
		return this.value.getName();
	}
	
	public Enum<?> getType() {
		return this.value.getType();
	}
	
	public void onTickButton() {
		this.value.getEvent().onClick();
	}
}