package net.minecraft.util;

import net.minecraft.client.settings.GameSettings;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.module.modules.movement.NoSlow;

public class MovementInputFromOptions extends MovementInput {
	
	public final GameSettings gameSettings;

	public MovementInputFromOptions(GameSettings gameSettingsIn) {
		this.gameSettings = gameSettingsIn;
	}

	public void updatePlayerMoveState() {
		this.moveStrafe = 0.0F;
		this.moveForward = 0.0F;

		if (this.gameSettings.keyBindForward.isKeyDown()) {
			++this.moveForward;
		}

		if (this.gameSettings.keyBindBack.isKeyDown()) {
			--this.moveForward;
		}

		if (this.gameSettings.keyBindLeft.isKeyDown()) {
			++this.moveStrafe;
		}

		if (this.gameSettings.keyBindRight.isKeyDown()) {
			--this.moveStrafe;
		}

		this.jump = this.gameSettings.keyBindJump.isKeyDown();
		this.sneak = this.gameSettings.keyBindSneak.isKeyDown();

		if (this.sneak && !ModuleManager.getModuleState(NoSlow.class)) {
			this.moveStrafe = (float) ((double) this.moveStrafe * 0.3D);
			this.moveForward = (float) ((double) this.moveForward * 0.3D);
		}
	}
	
	public GameSettings getGameSetting() {
		return this.gameSettings;
	}
}