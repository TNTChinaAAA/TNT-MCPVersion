package net.tntchina.inject;

public interface IEntityPlayerSP {
	
	void onMoveEntity(double x, double y, double z);
	
	boolean moving();
	
	float getSpeed();
	
	void setSpeed(double speed);

	void setMoveSpeed(double speed);
	
	float getDirection();
}