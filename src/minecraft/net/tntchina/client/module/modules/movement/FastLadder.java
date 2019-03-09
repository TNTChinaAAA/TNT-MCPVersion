package net.tntchina.client.module.modules.movement;

import java.util.Arrays;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.event.events.MotionUpdateEvent.State;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.values.ModeValue;

public class FastLadder extends Module {

	public ModeValue mode = new ModeValue("AAC3.3.10", "Mode", Arrays.asList("AAC3.3.10"));
	
	public FastLadder(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onPostUpdate(MotionUpdateEvent event) {
		if (!this.getState() | event.getState() != State.POST | !this.isModeEqual("AAC3.3.10")) {
			return;
		}
		
		if (this.mc.thePlayer.isOnLadder() && this.mc.gameSettings.keyBindJump.isKeyDown()) {
			this.mc.thePlayer.motionY = 0.169;
		}
	}
	
	public boolean isModeEqual(String mode) {
		return this.mode.getObject().equalsIgnoreCase(mode);
	}
	
	public String getMode() {
		return this.mode.getObject();
	}
	
	public boolean hasMode() {
		return true;
	}
}