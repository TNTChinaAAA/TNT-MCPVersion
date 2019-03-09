package net.tntchina.client.module.modules.movement;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovementInput;
import net.tntchina.client.event.EventListener;
import net.tntchina.client.event.EventManager;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.MoveEvent;
import net.tntchina.client.event.events.UpdateEvent;
import net.tntchina.client.event.events.UpdatePreEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.module.modules.world.Timer;
import net.tntchina.client.util.BlockUtils;
import net.tntchina.client.util.PlayerUtils;
import net.tntchina.client.value.values.BooleanValue;
import net.tntchina.client.value.values.ModeValue;
import net.tntchina.client.value.values.NumberValue;
import net.tntchina.inject.IEntityPlayerSP;

public class Speed extends Module {

	public class NCP implements EventListener {
		
		public int stage = 0;
		public double moveSpeed = 0, lastDist = 0;
		public Minecraft mc = Minecraft.getMinecraft();
		
		public NCP() {
			EventManager.registerListener(this);
		}
		
		@EventTarget
		public void onPostUpdate(UpdateEvent event) {
			if (!event.onPost()) {
				return;
			}
			
			double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
			double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
			this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
		}
		
		public void onUpdate() {
			MovementInput movementInput = this.mc.thePlayer.movementInput;
			float forward = movementInput.moveForward;
			float strafe = movementInput.moveStrafe;
			float yaw = this.mc.thePlayer.rotationYaw;
			
			if ((this.stage == 1) && ((this.mc.thePlayer.moveForward != 0.0F) || (this.mc.thePlayer.moveStrafing != 0.0F))) {
				this.stage = 2;
				this.moveSpeed = (1.38D * this.getBaseMoveSpeed() - 0.01D);
			} else if (this.stage == 2) {
				this.stage = 3;
				this.mc.thePlayer.motionY = 0.399399995803833D;
				this.moveSpeed *= 2.149D;
			} else if (this.stage == 3) {
				this.stage = 4;
				double difference = 0.66D * (this.lastDist - this.getBaseMoveSpeed());
				this.moveSpeed = (this.lastDist - difference);
				
				if (forward != 0.0F || (strafe != 0.0F && !this.mc.gameSettings.keyBindJump.pressed)) {
					this.mc.thePlayer.motionY = -0.4;
					this.stage = 2;
				}
			} else {
				if (this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, mc.thePlayer.boundingBox.offset(0.0D, this.mc.thePlayer.motionY, 0.0D)).size() > 0 || mc.thePlayer.isCollidedVertically) {
					this.stage = 1;
				}
				
				this.moveSpeed = (this.lastDist - this.lastDist / 159.0D);
			}

			this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
			
			if ((forward == 0.0F) && (strafe == 0.0F)) {
				this.mc.thePlayer.motionX = 0.0D;
				this.mc.thePlayer.motionZ = 0.0D;
			} else if (forward != 0.0F) {
				if (strafe >= 1.0F) {
					yaw += (forward > 0.0F ? -45 : 45);
					strafe = 0.0F;
				} else if (strafe <= -1.0F) {
					yaw += (forward > 0.0F ? 45 : -45);
					strafe = 0.0F;
				}
				if (forward > 0.0F) {
					forward = 1.0F;
				} else if (forward < 0.0F) {
					forward = -1.0F;
				}
			}
			
			double mx = Math.cos(Math.toRadians(yaw + 90.0F));
			double mz = Math.sin(Math.toRadians(yaw + 90.0F));
			this.mc.thePlayer.motionX = (forward * this.moveSpeed * mx + strafe * this.moveSpeed * mz);
			this.mc.thePlayer.motionZ = (forward * this.moveSpeed * mz - strafe * this.moveSpeed * mx);
		}
		
		private double getBaseMoveSpeed() {
			double baseSpeed = 0.2873D;
			
			if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
				int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
				baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
			}
			
			return baseSpeed;
		}
	}
	
	public ModeValue mode = new ModeValue("NCP", "Mode", Arrays.asList("NCP", "AAC 3.3.11", "Area51", "Sloth", "Janitor", "AGC", "Mineplex", "GuardianYport", "Guardian", "OldGuardian", "OnGround", "HypixelHop", "BHop"));
	public NumberValue speed = new NumberValue(1.1, "Speed", 2.0, 1.0);
	public BooleanValue ice = new BooleanValue(true, "Ice");
	public double moveSpeedVanilla = 0.0;
	public int stage = 0;
	public double moveSpeed = 0.0;
	public double lastDist = 0.0;
	public double movementSpeed = 0.0;
	public double distance = 0.0;
	public NCP ncp = new NCP();
	
	public Speed(String name, int key, ModuleCategory Categorys) {
		super(name, key, Categorys);
	}
	
	@Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
       
        if (this.mode.getValue().equalsIgnoreCase("Area51")) {
            this.mc.thePlayer.motionX = 0.0;
            this.mc.thePlayer.motionZ = 0.0;
        }
        
    	this.onNCPDisable();
		this.onAAC_3_3_11Disable();
    }

    private boolean canZoom() {
    	IEntityPlayerSP thePlayer = (IEntityPlayerSP) this.mc.thePlayer;
    	
    	if (thePlayer.moving() && this.mc.thePlayer.onGround) {
            return true;
        }
    	
        return false;
    }

    @EventTarget
    private void onUpdate(UpdatePreEvent e) {
    	if (!this.getState()) {
    		return;
    	}
    	
    	if (this.mode.getValue().equalsIgnoreCase("Sloth") && this.canZoom()) {
    		if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                boolean under = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.getEntityBoundingBox().offset(this.mc.thePlayer.motionX, 1.6, this.mc.thePlayer.motionZ)).isEmpty();
                
                if (this.mc.thePlayer.ticksExisted % 2 != 0 && under) {
                    this.mc.thePlayer.motionY += 0.42;
                }
                
                this.mc.thePlayer.motionY = -10.0;
                
                if (this.mc.thePlayer.onGround) {
                    ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(((IEntityPlayerSP) this.mc.thePlayer).getSpeed() * (this.mc.thePlayer.ticksExisted % 2 == 0 ? 4.0f : 0.28f));
                }
            }
        } else if (this.mode.getValue().equalsIgnoreCase("OnGround")&& this.canZoom()) {
            switch (this.stage) {
                case 1: {
                    this.mc.thePlayer.motionY += 0.4;
                    this.mc.thePlayer.onGround = false;
                    ++this.stage;
					break;
				}

				case 2: {
	
					this.mc.thePlayer.onGround = false;
					++this.stage;
					break;
				}
	
				default: {
					this.stage = 1;
					break;
				}
			}
        } else if (this.mode.getValue().equalsIgnoreCase("Janitor") || !this.canZoom()) {
            if (this.mode.getValue().equalsIgnoreCase("AGC")) {
                double speed = 0.2;
                double x = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
                double z = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
                double n = (double)this.mc.thePlayer.movementInput.moveForward * speed * x;
                double xOff = n + (double)this.mc.thePlayer.movementInput.moveStrafe * speed * z;
                double n2 = (double)this.mc.thePlayer.movementInput.moveForward * speed * z;
                double zOff = n2 - (double)(this.mc.thePlayer.movementInput.moveStrafe * 0.5f) * x;
                this.mc.thePlayer.setAIMoveSpeed(this.mc.thePlayer.getAIMoveSpeed());
                
                if (this.mc.thePlayer.onGround) {
                    if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                        this.mc.thePlayer.motionY = 0.2;
                    }
                } else if (this.mc.thePlayer.motionY <= -0.10000000149011612) {
                    double cock = 10.0;
                    this.mc.thePlayer.setPosition(this.mc.thePlayer.posX + xOff * cock, this.mc.thePlayer.posY, this.mc.thePlayer.posZ + zOff * cock);
                    this.mc.thePlayer.motionY -= 0.0010000000474974513;
                }
            } else if (this.mode.getValue().equalsIgnoreCase("OldGuardian") && this.mode.getValue().equalsIgnoreCase("GuardianYport")) {
                double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
                double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
                this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
            } else if (this.mode.getValue().equalsIgnoreCase("GuardianYport")) {
                this.mc.timer.timerSpeed = ((IEntityPlayerSP) this.mc.thePlayer).moving() ? 1.6f : 1.0f;
                if (((IEntityPlayerSP) this.mc.thePlayer).moving() && this.mc.thePlayer.onGround) {
                    this.mc.thePlayer.motionY = 0.12;
                    this.mc.thePlayer.motionY -= 0.04;
                    this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-9, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
                    ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(0.7);
                } else {
                    ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
                }
            } else if (((IEntityPlayerSP) this.mc.thePlayer).moving() && this.mc.thePlayer.onGround) {
                this.mc.thePlayer.motionY = 0.4;
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-9, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
                ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(1.75);
            } else {
                ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
            }
        }
    }

    @EventTarget
    private void onMove(MoveEvent e) {
        if (!this.getState()) {
        	return;
        }
    	
    	if (this.mode.getValue().equalsIgnoreCase("HypixelHop")) {
            if (this.canZoom() && this.stage == 1) {
                this.movementSpeed = 1.56 * PlayerUtils.getBaseMoveSpeed() - 0.01;
                this.mc.timer.timerSpeed = 1.15f;
            } else if (this.canZoom() && this.stage == 2) {
                this.mc.thePlayer.motionY = 0.3999;
                this.movementSpeed *= 1.58;
                this.mc.timer.timerSpeed = 1.2f;
            } else if (this.stage == 3) {
                double difference = 0.66 * (this.distance - PlayerUtils.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
                this.mc.timer.timerSpeed = 1.1f;
            } else {
                List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
                
                if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically && this.stage > 0) {
                    this.stage = ((IEntityPlayerSP) this.mc.thePlayer).moving() ? 1 : 0;
                }
                
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            
            this.movementSpeed = Math.max(this.movementSpeed, PlayerUtils.getBaseMovementSpeed());
            ((IEntityPlayerSP) this.mc.thePlayer).setMoveSpeed(this.movementSpeed);
            
            if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                ++this.stage;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("Area51")) {
            if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
				if (this.mc.thePlayer.motionY <= 0.0) {
					this.mc.thePlayer.motionY *= 1.5;
				}
				
				this.mc.thePlayer.onGround = true;
				this.mc.timer.timerSpeed = 0.33f;
                ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(4.0);
            } else {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("OnGround") && this.canZoom()) {
            switch (this.stage) {
				case 1: {
					this.mc.timer.timerSpeed = 1.22f;
					this.movementSpeed = 1.89 * PlayerUtils.getBaseMovementSpeed() - 0.01;
					this.distance += 1.0;
					
					if (this.distance == 1.0) {
						this.mc.thePlayer.motionY += 8.0E-6;
						break;
					}
					
					if (this.distance != 2.0) {
						break;
					}
					
					this.mc.thePlayer.motionY -= 8.0E-6;
					this.distance = 0.0;
					break;
				}
				
				case 2: {
					this.movementSpeed = 1.2 * PlayerUtils.getBaseMovementSpeed() - 0.01;
					break;
				}
				
				default: {
					this.movementSpeed = (float) PlayerUtils.getBaseMovementSpeed();
				}
            }
            
            this.movementSpeed = Math.max(this.movementSpeed, PlayerUtils.getBaseMovementSpeed());
            ((IEntityPlayerSP) this.mc.thePlayer).setMoveSpeed(this.movementSpeed);
            ++this.stage;
        } else if (this.mode.getValue().equalsIgnoreCase("Janitor") && this.canZoom()) {
            ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(this.mc.thePlayer.ticksExisted % 2 != 0 ? 0 : 2);
        } else if (this.mode.getValue().equalsIgnoreCase("Mineplex")) {
            this.mc.timer.timerSpeed = 1.1f;
            if (this.canZoom() && this.stage == 1) {
                this.movementSpeed = 0.58;
            } else if (this.canZoom() && this.stage == 2) {
                this.mc.thePlayer.motionY = 0.3;
                this.movementSpeed = 0.64;
            } else if (this.stage == 3) {
                double difference = 0.66 * (this.distance - PlayerUtils.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
            } else {
                List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
                
                if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically && this.stage > 0) {
                    this.stage = ((IEntityPlayerSP) this.mc.thePlayer).moving() ? 1 : 0;
                }
                
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, PlayerUtils.getBaseMovementSpeed());
            ((IEntityPlayerSP) this.mc.thePlayer).setMoveSpeed(this.movementSpeed);
            
            if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                ++this.stage;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("BHop")) {
            this.mc.timer.timerSpeed = 1.07f;
            
            if (this.canZoom() && this.stage == 1) {
                this.movementSpeed = 2.55 * PlayerUtils.getBaseMovementSpeed() - 0.01;
            } else if (this.canZoom() && this.stage == 2) {
                this.mc.thePlayer.motionY = 0.3999;
                this.movementSpeed *= 2.1;
            } else if (this.stage == 3) {
                double difference = 0.66 * (this.distance - PlayerUtils.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
            } else {
                List collidingList = this.mc.theWorld.getCollidingBoundingBoxes(this.mc.thePlayer, this.mc.thePlayer.boundingBox.offset(0.0, this.mc.thePlayer.motionY, 0.0));
                
                if (collidingList.size() > 0 || this.mc.thePlayer.isCollidedVertically && this.stage > 0) {
                    this.stage = ((IEntityPlayerSP) this.mc.thePlayer).moving() ? 1 : 0;
                }
                
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            
            this.movementSpeed = Math.max(this.movementSpeed, PlayerUtils.getBaseMovementSpeed());
            ((IEntityPlayerSP) this.mc.thePlayer).setMoveSpeed(this.movementSpeed);
            
            if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                ++this.stage;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("Guardian")) {
            if (((IEntityPlayerSP) this.mc.thePlayer).moving()) {
                if (this.mc.thePlayer.onGround) {
                    int i = 0;
                    while (i < 20) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-9, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
                        ++i;
                    }
                    ((IEntityPlayerSP) this.mc.thePlayer).setSpeed(1.399999976158142);
                    this.mc.thePlayer.motionY = 0.4;
                } else {
                    ((IEntityPlayerSP) this.mc.thePlayer).setSpeed((float)Math.sqrt(this.mc.thePlayer.motionX * this.mc.thePlayer.motionX + this.mc.thePlayer.motionZ * this.mc.thePlayer.motionZ));
                }
            } else {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
            }
        }
    }

	public boolean hasMode() {
		return true;
	}
	
	public String getMode() {
		return this.mode.getValue();
	}
	
	public void onUpdate() {
		if (this.getState()) {
			if (this.ice.getObject().booleanValue()) {
				if (BlockUtils.getBlockUnderPlayer(this.mc.thePlayer, 0.001) instanceof BlockIce || BlockUtils.getBlockUnderPlayer(this.mc.thePlayer, 0.001) instanceof BlockPackedIce) {
					Blocks.ice.slipperiness = 0.39F;
					Blocks.packed_ice.slipperiness = 0.39F;
					return;
				}
			} else {
				Blocks.ice.slipperiness = 0.98F;
				Blocks.packed_ice.slipperiness = 0.98F;
			}
			
			if (this.isModeEqual("NCP")) {
				this.onNCP();
			}
			
			if (this.isModeEqual("AAC 3.3.11")) {
				this.onAAC_3_3_11();
			}
		}
	}
	
	@EventTarget
	public void onPostUpdate(UpdateEvent event) {
		if (!this.getState() | event.getState() != UpdateEvent.State.POST) {
			return;
		}
		
		double xDist = this.mc.thePlayer.posX - this.mc.thePlayer.prevPosX;
		double zDist = this.mc.thePlayer.posZ - this.mc.thePlayer.prevPosZ;
		this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
	}
	
	public void onNCP() {
		if (!this.getState() || this.mc.thePlayer.isInWater() || this.mc.thePlayer.isInLava()) {
			return;
		}
		
		this.ncp.onUpdate();
	}
	
	private double getBaseMoveSpeed() {
		double baseSpeed = 0.2873D;
		
		if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
			int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
			baseSpeed *= (1.0D + 0.2D * (amplifier + 1));
		}
		
		return baseSpeed;
	}
	
	public void onNCPDisable() {
		;
	}
	
	public void onAAC_3_3_11() {
		if (this.mc.thePlayer.moveForward > 0.0) {
			if (this.mc.thePlayer.onGround) {
				this.mc.thePlayer.jump();
			} else {
				float d = this.getDirection();
				double speed = this.speed.getValue() - 0.65;
				this.mc.thePlayer.motionX = -Math.sin(d) * speed;
				this.mc.thePlayer.motionZ = Math.cos(d) * speed;
			}
		}
	}
	
	public void onAAC_3_3_11Disable() {
		this.mc.timer.timerSpeed = 1.0F;
	}
	
	private boolean isModeEqual(String mode) {
		return this.mode.getValue().equalsIgnoreCase(mode);
	}
}