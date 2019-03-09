package net.tntchina.client.clickgui.module;

import net.tntchina.client.clickgui.other.Option;
import net.tntchina.client.gui.Button;
import net.tntchina.client.gui.Label;
import net.tntchina.client.module.Module;

public class ModuleButton extends Button {

	private Module m;
	private Option option;
	
	public ModuleButton(Module m) {
		super(new Label(m.getName()));
		this.m = m;
	}

	public Module getModule() {
		return this.m;
	}
	
	public void setOption(Option option) {
		this.option = option;
	}
	
	public Option getOption() {
		return this.option;
	}
	
	public boolean hasOption() {
		return this.option != null;
	}
}
