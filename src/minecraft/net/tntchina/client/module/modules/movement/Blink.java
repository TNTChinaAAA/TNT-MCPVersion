package net.tntchina.client.module.modules.movement;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class Blink extends Module {

	public EntityOtherPlayerMP oldPlayer;
	
	public Blink(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onEnable() {
		if (this.oldPlayer == null) {
			this.oldPlayer = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile());
		}
		
		this.oldPlayer.setPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
		this.oldPlayer.rotationYawHead = this.mc.thePlayer.rotationYawHead;
		this.oldPlayer.clonePlayer(this.mc.thePlayer, true);
		this.oldPlayer.copyLocationAndAnglesFrom(this.mc.thePlayer);
		this.oldPlayer.rotationYaw = this.mc.thePlayer.rotationYaw;
		this.mc.theWorld.addEntityToWorld(-123, this.oldPlayer);
	}
	
	public void onDisable() {
		if (this.oldPlayer == null) {
			return;
		}
		
		this.mc.theWorld.removeEntityFromWorld(-123);
		this.oldPlayer = null;
	}
}