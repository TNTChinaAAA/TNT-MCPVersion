package net.tntchina.client.module.modules.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.util.AxisAlignedBB;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.BlockBBEvent;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class SnowFly extends Module {

	public SnowFly(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onAabb(BlockBBEvent event) {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null | this.mc.gameSettings == null) {
			return;
		}
		
		if (!this.mc.gameSettings.keyBindSneak.isKeyDown() && this.mc.thePlayer.posY > event.getPos().getY()) {
			if (this.isVaild(event.getBlock())) {
				event.setAxisAlignedBB(new AxisAlignedBB(event.getPos(), event.getPos().add(1.0, 1.0, 1.0)));
			}
		}
	}
	
	@EventTarget
	public void onMotionUpdate(MotionUpdateEvent event) {
		if (!this.getState() | !event.onPre() | !this.isVaild(this.mc.theWorld.getBlockState(this.mc.thePlayer.getPosition().add(0, -1, 0)).getBlock()) | !this.isVaild(this.mc.theWorld.getBlockState(this.mc.thePlayer.getPosition()).getBlock())) {
			return;
		}
		
		if (this.mc.gameSettings.keyBindJump.isPressed() && this.mc.thePlayer.onGround) {
			this.mc.thePlayer.motionX *= 0.25;
			this.mc.thePlayer.motionZ *= 0.25;
			this.mc.thePlayer.jump();
			this.mc.thePlayer.motionY = 3;
		}
		
		this.mc.thePlayer.onGround = false;
	}
	
	public boolean isVaild(Block block) {
		return block instanceof BlockSnow;
	}
}