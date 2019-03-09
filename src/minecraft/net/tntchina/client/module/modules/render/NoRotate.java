package net.tntchina.client.module.modules.render;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.PacketEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class NoRotate extends Module {

	public NoRotate(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	@EventTarget
    public void onEvent(PacketEvent event) {
		if (!this.getState() | this.mc.thePlayer == null) {
			return;
		}
		
		if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook) event.getPacket();
            pac.yaw = this.mc.thePlayer.rotationYaw;
            pac.pitch = this.mc.thePlayer.rotationPitch;
        }
    }
}
