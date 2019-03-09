package net.tntchina.client.module.modules.combat;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.values.NumberValue;

public class AntiBot extends Module {

	private NumberValue distance = new NumberValue(5.0, "Distance", 6.0, 3.0);
	
	public AntiBot(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		synchronized (this.getEntityList()) {
			for (Entity entity : this.getEntityList()) {
				if (!(entity instanceof EntityLivingBase)) {
					continue;
				}
				
				if (!this.isValid((EntityLivingBase) entity)) {
					continue;
				}
				
				if (entity instanceof EntityLivingBase) {
					float[] facing = KillAura.getNeededRotations(this.getRandomCenter(entity.boundingBox));;
					this.mc.thePlayer.rotationYaw = facing[0];
					this.mc.thePlayer.rotationPitch = facing[1];
					this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(entity.posX, entity.posY, entity.posZ, facing[0], facing[1], true));
				}
			}
		}
	}
	
	public strictfp boolean isLong(Entity Living) {
		double value = this.distance.getValue();
		double range = Living.isInWater() ? (value - 1) : (value);
		return this.mc.thePlayer.getDistanceToEntity(Living) >= range;
	}
	
	public strictfp boolean isValid(EntityLivingBase Living) {
		return !this.isPlayer(Living) && (Living.canAttackWithItem() | Living instanceof EntityPlayer) && Living.isEntityAlive() && !this.isLong(Living);
	}
	
	public strictfp Vec3 getRandomCenter(AxisAlignedBB bb) {
		return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * 0.8, bb.minY + (bb.maxY - bb.minY) * 1 * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * 0.8);
	}
	
	public boolean isPlayer(EntityLivingBase e) { 
		if (this.mc.thePlayer.equals(e)) {
			return true;
		}
		
		if (e instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) e;
			
			if (player.getGameProfile() == null) {
				return false;
			} else {
				if (player.getGameProfile().equals(this.mc.thePlayer.getGameProfile())) {
					return true;
				} else {
					return false;
				}
			}
		} else {
			return false;
		}
	}
}