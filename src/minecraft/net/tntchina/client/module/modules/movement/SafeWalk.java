package net.tntchina.client.module.modules.movement;

import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.SafeWalkEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class SafeWalk extends Module {

	public SafeWalk(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onSafeWalk(SafeWalkEvent event) {
		event.setSafe(!event.getSafe() ? (!this.getState() ? event.getSafe() : this.getState()) : event.getSafe());
	}
}
