package net.tntchina.client.module.modules.world;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.values.NumberValue;

public class Timer extends Module {
	
	public NumberValue value = new NumberValue(4.9, "Scale", 10.0, 1.0);
	
	public Timer(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			this.mc.timer.timerSpeed = (float) this.value.getValue();
		} 
	}
	
	public void onDisable() {
		this.mc.timer.timerSpeed = (float) this.value.getValue();
	}
}
