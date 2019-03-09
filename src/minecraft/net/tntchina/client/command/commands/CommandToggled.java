package net.tntchina.client.command.commands;

import net.tntchina.client.command.Command;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleManager;

/**
 * the command on process changed module state.(这个命令被激活时会切换功能的开启状态)
 * @author TNTChina
 */
public class CommandToggled extends Command {
	
	public CommandToggled() {
		super("Toggled");
	}

	public void execute(String[] strings) {
		if (strings.length > 1) {
			Module m = ModuleManager.getModule(strings[1]);
			
			if (m != null) {
				m.Toggled();
				this.printMessageToGuiScreen(Color + "c<Toggled>: Toggled Module " + m.getName() + ".");
			} else {
				this.printMessageToGuiScreen(this.getBigString("<Toggled>: ") + "Module not exists!");
			}
		} else {
			this.printMessageToGuiScreen(Color + "c<Toggled>: Format Error!");
		}
	}
}
