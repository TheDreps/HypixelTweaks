package me.thedreps.hypixeltweaks;

import com.google.gson.Gson;
import me.thedreps.hypixeltweaks.events.AutowhoManager;
import me.thedreps.hypixeltweaks.events.DeleteJson;
import me.thedreps.hypixeltweaks.events.LoginChat;
import me.thedreps.hypixeltweaks.reference.CurrentGame;
import me.thedreps.hypixeltweaks.reference.Reference;
import me.thedreps.hypixeltweaks.update.UpdateCheck;
import me.thedreps.hypixeltweaks.utility.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = Reference.MOD_ID,
		name = Reference.NAME,
		version = Reference.MOD_VERSION,
		acceptedMinecraftVersions = Reference.MC_VERSION,
		guiFactory = Reference.GUI_FACTORY_CLASS)


@SuppressWarnings("unused")
public class HypixelTweaks
{
	@Mod.Instance(Reference.MOD_ID)
	public static HypixelTweaks instance = new HypixelTweaks();
    public static Configuration config = ConfigHandler.getConfig();
	private static ServerInfo serverInfo = new ServerInfo();
	public static boolean isAwaitingAutowho = true;

	public static ServerInfo getServerInfo(){return serverInfo;}


	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent e){

		//Config
		String configDir = e.getModConfigurationDirectory().toString();
		ConfigHandler.init(configDir);
		MinecraftForge.EVENT_BUS.register(new ConfigHandler());

		//Update checker
		UpdateCheck.checkForUpdates();
		MinecraftForge.EVENT_BUS.register(new LoginChat());

		//Mods
		MinecraftForge.EVENT_BUS.register(new DeleteJson());
		MinecraftForge.EVENT_BUS.register(new AutowhoManager());


//		MinecraftForge.EVENT_BUS.register(new KeyHandler());
//		MinecraftForge.EVENT_BUS.register(new Walking());

	}


    @Mod.EventHandler
    public void init(FMLInitializationEvent e)
    {
        MinecraftForge.EVENT_BUS.register(this);
    }





    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();
        
        	//Catches /locraw
            if(message.contains("{\"server\":")) {
            	isAwaitingLocation = false;
            	serverInfo.reset();
            	Gson g = new Gson();
        		serverInfo = g.fromJson(message, ServerInfo.class);
        		
            	serverInfo.json = message;
        		e.setCanceled(true);
        		
        		
        		//Starts autowho timer
        		if(isAwaitingAutowho) {
        			if((serverInfo.gametype.equals("BEDWARS") || serverInfo.gametype.equals("SKYWARS")) && !(serverInfo.mode.equals("default"))) {
						AutowhoManager.startAutowho(20);
                	}
        		}
        	}

			else if(message.contains("BED DESTRUCTION > ") || message.contains("FINAL KILL!")){
				CurrentGame.updateKillMessage(e);
			}
            
            else if(message.contains("Your new API key is")) {
				String userApiKey = message.substring(message.length() - 36);
				ConfigHandler.setApiKey(userApiKey);
				LogHelper.info("[Hypixel Tweaks] Registered new API key: " + userApiKey);
            }
    }

    boolean isAwaitingLocation = true;
    
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
      serverInfo.reset();
      isAwaitingLocation = true;
      isAwaitingAutowho = true;
	  CurrentGame.resetGame();
      
      
    }
    
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChunkLoad(ChunkEvent.Load event) {
    	if(isAwaitingLocation) {
    		if(ConfigHandler.enableCompatibilityMode) {
    			LogHelper.info("Compatibility mode enabled - Ran /locraw");
    			Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
    		}
    		isAwaitingLocation = false;
        }
    }
    
}
