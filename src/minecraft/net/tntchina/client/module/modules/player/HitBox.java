package net.tntchina.client.module.modules.player;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.values.NumberValue;

public class HitBox extends Module {

	public NumberValue hitBox = new NumberValue(100.0, "HitBox", 1000.0, 0.1);
	
	public HitBox(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public float getValue() {
		return (float) this.hitBox.getValue();
	}
	
	public String getMode() {
		return this.hitBox.getValue() + "";
	}
	
	public boolean hasMode() {
		return true;
	}
}
