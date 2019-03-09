package net.tntchina.client.main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IChatComponent;
import net.tntchina.client.command.CommandManager;
import net.tntchina.client.module.Module;
import net.tntchina.client.module.ModuleManager;
import net.tntchina.client.module.modules.render.hud.HUDValueManager;
import net.tntchina.client.tabgui.TabManager;
import net.tntchina.client.util.LogManager;
import net.tntchina.client.util.Logger;
import net.tntchina.client.util.XRayUtil;

public class TNTChina {
	
	public static final String SPACE = " ";
	public static final String CLIENT_NAME = "TNT";
	public static final String CLIENT_AUTHOR = "TNTChina";
	public static final String CLIENT_VERSION = "1.1.3";
	public static final String CLIENT_FULL_NAME = TNTChina.getFullName();
	public static final String CLIENT_AUTHOR_QQ = "3274578216";
	public static final Logger logger = LogManager.getLogger("TNTChina Client");
	public static final File TNTCHINA_FOLDEN = new File(System.getenv("APPDATA") + File.separator + "TNTChina");
	public static Minecraft mc;
	public static File ResourceFolden;
	public static TNTChina theClient;
	public static ModuleManager moduleManager;
	public static CommandManager commandManager;
	private static FileManager fileManager;
	private static volatile boolean isLoading = true;
	
	private TNTChina() {
		TNTChina.theClient = this;
		TNTChina.TNTCHINA_FOLDEN.mkdirs();
		
		/*try {
			Class.forName(MainCheck.class.getName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (!new File(TNTChina.TNTCHINA_FOLDEN, "read.data").exists()) {
			try {
				new File(TNTChina.TNTCHINA_FOLDEN, "read.data").createNewFile();
				MainCheck.main(new String[0]);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			File file = new File(TNTChina.TNTCHINA_FOLDEN, "read.data");
			
			try {
				FileReader fileReader = new FileReader(file);
				String key = "";
				String key_ = "";
				LineNumberReader reader = new LineNumberReader(fileReader);
				
				while ((key = reader.readLine()) != null) {
					if (Strings.isEmpty(key)) {
						continue;
					}
					
					key_ = key;
					break;
				}
				
				if (!Arrays.asList(MainCheck.tokens.split(",")).contains(key_)) {
					reader.close();
					fileReader.close();
					MainCheck.main(new String[0]);
				} else {
					MainCheck.lastToken = key_;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}*/
	}
	
	public static InputStream getResourceAsStream(String classLoaderPath) {
		return TNTChina.class.getClassLoader().getResourceAsStream(classLoaderPath);
	}
	
	public static String getFullName() {
		List<String> strings = new ArrayList<String>();
		LineNumberReader line = new LineNumberReader(new InputStreamReader(TNTChina.getResourceAsStream("assets/minecraft/txt/sir.txt")));
		String txt = "";
		
		try {
			while ((txt = line.readLine()) != null) {
				strings.add(txt);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Random random = new Random();
		int index = random.nextInt(strings.size());
		return CLIENT_NAME + SPACE + "V" + CLIENT_VERSION + " | " + strings.get(index) + " ---by " + TNTChina.CLIENT_AUTHOR + " | QQ " + TNTChina.CLIENT_AUTHOR_QQ;
	}
	
	public static TNTChina getTNTChina() {
		return TNTChina.theClient;
	}
	
	public static String getClassName() {
		String name = TNTChina.class.getName().replace(".", "/");
		return name;
	}

	public static void InstanceClient() {
		new TNTChina();
		TNTChina.loaderEvent();
		XRayUtil.initXRay();
		TNTChina.registerValue();
		TNTChina.mkdirs();
		TNTChina.registerAll();
		TNTChina.startThread();
	}
	
	private static void loaderEvent() {
		new EventLoader();
	}
	
	private static void registerValue() {
		TNTChina.mc = Minecraft.getMinecraft();
		TNTChina.ResourceFolden = new File(Minecraft.getMinecraft().mcDataDir, "TNTChina Client");
		logger.info("Register value end.");
	}
	
	private static void registerAll() {
		TNTChina.registerModules();
		TNTChina.registerCommand();
		TNTChina.registerTabGui();
		TNTChina.registerJson();
		logger.info("Register Class end.");
	}
	
	private static void registerCommand() {
		TNTChina.commandManager = new CommandManager();
	}
	
	private static void registerModules() {
		TNTChina.moduleManager = new ModuleManager();
	}
	
	private static void registerTabGui() {
		TabManager.initTabGui();
	}
	
	private static void registerJson() {
		TNTChina.fileManager = new FileManager();
	}
	
	private static void startThread() {
		TNTChina.fileManager.loadModules();
	}
	
	private static void mkdirs() {
		TNTChina.ResourceFolden.mkdirs();
		logger.info("Md dir end.");
	}
	
	public static void loadModule() {
		if (Minecraft.getMinecraft().thePlayer == null) {
			return;
		}
		
		if (TNTChina.isLoading) {
			for (Module m : ModuleManager.getModules()) {
				m.onToggled();
			
				if (m.getState()) {
					m.onEnable();
				} else {
					m.onDisable();
				}
			}
			
			TNTChina.logger.info("Loading Module...");
		} else {
			return;
		}
	
		TNTChina.isLoading = false;
	}
	
	public static void startClient() throws Exception {
		HUDValueManager.makeValues();
		Wrapper.getClickGUIModule().setup();
		Wrapper.getCustomUI().initUI();
		TNTChina.logger.info("TNTChina Client by " + TNTChina.CLIENT_AUTHOR);
		TNTChina.logger.info("The Client has " + TNTChina.getModules().size() + " modules.");
		TNTChina.fileManager.loadGUISetting();
		TNTChina.fileManager.loadCustomUI();
		Display.getParent();
		Display.getParent();
		Display.getParent();
	}
	
	public static void stopClient() throws Exception {
		TNTChina.fileManager.saveModules();
		TNTChina.fileManager.saveGUISetting();
		TNTChina.fileManager.saveCustomUI();
		
		/*try {
			FileWriter writer = new FileWriter(new File(TNTChina.TNTCHINA_FOLDEN, "read.data"));
			writer.write(MainCheck.lastToken);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	public static void isPresses(int key) {
		if (Wrapper.getClickGUIModule().getKeyBind() == key) {
			Wrapper.getClickGUIModule().Toggled();
		}
		
		for (Module m : ModuleManager.getModules()) {
			if (m.getKeyBind() == key) {
				m.Toggled();
			}
		}
	}
	
	public static void onRender() {
		for (Module m : ModuleManager.getModules()) {
			m.onRender();
		}
	}
	
	public static List<Module> getModules() {
		return ModuleManager.getModules();
	}
	
    public static void displayMessage(String message) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent("{text:\"" + message + "\"}"));
    }
    
    public static String getTNTChinaDir() {
    	return TNTChina.ResourceFolden.getAbsolutePath();
    }
    
    public static void callCommand(String args) {
    	TNTChina.commandManager.callCommand(args);
    }
    
    public static CommandManager getCommandManager() {
    	return TNTChina.commandManager;
    }
    
    public static Logger getLogger() {
    	return TNTChina.logger;
    }
    
    public static void GameKeyPresses() {
        if (Minecraft.getMinecraft().thePlayer == null) {
        	return;
        }
    	
    	int i = Keyboard.getEventKey() == 0 ? Keyboard.getEventCharacter() : Keyboard.getEventKey();

		if (i == Wrapper.getClickGUIModule().getKeyBind()) {
        	Wrapper.getClickGUIModule().Toggled();
        	
        	if (!Wrapper.getClickGUIModule().getState()) {	
            	Minecraft.getMinecraft().displayGuiScreen((GuiScreen) null);
            	
            	if (Minecraft.getMinecraft().currentScreen == null) {
            		Minecraft.getMinecraft().setIngameFocus();
            	}
            }
		}
	}
    
    public static File getDir() {
    	return new File(Minecraft.getMinecraft().mcDataDir, "TNTChina Client");
    }

	public static FileManager getFileManager() {
		return TNTChina.fileManager;
	}
}