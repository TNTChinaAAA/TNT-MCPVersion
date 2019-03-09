package net.tntchina.client.inputFix;

import java.lang.reflect.Method;

import net.minecraft.client.gui.GuiScreen;
import net.tntchina.client.inputFix.interfaces.IGuiScreen;
import net.tntchina.client.inputFix.utils.ReflectionHelper;

import org.lwjgl.input.Keyboard;

public class GuiScreenFix {

	public static class Proxy implements IGuiScreen {

		private GuiScreen gui;

		public void keyTyped(char c, int k) {
			try {
				if (this.gui != null)
					keyTyped.invoke(gui, c, k);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}

		public Proxy setGui(GuiScreen gui) {
			this.gui = gui;
			return this;
		}
	}

	public static final ThreadLocal<Proxy> proxies = new ThreadLocal<Proxy>() {

		protected Proxy initialValue() {
			return new Proxy();
		}
	};

	public static final Method keyTyped = ReflectionHelper.findMethod(GuiScreen.class, new String[] { "func_73869_a", "keyTyped" }, new Class<?>[] { char.class, int.class });

	public static void handleKeyboardInput(GuiScreen gui) {
		Proxy p = proxies.get().setGui(gui);

		if (InputFixSetup.impl != null) {
			InputFixSetup.impl.handleKeyboardInput(p);
		} else if (Keyboard.getEventKeyState()) {
			p.keyTyped(Keyboard.getEventCharacter(), Keyboard.getEventKey());
		}

		gui.mc.dispatchKeypresses();
	}
}