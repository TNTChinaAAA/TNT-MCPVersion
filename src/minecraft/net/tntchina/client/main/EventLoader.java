package net.tntchina.client.main;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.tntchina.client.event.EventListener;
import net.tntchina.client.event.EventManager;
import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.AttackEntityEvent;
import net.tntchina.client.event.events.CreateDisplayEvent;

public class EventLoader implements EventListener {
	
	public Minecraft mc;
	
	public EventLoader() {
		EventLoader.this.mc = Minecraft.getMinecraft();
		EventManager.registerListener(EventLoader.this);
	}
	
	@EventTarget
	public void onAttack(AttackEntityEvent event) {
		if (event.getEntity().equals(this.mc.thePlayer)) {
			if (event.getAttackTarget() instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.getAttackTarget();
				
				if (player.getGameProfile() != null) {
					GameProfile profile = player.getGameProfile();
					
					if (profile.equals(this.mc.thePlayer.getGameProfile())) {
						event.setCancelled(true);
					}
				}
			}
		}
	}
	
	@EventTarget
	public void onDisplay(CreateDisplayEvent event) {
		event.setWidth(1489);
		event.setHeight(750);
	}
}