package net.tntchina.client.module.modules.combat;

import java.util.Arrays;
import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.MotionUpdateEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.util.Utils;
import net.tntchina.client.value.values.BooleanValue;
import net.tntchina.client.value.values.ModeValue;
import net.tntchina.client.value.values.NumberValue;

public class Triggerbot extends Module {

	private int[] nums = { 1, 2, 3, 4, 5, 6, 7 };
	private int delay = 0;
    private final Utils util = new Utils();
	public static ModeValue delayMode = new ModeValue("Normal", "Delay Mode", Arrays.asList("Normal", "Random"));
    public static ModeValue triggerMode = new ModeValue("BoundingBox", "Trigger Mode", Arrays.asList("BoundingBox", "FOV"));
    public static NumberValue attackDelay = new NumberValue(3.0, "AttackDelay", 0.0, 6.0);
    public static NumberValue FOV = new NumberValue(20.0, "FOV", 1.0, 90.0);
    public static BooleanValue mobs = new BooleanValue(false, "Mobs");
    public static BooleanValue players = new BooleanValue(true, "Players");
    public static BooleanValue animals = new BooleanValue(false, "Animals");
    public static BooleanValue invisibles = new BooleanValue(false, "Invisibles");
	
	public Triggerbot(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
    public int getRandom(final int[] array) {
        final int rnd = new Random().nextInt(array.length);
        return array[rnd];
    }
	
	@EventTarget
    public void preTick(MotionUpdateEvent event) {
		if (!this.getState() | event.getState() != MotionUpdateEvent.State.POST) {
			return;
		}
		
        if (this.getState()) {
            if (Triggerbot.delayMode.getObject().equalsIgnoreCase("Normal")) {
                if (Triggerbot.triggerMode.getObject().equalsIgnoreCase("BoundingBox")) {
                    final boolean isValidEntity = (Triggerbot.mobs.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityMob && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityAnimal) && !(this.mc.objectMouseOver.entityHit instanceof EntityPlayer)) || (Triggerbot.animals.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityAnimal && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityMob) && !(this.mc.objectMouseOver.entityHit instanceof EntityPlayer)) || (Triggerbot.players.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityPlayer && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityAnimal) && !(this.mc.objectMouseOver.entityHit instanceof EntityMob) /*&& !FriendManager.isFriend(this.mc.objectMouseOver.entityHit.getName()))*/ || (Triggerbot.invisibles.getBooleanValue() && this.mc.objectMouseOver.entityHit.isInvisible() /*&& !FriendManager.isFriend(this.mc.objectMouseOver.entityHit.getName())*/));
                    
                    if (this.mc.objectMouseOver.entityHit != null && isValidEntity && this.util.shouldHitEntity(this.mc.objectMouseOver.entityHit, 4.0, 360.0f, true, 0, Triggerbot.invisibles.getBooleanValue() ? 1 : 0, Triggerbot.players.getBooleanValue() ? 1 : 0, Triggerbot.mobs.getBooleanValue() ? 1 : 0, Triggerbot.animals.getBooleanValue() ? 1 : 0)) {
                        ++this.delay;
                        
                        if (this.delay >= Triggerbot.attackDelay.getValue()) {
                            this.mc.thePlayer.swingItem();
                            this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.objectMouseOver.entityHit);
                            this.delay = 0;
                        }
                    }
                }
                
                if (Triggerbot.triggerMode.getObject().equalsIgnoreCase("FOV")) {
                    final Entity entity = this.util.getBestEntity(4.0, (float) Triggerbot.FOV.getValue(), true, 0, Triggerbot.invisibles.getBooleanValue() ? 1 : 0, Triggerbot.players.getBooleanValue() ? 1 : 0, Triggerbot.mobs.getBooleanValue() ? 1 : 0, Triggerbot.animals.getBooleanValue() ? 1 : 0);
                    final boolean isValidEntity2 = (Triggerbot.mobs.getBooleanValue() && entity instanceof EntityMob && !entity.isInvisible() && !(entity instanceof EntityAnimal) && !(entity instanceof EntityPlayer)) || (Triggerbot.animals.getBooleanValue() && entity instanceof EntityAnimal && !entity.isInvisible() && !(entity instanceof EntityMob) && !(entity instanceof EntityPlayer)) || (Triggerbot.players.getBooleanValue() && entity instanceof EntityPlayer && !entity.isInvisible() && !(entity instanceof EntityAnimal) && !(entity instanceof EntityMob) /*&& !FriendManager.isFriend(entity.getName()))*/ || (Triggerbot.invisibles.getBooleanValue() && entity.isInvisible() /*&& !FriendManager.isFriend(entity.getName())*/));
                   
                    if (entity != null && isValidEntity2) {
                        ++this.delay;
                        
                        if (this.delay >= Triggerbot.attackDelay.getValue()) {
                            this.mc.thePlayer.swingItem();
                            this.mc.playerController.attackEntity(this.mc.thePlayer, entity);
                            this.delay = 0;
                        }
                    }
                }
            }
            
            if (Triggerbot.delayMode.getObject().equalsIgnoreCase("Random")) {
                if (Triggerbot.triggerMode.getObject().equalsIgnoreCase("BoundingBox")) {
                    final boolean isValidEntity = (Triggerbot.mobs.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityMob && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityAnimal) && !(this.mc.objectMouseOver.entityHit instanceof EntityPlayer)) || (Triggerbot.animals.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityAnimal && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityMob) && !(this.mc.objectMouseOver.entityHit instanceof EntityPlayer)) || (Triggerbot.players.getBooleanValue() && this.mc.objectMouseOver.entityHit instanceof EntityPlayer && !this.mc.objectMouseOver.entityHit.isInvisible() && !(this.mc.objectMouseOver.entityHit instanceof EntityAnimal) && !(this.mc.objectMouseOver.entityHit instanceof EntityMob) /*&&*/ /*!FriendManager.isFriend(this.mc.objectMouseOver.entityHit.getName()))*/ || (Triggerbot.invisibles.getBooleanValue() && this.mc.objectMouseOver.entityHit.isInvisible() /*&& !FriendManager.isFriend(this.mc.objectMouseOver.entityHit.getName())*/));
                    
                    if (this.mc.objectMouseOver.entityHit != null && isValidEntity && this.util.shouldHitEntity(this.mc.objectMouseOver.entityHit, 4.0, 360.0f, true, 0, Triggerbot.invisibles.getBooleanValue() ? 1 : 0, Triggerbot.players.getBooleanValue() ? 1 : 0, Triggerbot.mobs.getBooleanValue() ? 1 : 0, Triggerbot.animals.getBooleanValue() ? 1 : 0)) {
                        ++this.delay;
                        
                        if (this.delay >= this.getRandom(this.nums)) {
                            this.mc.thePlayer.swingItem();
                            this.mc.playerController.attackEntity(this.mc.thePlayer, this.mc.objectMouseOver.entityHit);
                            this.delay = 0;
                        }
                    }
                }
                
                if (Triggerbot.triggerMode.getObject().equalsIgnoreCase("FOV")) {
                    final Entity entity = this.util.getBestEntity(4.0, (float) Triggerbot.FOV.getValue(), true, 0, Triggerbot.invisibles.getBooleanValue() ? 1 : 0, Triggerbot.players.getBooleanValue() ? 1 : 0, Triggerbot.mobs.getBooleanValue() ? 1 : 0, Triggerbot.animals.getBooleanValue() ? 1 : 0);
                    final boolean isValidEntity2 = (Triggerbot.mobs.getBooleanValue() && entity instanceof EntityMob && !entity.isInvisible() && !(entity instanceof EntityAnimal) && !(entity instanceof EntityPlayer)) || (Triggerbot.animals.getBooleanValue() && entity instanceof EntityAnimal && !entity.isInvisible() && !(entity instanceof EntityMob) && !(entity instanceof EntityPlayer)) || (Triggerbot.players.getBooleanValue() && entity instanceof EntityPlayer && !entity.isInvisible() && !(entity instanceof EntityAnimal) && !(entity instanceof EntityMob) /*&&*/ /*!FriendManager.isFriend(entity.getName()))*/ || (Triggerbot.invisibles.getBooleanValue() && entity.isInvisible() /*&& !FriendManager.isFriend(entity.getName())*/));
                    
                    if (entity != null && isValidEntity2) {
                        ++this.delay;
                        
                        if (this.delay >= this.getRandom(this.nums)) {
                            this.mc.thePlayer.swingItem();
                            this.mc.playerController.attackEntity(this.mc.thePlayer, entity);
                            this.delay = 0;
                        }
                    }
                }
            }
        }
    }
}
