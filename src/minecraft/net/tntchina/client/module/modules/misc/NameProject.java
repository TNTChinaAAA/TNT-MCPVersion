package net.tntchina.client.module.modules.misc;

import net.tntchina.client.event.EventTarget;
import net.tntchina.client.event.events.TextEvent;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.value.Value;

public class NameProject extends Module {

	public Value<String> name = new Value<String>("byTNTChina", "Name");
	
	public NameProject(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	@EventTarget
	public void onText(TextEvent event) {
		if (this.getState() && event.getStr() != null && this.mc.thePlayer != null) {
			final String str = event.getStr();
			
			if (str.equals(this.mc.thePlayer.getName())) {
				event.setStr(this.name.getObject());
			}
		}
	}
}