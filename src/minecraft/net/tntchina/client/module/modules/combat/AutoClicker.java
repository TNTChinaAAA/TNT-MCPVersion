package net.tntchina.client.module.modules.combat;

import org.lwjgl.input.Mouse;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleCategory;
import net.tntchina.client.util.MathHelper;
import net.tntchina.client.value.values.NumberValue;

public class AutoClicker extends Module {

	private NumberValue CPS = new NumberValue(10.0D, "CPS", 20.0D, 1.0D);
	
	public AutoClicker(String name, ModuleCategory categorys) {
		super(name, categorys);
	}
	
	public void onUpdate() {
		if (!this.getState()) {
			return;
		}
		
		if (this.timeUtil.hasTimeReached(MathHelper.div(1000, this.CPS.getObject().doubleValue()))) {
			this.mc.gameSettings.keyBindAttack.pressed = true;
			this.timeUtil.setLastMS();
		} else {
			if (Mouse.getEventButtonState()) {
				this.mc.gameSettings.keyBindAttack.pressed = Mouse.isButtonDown(1);
			}
		}
	}
}
