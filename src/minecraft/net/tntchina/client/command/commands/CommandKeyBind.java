package net.tntchina.client.command.commands;

import org.lwjgl.input.Keyboard;

import net.tntchina.client.command.Command;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleManager;

/**
 * the command on process changed module key.(����������ʱ����Ĺ��ܵİ��ȼ�)
 * @author TNTChina
 */
public class CommandKeyBind extends Command {
	
	public CommandKeyBind() {
		super("bind");
	}

	public void execute(String[] strings) {
		if (strings.length > 2) {
			Module module = ModuleManager.getModule(strings[1]);
			
			if (module == null) {
				this.printMessageToGuiScreen(this.getBigString("<KeyBind>: ") + Color + "c: The Module not exists.");
			} else {
				module.setKeyBind(Keyboard.getKeyIndex(strings[2].toUpperCase()));
				this.printMessageToGuiScreen(this.getBigString("<KeyBind>: ") + Color + "a" + module.getName() + " was to " + Keyboard.getKeyName(module.getKeyBind()) + ".");
			}
		} else {
			this.printMessageToGuiScreen(this.getBigString("<KeyBind>: ") + "Format Error.");
		}
	}
}
