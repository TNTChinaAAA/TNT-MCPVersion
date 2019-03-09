package net.tntchina.client.module.modules.combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.tntchina.client.event.EventManager;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.AttackEntityEvent;
import net.tntchina.client.event.events.CancellableEvent;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.event.events.MovedWronglyEvent;
import net.tntchina.client.module.*;
import net.tntchina.client.util.BlockPosUtil;
import net.tntchina.client.util.MathHelper;
import net.tntchina.client.value.values.BooleanValue;
import net.tntchina.client.value.values.NumberValue;

public class TPAura extends Module {

	private volatile List<EntityLivingBase> targets = new ArrayList<EntityLivingBase>();
	private NumberValue cps = new NumberValue(10, "CPS", 20, 1);
	private NumberValue range = new NumberValue(51.0, "Range", 100.0, 50.0);
	private BooleanValue canAttackMob = new BooleanValue(true, "AttackMob");
	private BooleanValue autoBlock = new BooleanValue(false, "AutoBlock");
	
	public TPAura(String name, int key, ModuleCategory Categorys, String description) {
		super(name, key, Categorys, description);
	}

	public TPAura(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public TPAura(String name, ModuleCategory categorys) {
		super(name, categorys);
	}

	public TPAura(String name) {
		super(name);
	}

	public boolean hasTimeReached() {
		return this.timeUtil.hasTimeReached(MathHelper.div(1000, this.cps.getObject().doubleValue()));
	}
	
	@EventTarget
	public void onErrorMove(MovedWronglyEvent event) {
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
			};
			
			case POST: {
				if (this.hasTimeReached()) {
					if (this.sort() && !this.targets.isEmpty()) {
						EntityLivingBase living = this.getTarget();
						
						if (living != null) {
							BlockPosUtil pos = new BlockPosUtil(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ);
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(living.posX, living.posY, living.posZ, true));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
							this.mc.thePlayer.fallDistance = 0.0F;
							this.mc.thePlayer.onGround = true;
							float[] facing = TPAura.getNeededRotations(KillAura.getRandomCenter(living.boundingBox), this.mc.thePlayer);
							event.setYaw(facing[0]);
							event.setPitch(facing[1]);
							float yawhead = this.mc.thePlayer.rotationYawHead;
							this.mc.thePlayer.setRotationYawHead(facing[0]);
							this.attackEntity(living);
							this.mc.thePlayer.setRotationYawHead(yawhead);
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(pos.getX(), pos.getY(), pos.getZ(), true));
							this.mc.getNetHandler().addToSendQueue(new C03PacketPlayer(true));
						}
					}
	
					this.targets.clear();
					this.timeUtil.setLastMS();
				}
			};
			
			default: {
				;
			};
		};
	}
	
	public void tpToEntity(Entity e) {

	}
	
	public EntityLivingBase getTarget() {
		synchronized (this.targets) {
			if (this.targets.isEmpty()) {
				return null;
			}
			
			EntityLivingBase living = this.targets.get(0);
			
			if (!this.isValid(living)) {
				this.targets.remove(0);
				this.getTarget();
			}
			
			return living;
		}
	}
	
	public strictfp void setPostion(BlockPos pos) {
		this.mc.thePlayer.setPosition(pos.getX(), pos.getY(), pos.getZ());
	}
	
	public strictfp void setPostion(double x, double y, double z) {
		this.mc.thePlayer.setPosition(x, y, z);
	}

	public strictfp boolean isValid(EntityLivingBase Living) {
		return !Living.equals(this.mc.thePlayer) && !Living.equals(this.mc.thePlayer) && (Living.canAttackWithItem() | Living instanceof EntityPlayer) && Living.isEntityAlive() && !this.isLong(Living);
	}

	public strictfp boolean sort() {
		Collections.sort(this.targets, new Comparator<EntityLivingBase>() {

			public int compare(EntityLivingBase o1, EntityLivingBase o2) {
				return Float.valueOf(TPAura.this.mc.thePlayer.getDistanceToEntity(o1)).compareTo(Float.valueOf(TPAura.this.mc.thePlayer.getDistanceToEntity(o2)));
			}
		});
		
		return true;
	}

	public strictfp boolean isLong(EntityLivingBase Living) {
		double range = this.range.getValue();
		return this.mc.thePlayer.getDistanceToEntity(Living) >= range;
	}
	
    public static float[] getNeededRotations(Vec3 vec, EntityPlayerSP thePlayer) {
        final Vec3 eyesPos = new Vec3(thePlayer.posX, thePlayer.posY + thePlayer.getEyeHeight(), thePlayer.posZ);
        final double diffX = vec.xCoord - eyesPos.xCoord;
        final double diffY = vec.yCoord - eyesPos.yCoord;
        final double diffZ = vec.zCoord - eyesPos.zCoord;
        final double diffXZ = Math.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = Float.parseFloat(String.format("%.2f", (float) Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0f));
        final float pitch = Float.parseFloat(String.format("%.2f", (float) (-Math.toDegrees(Math.atan2(diffY, diffXZ)))));
        return new float[]{ MathHelper.wrapAngleTo180_float(yaw), MathHelper.wrapAngleTo180_float(pitch) };
    }
    
    public static Vec3 getRandomCenter(AxisAlignedBB bb) {
        return new Vec3(bb.minX + (bb.maxX - bb.minX) * 0.8 * 0.8, bb.minY + (bb.maxY - bb.minY) * 1 * 0.8, bb.minZ + (bb.maxZ - bb.minZ) * 0.8 * 0.8);
    }

	public strictfp void attackEntity(EntityLivingBase Living) {
		this.info("Attack " + ((Living instanceof EntityPlayer) ? "Player" : "Entity") + ": " + Living.getName() + " | posX: " + String.format("%.2f", Living.posX) + " | posY: " + String.format("%.2f", Living.posY) + " | posZ: " + String.format("%.2f", Living.posZ) + " | Health: " + Living.getHealth() + ".");
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
		this.mc.thePlayer.swingItem();
		this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(Living, C02PacketUseEntity.Action.ATTACK));
		this.mc.thePlayer.attackTargetEntityWithCurrentItem(Living);
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

	public double getRange() {
		return this.range.getValue();
	}
}