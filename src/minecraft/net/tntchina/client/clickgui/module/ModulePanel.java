package net.tntchina.client.clickgui.module;

import net.tntchina.client.gui.Panel;
import net.tntchina.client.module.ModuleCategory;

public class ModulePanel extends Panel<ModuleButton> {

	private boolean state;
	
	public ModulePanel(ModuleCategory category) {
		super(category.getName());
		this.state = false;
	}

	public boolean getState() {
		return this.state;
	}

	public void setState(boolean state) {
		this.state = state;
	}
}