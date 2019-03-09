package net.tntchina.client.inputFix.impl;

import org.lwjgl.input.Keyboard;
import net.tntchina.client.inputFix.interfaces.IGuiScreen;
import net.tntchina.client.inputFix.interfaces.IGuiScreenFix;

public class GuiScreenFixWindows implements IGuiScreenFix {

	@Override
	public void handleKeyboardInput(IGuiScreen gui) {
		char c = Keyboard.getEventCharacter();
		int k = Keyboard.getEventKey();
		
		if (Keyboard.getEventKeyState() || (k == 0 && Character.isDefined(c))) {
			gui.keyTyped(c, k);
		}
	}
}