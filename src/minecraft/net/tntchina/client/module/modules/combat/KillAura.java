package net.tntchina.client.module.modules.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.*;
import net.tntchina.client.event.EventManager;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.AttackEntityEvent;
import net.tntchina.client.event.events.CancellableEvent;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.util.MathHelper;
import net.tntchina.client.util.RotationUtils;
import net.tntchina.client.value.values.BooleanValue;
import net.tntchina.client.value.values.IntegerValue;
import net.tntchina.client.value.values.NumberValue;

public class KillAura extends Module {

	private volatile List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
	private static KillAura killaura;
	private NumberValue cps = new NumberValue(10, "CPS", 20, 1);
	public NumberValue range = new NumberValue(4.5, "Range", 6.0, 1.0);
	private IntegerValue attackTargetNumber = new IntegerValue(1, "AttackNumber", 4L, 1L);
	private IntegerValue hurtTime = new IntegerValue(5, "HurtTime", 10, 1);
	private BooleanValue canAttackMob = new BooleanValue(true, "AttackMob");
	private BooleanValue autoBlock = new BooleanValue(false, "AutoBlock");
	
	public KillAura(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
		KillAura.setKillAura(this);
	}

	public boolean hasTimeReached() {
		return this.timeUtil.hasTimeReached(MathHelper.div(1000, KillAura.this.cps.getObject().doubleValue()));
	}
	
	@EventTarget
	public void onMotionUpdate(MotionUpdateEvent event) {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		switch (event.getState()) {
			case PRE: {
				synchronized (this.getLoaedEntityList()) {
					for (Entity entity : this.getLoaedEntityList()) {
						if (!(entity instanceof EntityLivingBase)) {
							continue;
						}
						
						try {
							if (entity.getEntityId() == -123) {
								continue;
							}
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							if (!(entity instanceof EntityPlayer) && !this.canAttackMob.getObject().booleanValue()) {
								continue;
							}
							
							EntityLivingBase Living = (EntityLivingBase) entity;
							
							if (!this.isValid(Living)) {
								continue;
							}
							
							if (Living.getName().length() > 16) {
								continue;
							}

							this.targets.add(Living);
						}
					}
				}
				
				if (this.hasTimeReached()) {
					if (this.sort() && !this.targets.isEmpty()) {
						for (int i = 0; i < this.attackTargetNumber.getObject().intValue(); i++) {
							synchronized (this.targets) {
								if (this.targets.size() == 0) {
									return;
								}

								if (this.targets.size() < i + 1) {
									return;
								}

								EntityLivingBase living = this.targets.get(i);

								if (!this.isValid(living)) {
									return;
								}
								
								float[] facing = RotationUtils.getRotations(living);
								this.mc.thePlayer.rotationYaw = (float) facing[0];
								this.mc.thePlayer.rotationPitch = (float) facing[1];
								this.attackEntity(living);
								logger.info(living.getName());
							}
						}
						
						this.targets.clear();
						this.timeUtil.setLastMS();
					}
				}
			};
			
			default: {
				;
			};
		};
	}

	public strictfp boolean isValid(EntityLivingBase Living) {
		return !Living.equals(this.getPlayer()) && (Living.canAttackWithItem() | Living instanceof EntityPlayer) && Living.isEntityAlive() && !this.isLong(Living);
	}

	public strictfp boolean sort() {
		Collections.sort(this.targets, new Comparator<EntityLivingBase>() {

			public int compare(EntityLivingBase o1, EntityLivingBase o2) {
				return Float.valueOf(KillAura.this.mc.thePlayer.getDistanceToEntity(o1)).compareTo(Float.valueOf(KillAura.this.mc.thePlayer.getDistanceToEntity(o2)));
			}
		});
		
		return true;
	}

	public strictfp boolean isLong(EntityLivingBase Living) {
		double range = this.range.getValue();
		return this.mc.thePlayer.getDistanceToEntity(Living) >= range;
	}
	
    public static float[] getNeededRotations(Vec3 vec) {
    	final Vec3 eyesPos = new Vec3(Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight(), Minecraft.getMinecraft().thePlayer.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = Float.parseFloat(String.format("%.2f", (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f));
        final float pitch = Float.parseFloat(String.format("%.2f", (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
        return new float[]{ MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }
    
    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * Math.random(), bb.minY + (bb.maxY - bb.minY) * 0.8 * Math.random(), bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * Math.random());
    }
    
    public static Vec3 getRandomCenter_static(AxisAlignedBB bb) {
    	return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * 0.8, bb.minY + (bb.maxY - bb.minY) * 1 * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * 0.8);
    }

	public strictfp void attackEntity(EntityLivingBase Living) {
		int i = 0;

		CancellableEvent event = new AttackEntityEvent(this.mc, Living, this.mc.thePlayer);
		EventManager.callEvent(event);
		
		if (event.isCancelled()) {
			return;
		}
		
		if (!this.isValid(Living)) {
			return;
		}
		
		this.toggleSword();
		this.autoBlock();
		super.attackEntity(Living);
		Living.hurtTime = this.hurtTime.getValue();
		
		if (this.mc.thePlayer.isSprinting()) {
			++i;
		}
		
	    Living.addVelocity(-MathHelper.sin((float) (this.mc.thePlayer.rotationYaw * Math.PI / 180.0F)) * i * 0.5F, 0.1D, MathHelper.cos((float) (this.mc.thePlayer.rotationYaw * Math.PI / 180.0F)) * i * 0.5F);
	    this.mc.thePlayer.onCriticalHit(Living);
		this.autoBlock();
	}
	
	public void autoBlock() {
		if (this.autoBlock.getValue()) {
			ItemStack itemstack1 = null;
			
			try {
				itemstack1 = this.mc.thePlayer.inventory.getCurrentItem();
			} catch (Exception e) {
				if (e instanceof NullPointerException) {
					;
				} else {
					e.printStackTrace();
				}
			}
			
			try {
				if (itemstack1 != null) {
					if (itemstack1.getItem() instanceof ItemSword) {
						 this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, itemstack1);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void toggleSword() {
		ItemSword lastItem = null;
		
		for (int i = 0; i < 9; i++) {
			ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
			
			if (stack == null) {
				continue;
			}
			
			if (stack.getItem() == null) {
				continue;
			}
			
			if (stack.getItem() instanceof ItemSword) {
				ItemSword currentItem = (ItemSword) stack.getItem();
				
				if (lastItem == null) {
					lastItem = currentItem;
					Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
				} else {
					if (lastItem.getDamageVsEntity() > currentItem.getDamageVsEntity()) {
						continue;
					} else {
						lastItem = currentItem;
						Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
					}
				}
			}
		}
	}

	public static KillAura getKillAura() {
		return KillAura.killaura;
	}

	public static void setKillAura(KillAura killaura) {
		KillAura.killaura = killaura;
	}
	
	public double getRange() {
		return this.getRange();
	}
}