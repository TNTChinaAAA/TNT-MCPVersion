package net.tntchina.client.module.modules.render;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class XRay extends Module {

	public XRay(String name, int key, ModuleCategory category) {
		super(name, key, category);
	}
	
	public void onEnable() {
		this.mc.renderGlobal.loadRenderers();
	}
	
	public void onDisable() {
		this.mc.renderGlobal.loadRenderers();
	}
}
