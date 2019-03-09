package net.tntchina.client.inputFix;

import net.tntchina.client.inputFix.impl.GuiScreenFixOthers;
import net.tntchina.client.inputFix.impl.GuiScreenFixWindows;
import net.tntchina.client.inputFix.interfaces.IGuiScreenFix;
import net.tntchina.client.inputFix.utils.OSDetector;

public class InputFixSetup {

	public static IGuiScreenFix impl;

	public static void call() throws Exception {
		OSDetector.OS OS = OSDetector.detectOS();
		switch (OS) {
		case Windows:
			impl = new GuiScreenFixWindows();
			break;
		case Linux:
		case Mac:
			try {
				impl = new GuiScreenFixOthers();
			} catch (Throwable t) {
				impl = new GuiScreenFixWindows();
			}

			break;
		case Unknown:

		default:
			break;
		}
	}
}