package net.tntchina.client.module.modules.movement;

import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.values.NumberValue;

public class BugUp extends Module {

	private BlockPos pos = new BlockPos(0, 0, 0);
	public boolean spawn = false;
	private NumberValue checkHeight = new NumberValue(5.0, "FallDistance", 6.0, 4.0);
	private static BugUp INSTANCE;
	
	public BugUp(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
		BugUp.INSTANCE = this;
	}
	
	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.thePlayer.capabilities.isFlying) {
			return;
		}
		
		if (this.spawn && !this.mc.thePlayer.onGround) {
			return;
		} else {
			this.spawn = false;
		}
		
		if (!this.isInBlock()) {
			this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
		} else {
			BlockPos blockpos = new BlockPos(this.mc.getRenderViewEntity().posX, this.mc.getRenderViewEntity().getEntityBoundingBox().minY, this.mc.getRenderViewEntity().posZ);
			this.pos = blockpos;
		}
		
		if (!this.isInBlock()) {
			if (this.pos.getY() - this.mc.thePlayer.posY >= this.checkHeight.getObject()) {
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				this.logger.info(this.pos.getX() + " | " + this.pos.getY() + " | " + this.pos.getZ());
				this.mc.thePlayer.setPosition(this.pos.getX(), this.pos.getY(), this.pos.getZ());
				this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
			}
		}
	}
	
	public void setPosition(double x, double y, double z) {
		this.mc.thePlayer.setPosition(x, y, z);
	}
	
	public boolean isInBlock() {
		return this.mc.thePlayer.onGround && !this.mc.thePlayer.isOnLadder() && !this.mc.thePlayer.isInLava() && !this.isOnLiquid();
	}
	
	public boolean isInAir() {
		return this.mc.theWorld.isAirBlock(this.mc.thePlayer.getPosition());
	}
	
	public static BugUp getInstance() {
		return BugUp.INSTANCE;
	}
}