package net.tntchina.client.module.modules.movement;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class Sprint extends Module {

	public Sprint(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null) {
			return;
		}
		
		if (this.mc.gameSettings.keyBindForward.isKeyDown()) {
			this.setSprinting();
		}
	}
}