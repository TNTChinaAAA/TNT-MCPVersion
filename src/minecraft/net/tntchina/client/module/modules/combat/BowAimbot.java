package net.tntchina.client.module.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.util.RotationUtils;

public class BowAimbot extends Module {

	public BowAimbot(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
    @EventTarget
    public void onEvent(MotionUpdateEvent event) {
    	if (this.getState() && event.onPre()) {
    		EntityLivingBase target = this.getTarg();
            
            if (Minecraft.getMinecraft().thePlayer.isUsingItem() && Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && target != Minecraft.getMinecraft().thePlayer && target != null) {
                float[] rotations = RotationUtils.getBowAngles(target);
                event.setYaw(rotations[0]);
                event.setPitch(rotations[1]);
            }
        }
    }

    private EntityLivingBase getTarg() {
        ArrayList<EntityLivingBase> loaded = new ArrayList<EntityLivingBase>();
        for (Object o : Minecraft.getMinecraft().theWorld.getLoadedEntityList()) {
            EntityLivingBase ent;
            
            if (!(o instanceof EntityLivingBase) || !((ent = (EntityLivingBase)o) instanceof EntityPlayer) || !Minecraft.getMinecraft().thePlayer.canEntityBeSeen(ent) || Minecraft.getMinecraft().thePlayer.getDistanceToEntity(ent) >= 15.0f) continue;
            
            loaded.add(ent);
        }
        
        if (loaded.isEmpty()) {
            return null;
        }
        
        loaded.sort(new Comparator<EntityLivingBase>() {

        	public int compare(EntityLivingBase o1, EntityLivingBase o2) {
			    float[] rot1 = RotationUtils.getRotations(o1);
			    float[] rot2 = RotationUtils.getRotations(o2);
			    return (int)(RotationUtils.getDistanceBetweenAngles(Minecraft.getMinecraft().thePlayer.rotationYaw, rot1[0]) + RotationUtils.getDistanceBetweenAngles(Minecraft.getMinecraft().thePlayer.rotationPitch, rot1[1]) - (RotationUtils.getDistanceBetweenAngles(Minecraft.getMinecraft().thePlayer.rotationYaw, rot2[0]) + RotationUtils.getDistanceBetweenAngles(Minecraft.getMinecraft().thePlayer.rotationPitch, rot2[1])));
			}
		});
        
        EntityLivingBase target = (EntityLivingBase) loaded.get(0);
        return target;
    }
}
