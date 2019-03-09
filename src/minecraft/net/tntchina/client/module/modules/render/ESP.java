package net.tntchina.client.module.modules.render;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.module.modules.movement.Blink;
import net.tntchina.client.util.RenderUtil;

public class ESP extends Module {

	private static ESP esp;
	
	public ESP(String name, ModuleCategory category) {
		super(name, category);
		ESP.esp = this;
	}
	
	public void onRender() {
		if (this.getState()) {
			for (Entity entity : this.mc.theWorld.entityList) {
				if (entity.equals(this.mc.thePlayer)) {
					continue;
				}
				
				if (entity instanceof EntityLivingBase) {
					Blink blink = ModuleManager.getModule(Blink.class);
					
					if (blink.getState()) {
						if (entity.equals(blink.oldPlayer)) {
							continue;
						}
					}
					
					RenderUtil.entityESPBox(entity, 0);
				}
			}
		}
	}
	
	public static ESP getInstance() {
		return ESP.esp;
	}
}
