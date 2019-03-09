package net.tntchina.client.module.modules.render;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class NotViewBob extends Module {

	public NotViewBob(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			this.mc.gameSettings.viewBobbing = false;
		}
	}
}
