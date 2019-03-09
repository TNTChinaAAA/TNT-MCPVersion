package net.tntchina.client.main;

import net.tntchina.client.clickgui.SettingGUI;

public class Wrapper {
    
	private static final OpenClickGui clickGUI = new OpenClickGui("ClickGui", 54, null);
	private static final SettingGUI customUI = new SettingGUI();
	
	public static final OpenClickGui getClickGUIModule() {
		return Wrapper.clickGUI;
	}
	
	public static final SettingGUI getCustomUI() {
		return Wrapper.customUI;
	}
}