package net.tntchina.client.module.modules.misc;

import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;

import net.minecraft.network.play.client.C01PacketChatMessage;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.UpdateEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.util.TimeHelper;
import net.tntchina.client.value.values.NumberValue;

public class Spammer extends Module {
	
	private String message = "Debug are not strong, but TNT are strong client!";
	public TimeHelper time = new TimeHelper();
	private NumberValue delay = new NumberValue(1000.0, "Delay", 3000.0, 0.0);
	
	public Spammer(String name, ModuleCategory categorys) {
		super(name, categorys);
		this.time.setLastMS();
	}

	public static int getRandom(int min, int max) {
		Random rand = new Random();
		return min + rand.nextInt(max - min + 1);
	}

	public String getMessage() {
		return this.message + " | " + RandomStringUtils.randomAlphanumeric(8);
	}

	@EventTarget
	public void onUpdate(UpdateEvent event) {
		if (this.getState() && event.getState() == UpdateEvent.State.PRE) {
			if (this.time.hasTimeReached(this.delay.getValue())) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(this.getMessage()));
				this.time.setLastMS();
			}
		}
	}
}
