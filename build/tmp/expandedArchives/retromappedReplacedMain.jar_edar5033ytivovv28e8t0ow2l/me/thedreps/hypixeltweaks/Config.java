package me.thedreps.hypixeltweaks;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
	
	private static Configuration config = null;
	
	public static final String GENERAL = "General";
	
	public static boolean enableAutowho;
	public static boolean enableJsonRemover;
	public static boolean enableCompatibilityMode;
	
	public static void preInit() {
		File configFile = new File(Loader.instance().getConfigDir(), "HypixelTweaks.cfg");
		config = new Configuration(configFile);
		syncFromFiles();
	}
	
	public static Configuration getConfig() {
		return config;
	}
	
	public static void clientPreInit() {
		MinecraftForge.EVENT_BUS.register(new ConfigEventHandler());
	}
	
	public static void syncFromFiles() {
		syncConfig(true, true);
	}
	
	public static void syncFromGUI() {
		syncConfig(false, true);
	}
	
	public static void syncFromFields() {
		syncConfig(false, false);
	}
	
	private static void syncConfig(boolean loadFromConfigFile, boolean readFieldsFromConfig) {
		if(loadFromConfigFile) {
			config.load();
		}
		
		Property propertyEnableAutowho = config.get(GENERAL, "enable_autowho", true);
		propertyEnableAutowho.comment = "Enable Autowho here";
		Property propertyEnableJsonRemover = config.get(GENERAL, "enable_json_remover", true);
		propertyEnableJsonRemover.comment = "Enable Json Remover here";
		Property propertyEnableCompatibilityMode = config.get(GENERAL, "enable_compatibility_mode", false);
		propertyEnableCompatibilityMode.comment = "If you are having issues with the plugin try to enable this";
		
		List<String> propertyOrderBlocks = new ArrayList<String>();
		propertyOrderBlocks.add(propertyEnableAutowho.getName());
		propertyOrderBlocks.add(propertyEnableJsonRemover.getName());
		propertyOrderBlocks.add(propertyEnableCompatibilityMode.getName());
		
		config.setCategoryPropertyOrder(GENERAL, propertyOrderBlocks);
		
		if(readFieldsFromConfig) {
			enableAutowho = propertyEnableAutowho.getBoolean();
			enableJsonRemover = propertyEnableJsonRemover.getBoolean();
			enableCompatibilityMode = propertyEnableCompatibilityMode.getBoolean();
		}
		
		propertyEnableAutowho.set(enableAutowho);
		propertyEnableJsonRemover.set(enableJsonRemover);
		propertyEnableCompatibilityMode.set(enableCompatibilityMode);
		
		if(config.hasChanged()) {
			config.save();
		}
		
	}

	public static class ConfigEventHandler{
		
		@SubscribeEvent(priority = EventPriority.LOWEST)
		public void onEvent(ConfigChangedEvent.OnConfigChangedEvent e) {
			if(e.modID.equals(HypixelTweaks.MODID)) {
				syncFromGUI();
			}
		}
	}
}
