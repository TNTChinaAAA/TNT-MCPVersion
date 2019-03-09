package net.tntchina.client.module.modules.combat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class AimBot extends Module {
	
	public AimBot(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public static void aimBots() {
		synchronized (Minecraft.getMinecraft().theWorld.entityList) {
			List<Entity> entities = new ArrayList<Entity>(Minecraft.getMinecraft().theWorld.entityList);
			
			for (Entity entity : entities) {
				if (entity instanceof EntityLivingBase) {
					if (entity.equals(Minecraft.getMinecraft().thePlayer)) {
						continue;
					}
					
					if (Minecraft.getMinecraft().theWorld.loadedEntityList.contains(entity)) {
						continue;
					}
					
					Minecraft.getMinecraft().theWorld.entityList.remove(entity);
					entities = new ArrayList<Entity>(Minecraft.getMinecraft().theWorld.entityList);
					continue;
				}
			}
		}
	}

	public void onUpdate() {
		if (!this.getState()) {
			return;
		}
		
		AimBot.aimBots();
	}
}
