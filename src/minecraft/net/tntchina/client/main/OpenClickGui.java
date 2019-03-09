package net.tntchina.client.main;

import net.tntchina.client.clickgui.*;
import net.tntchina.client.module.*;

public class OpenClickGui extends Module {

	public ClickGui clickGui = new ClickGui();
	
	public OpenClickGui(String name, int key, ModuleCategory category) {
		super(name, key, category);
	}
	
	public void onEnable() {
		if (this.mc.currentScreen == null) {
			this.displayClickGui();
		} else {
			if (!(this.mc.currentScreen instanceof ClickGui)) {
				this.displayClickGui();
			}
		}
	}
	
	public void displayClickGui() {
		this.mc.displayGuiScreen(this.clickGui);
	}
	
	public void setup() {
		this.clickGui.init();
	}
	
	public void setState(boolean state) {
		this.state = state;
		
		if (state) {
			this.onEnable();
		} else {
			this.onDisable();
		}
	}
	
	public ClickGui getClickGui() {
		return this.clickGui;
	}
}