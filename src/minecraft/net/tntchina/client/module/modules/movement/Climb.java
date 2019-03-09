package net.tntchina.client.module.modules.movement;

import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class Climb extends Module {

	private static Climb climb;
	
	public Climb(String name, ModuleCategory categorys) {
		super(name, categorys);
		Climb.climb = this;
	}
	
	public static Climb getInstance() {
		return Climb.climb;
	}
	
	public boolean canClimb() {
		return this.mc.thePlayer.isCollidedHorizontally && this.getState();
	}
}
