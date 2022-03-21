package me.thedreps.hypixeltweaks;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@Mod(modid = HypixelTweaks.MODID, version = HypixelTweaks.VERSION)
public class HypixelTweaks
{
    public static final String MODID = "hypixeltweaks";
    public static final String VERSION = "1.0";
    
    public static ServerInfo serverInfo = new ServerInfo();
    //Configuration config = Config.getConfig();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        Config.preInit();
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
            	System.out.println(serverInfo.debug());
        		e.setCanceled(true);
        		
        		
        		//Starts autowho timer
        		if(isAwaitingAutowho && Config.enableAutowho) {
        			if(serverInfo.gametype.equals("BEDWARS") && !(serverInfo.mode.equals("default"))) {
                		ticksLeftAutowho = 25;
                		countingTicksAutowho = true;
                		isAwaitingAutowho = false;
                	}
        		}
        		
        		return;
            }
            
    }
    
    
    //AUTOWHO
    
    
    boolean countingTicksAutowho = false;
    int ticksLeftAutowho;
    
    @SubscribeEvent
    public void startAutoWho(ClientTickEvent e) {
    	if(countingTicksAutowho) {
    		if(ticksLeftAutowho > 0) {
    			ticksLeftAutowho--;
    			
    		}else {
    			Minecraft.getMinecraft().thePlayer.sendChatMessage("/who");
    			countingTicksAutowho = false;
    		}
    	}
    }
    
    
    
    
    
    
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void deleteJSON(ClientChatReceivedEvent e) {
        if(Config.enableJsonRemover) {
        	String message = e.message.getUnformattedText();
            if(message.contains("{\"server\":")) {
            	e.setCanceled(true);
            }
        }
    }
    
    
    
    boolean isAwaitingAutowho = true;
    boolean isAwaitingLocation = true;
    
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
      serverInfo.reset();
      isAwaitingLocation = true;
      isAwaitingAutowho = true;
      
      
    }
    
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onChunkLoad(ChunkEvent.Load event) {
    	if(isAwaitingLocation) {
    		if(Config.enableCompatibilityMode) {
    			System.out.println("Compatibility mode enabled - Ran /locraw");
    			Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
    		}
    		isAwaitingLocation = false;
        }
    }
    
}
