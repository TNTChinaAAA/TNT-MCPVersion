package net.tntchina.client.clickgui.other;

import net.tntchina.client.gui.Button;
import net.tntchina.client.gui.Label;
import net.tntchina.client.value.impl.INumberValue;

public class NumberValueButton extends Button {
	
	private INumberValue value;
	private ProcessBar bar;
	
	public NumberValueButton(INumberValue value, ProcessBar bar) {
		super(new Label(value.getValueName()));
		this.value = value;
		this.bar = bar;
	}
	
	public double getValue() {
		return this.value.getDoubleValue();
	}
	
	public void setValue(double value) {
		if (value > this.value.getMaxDoubleValue()) {
			return;
		}
		
		if (value < this.value.getMinDoubleValue()) {
			return;
		}
	
		this.value.setDouble(value);
		this.bar.setValue(value);
	}
	
	public ProcessBar getProcessBar() {
		return this.bar;
	}
	
	public void setProcessBar(ProcessBar bar) {
		this.bar = bar;
	}
}
