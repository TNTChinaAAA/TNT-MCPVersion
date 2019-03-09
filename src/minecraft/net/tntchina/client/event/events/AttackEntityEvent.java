package net.tntchina.client.event.events;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

/**
 * on Player attacking entity event.(在有实体攻击实体的时候调用的事件)
 */
public class AttackEntityEvent extends CancellableEvent {
	
	private Minecraft mc;
	private EntityPlayer entity;
	private Entity attackTarget;
	
	public AttackEntityEvent(Minecraft mc, Entity attackTarget, EntityPlayer entity) {
		this.mc = mc;
		this.attackTarget = attackTarget;
		this.entity = entity;
	}
	
	public Minecraft getMinecraft() {
		return this.mc;
	}
	
	public EntityPlayer getEntity() {
		return this.entity;
	}
	
	public Entity getAttackTarget() {
		return this.attackTarget;
	}
}
