package me.thedreps.hypixeltweaks;

import me.thedreps.hypixeltweaks.reference.Reference;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.*;

@SuppressWarnings("unused")
public class ConfigHandler {
	
	public static Configuration config;
	public static String apiKeyPath;
	
	public static boolean updateCheck = true;
	public static boolean enableJsonRemover = true;
	public static boolean enableCompatibilityMode = false;

	public static void init(String configDir) {
		if(config == null) {
			File path = new File(configDir + "/" + Reference.MOD_ID + ".cfg");

			config = new Configuration(path);
			loadConfiguration();

			apiKeyPath = configDir + "/" + Reference.MOD_ID + "_apiKey.txt";
		}
	}

	private static void loadConfiguration(){
		updateCheck = config.getBoolean("Check For Updates", Configuration.CATEGORY_GENERAL, true, "Allow this mod to check for updates.");
		enableJsonRemover = config.getBoolean("Enable Json Remover", Configuration.CATEGORY_GENERAL, true, "Stops badly coded plugins spamming chat with /locraw");
		enableCompatibilityMode = config.getBoolean("Enable Compatibility Mode", Configuration.CATEGORY_GENERAL, false, "If the plugin isn't performing as expected try turning this on.");



		if(config.hasChanged()){
			config.save();
		}
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent e){
		if(e.modID.equalsIgnoreCase(Reference.MOD_ID)){
			loadConfiguration();
		}
	}

	public static Configuration getConfig() {return config;}

	public static String getApiKey(){
		try {
			BufferedReader br = new BufferedReader(new FileReader(apiKeyPath));
			return br.readLine();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void setApiKey(String key){
		PrintWriter pw;
		try {
			pw = new PrintWriter(apiKeyPath, "UTF-8");
			pw.println(key);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
