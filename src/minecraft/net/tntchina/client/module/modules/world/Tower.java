package net.tntchina.client.module.modules.world;

import org.lwjgl.input.Keyboard;

import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.event.events.SafeWalkEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.util.BlockUtil;
import net.tntchina.client.util.TimeHelper;
import net.tntchina.client.value.values.NumberValue;

public class Tower extends Module {

	private NumberValue delay = new NumberValue(20, "Delay", 1000, 1);
    private TimeHelper timer = new TimeHelper();
    private BlockPos currentPos;
    private EnumFacing currentFacing;
    private boolean rotated = false;
    
	public Tower(String name, ModuleCategory Categorys) {
		super(name, 24, Categorys);
	}

	@EventTarget
	public void onSafe(SafeWalkEvent event) {
		if (this.getState() && !ModuleManager.getModuleState(Scaffold.class)) {
			event.setSafe(this.rotated);
		}
	}
	
	private void setBlockAndFacing(BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            this.currentFacing = EnumFacing.UP;
        } else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            this.currentFacing = EnumFacing.EAST;
        } else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            this.currentFacing = EnumFacing.WEST;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            this.currentFacing = EnumFacing.SOUTH;
        } else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            this.currentFacing = EnumFacing.NORTH;
        } else {
        	this.currentPos = null;
        	this.currentFacing = null;
        }
    }
	
	@EventTarget
	public void onMotionUpdate(MotionUpdateEvent e) {
		if (this.getState() && this.mc.thePlayer != null && this.mc.theWorld != null) {
			if (e.getState() == MotionUpdateEvent.State.PRE) {
				this.rotated = false;
				this.currentPos = null;
				this.currentFacing = null;
				
				if (this.mc.thePlayer.onGround && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
					this.mc.gameSettings.keyBindJump.pressed = true;
				} else {
					if (this.getCurrentKey() < 256 && Keyboard.getEventKeyState()) {
						this.mc.gameSettings.keyBindJump.pressed = Keyboard.isKeyDown(57);
					}
				}
				
				BlockPos pos = new BlockPos(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0D, this.mc.thePlayer.posZ);
	            
	            if (this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir) {
	                this.setBlockAndFacing(pos);

	                if (this.currentPos != null) {
	                    float facing[] = BlockUtil.getDirectionToBlock(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ(), this.currentFacing);
	                    float yaw = facing[0];
	                    float pitch = Math.min(90, facing[1] + 9);
	                    this.rotated = true;
	                    this.mc.thePlayer.rotationYaw = yaw;
	                    this.mc.thePlayer.rotationPitch = pitch;
	                }
	            }
	            
	            if (this.currentPos != null) {
	            	if (this.timer.hasTimeReached(this.delay.getObject())) {
	            		if (this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBlock) {
	            			if (this.mc.playerController.onPlayerRightClick(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getCurrentEquippedItem(), this.currentPos, this.currentFacing, new Vec3(this.currentPos.getX(), this.currentPos.getY(), this.currentPos.getZ()))) {
	            				this.mc.thePlayer.swingItem();
	                        	this.timer.setLastMS();
	                        }
	                    }
	                }
	            }
	        }
		}
	}
}