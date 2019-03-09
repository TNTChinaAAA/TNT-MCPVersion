package net.tntchina.client.value.impl;

public interface INumberValue {
	
	double getMaxDoubleValue();
	
	double getMinDoubleValue();
	
	double getDoubleValue();

	void setDouble(double value);

	String getValueName();
}
