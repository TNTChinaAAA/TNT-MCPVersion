package net.tntchina.client.tabgui;

import net.tntchina.client.gui.Button;
import net.tntchina.client.gui.Label;
import net.tntchina.client.module.Module;

/**
 * the tab 's button(这个Tab类别的按钮)
 * @author TNTChina
 */
public class TabButton extends Button {

	private volatile Module module;
	
	public TabButton(Module module) {
		super(new Label(module.getName()));
		this.module = module;
	}

	public Module getModule() {
		return this.module;
	}
}
