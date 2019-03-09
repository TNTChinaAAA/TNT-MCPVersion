package net.tntchina.client.module.modules.world;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;

public class FastBreak extends Module {

	public FastBreak(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}

	public void onUpdate() {
		if (!this.getState() | this.mc.thePlayer == null | this.mc.theWorld == null) {
			return;
		}
		
		if (!this.mc.gameSettings.keyBindAttack.isPressed()) {
			return;
		}
		
		for (int i = 0; i < 50; i++) {
			this.breakBlock();
		}
	}
	
	public void breakBlock() {
		switch (this.mc.objectMouseOver.typeOfHit) {
		    case BLOCK: {
		        BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
		
		        if (this.mc.theWorld.getBlockState(blockpos).getBlock().getMaterial() != Material.air) {
		            this.mc.playerController.clickBlock(blockpos, this.mc.objectMouseOver.sideHit);
		            break;
		        }
		    };
		    
		    default: {
		    	break;
		    }
		}
	}
}
