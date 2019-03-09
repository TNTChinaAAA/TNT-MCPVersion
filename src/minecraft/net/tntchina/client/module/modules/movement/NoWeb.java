package net.tntchina.client.module.modules.movement;

import net.minecraft.block.BlockWeb;
import net.minecraft.util.AxisAlignedBB;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.BlockBBEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class NoWeb extends Module {

	public NoWeb(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (this.getState()) {
			if (this.mc.thePlayer.isInWeb) {
				this.mc.thePlayer.onGround = false;
				this.mc.thePlayer.isInWeb = false;
			}
		}
	}
	
	@EventTarget
	public void onBB(BlockBBEvent event) {
		if (this.getState() && event.getBlock() instanceof BlockWeb) {
			event.setAxisAlignedBB(new AxisAlignedBB(event.getPos(), event.getPos().add(1, 0.75, 1)));
		}
	}
}
