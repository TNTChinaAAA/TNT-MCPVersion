package net.tntchina.client.module.modules.player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntFunction;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.module.modules.combat.KillAura;
import optifine.Reflector;

public class AutoBlock extends Module {

	public AutoBlock(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public Vec3 getAxisAlignedBB(Entity entity) {
        double d0 = (double)this.mc.playerController.getBlockReachDistance();
        MovingObjectPosition objectMouseOver = entity.rayTrace(d0, 10);
        double d1 = d0;
		Vec3 vec3 = entity.getPositionEyes(10);
		boolean flag = false;
		int i = 3;

		if (this.mc.playerController.extendedReach()) {
			d0 = 6.0D;
			d1 = 6.0D;
		} else {
			if (d0 > 3.0D) {
				flag = true;
			}
		}

		if (objectMouseOver != null) {
			d1 = objectMouseOver.hitVec.distanceTo(vec3);
		}

		double d2 = d1;
		Vec3 vec31 = entity.getLook(10);
		Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
		Vec3 vec33 = null;
		float f = 1.0F;

		Predicate<Entity> predicate = Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
			public boolean apply(Entity p_apply_1_) {
				return p_apply_1_.canBeCollidedWith();
			}
		});
		
		List<Entity> list = this.mc.theWorld.getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0) .expand((double) f, (double) f, (double) f), predicate);
		Entity entity2 = null;	
		
		for (int j = 0; j < list.size(); ++j) {
			Entity entity1 = (Entity) list.get(j);
			float f1 = entity1.getCollisionBorderSize();
			AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expand((double) f1, (double) f1 + 1.0, (double) f1);
			MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

			if (axisalignedbb.isVecInside(vec3)) {
				if (d2 >= 0.0D) {
					entity2 = entity1;
					vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
					d2 = 0.0D;
				}
			} else if (movingobjectposition != null) {
				double d3 = vec3.distanceTo(movingobjectposition.hitVec);

				if (d3 < d2 || d2 == 0.0D) {
				    boolean flag2 = false;

                    if (Reflector.ForgeEntity_canRiderInteract.exists())
                    {
                        flag2 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                    }
                    
					if (entity1 == entity.ridingEntity && !flag2) {
						if (d2 == 0.0D) {
							entity2 = entity1;
							vec33 = movingobjectposition.hitVec;
						}
					} else {
						entity2 = entity1;
						vec33 = movingobjectposition.hitVec;
						d2 = d3;
					}
				}
			}
		}

		return vec33;
	}
	
	public strictfp boolean isLong(EntityLivingBase Living) {
		return this.mc.thePlayer.getDistanceToEntity(Living) >= KillAura.getKillAura().getRange();
	}
	
	public strictfp boolean isValid(Entity Living) {
		if (!(Living instanceof EntityLivingBase)) {
			return false;
		}
		
		return !Living.equals(this.getPlayer()) && (Living.canAttackWithItem() | Living instanceof EntityPlayer) && Living.isEntityAlive() && !this.isLong((EntityLivingBase) Living);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null) {
			return;
		}
		
		List<Entity> loaedEntries = new ArrayList<Entity>(this.mc.theWorld.getLoadedEntityList());
		List<Entity> entries = new ArrayList<Entity>();
		
		for (Entity entity : loaedEntries) {
			if (!this.isValid(entity)) {
				
			}
		}
	}
}