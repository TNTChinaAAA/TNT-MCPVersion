package net.tntchina.client.clickgui.other;

import net.tntchina.client.gui.*;
import net.tntchina.client.value.*;
import net.tntchina.client.value.impl.IIntegerValue;

public class IntegerValueButton extends Button {
	
	protected IIntegerValue value;
	protected IntegerProcessBar bar;
	
	public IntegerValueButton(IIntegerValue value, IntegerProcessBar bar) {
		super(new Label(value.getValueName()));
		this.value = value;
		this.bar = bar;
	}
	
	public long getValue() {
		return this.value.getIntValue();
	}
	
	public void setValue(long value) {
		if (value > this.value.getMaxValue()) {
			return;
		}
		
		if (value < this.value.getMinValue()) {
			return;
		}
	
		this.value.setInt(Long.valueOf(value).intValue());
		this.bar.setValue(value);
	}
	
	public IntegerProcessBar getProcessBar() {
		return this.bar;
	}
	
	public void setProcessBar(IntegerProcessBar bar) {
		this.bar = bar;
	}
}
