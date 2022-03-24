package me.thedreps.hypixeltweaks;

import java.util.List;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
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

@Mod(modid = HypixelTweaks.MODID, name = HypixelTweaks.NAME, version = HypixelTweaks.VERSION, 
guiFactory = "me.thedreps.hypixeltweaks."+HypixelTweaks.MODID+".gui.GuiFactoryHypixelTweaks")


public class HypixelTweaks
{
	public static final String NAME = "Hypixel Tweaks";
    public static final String MODID = "hypixeltweaks";
    public static final String VERSION = "1.1";
    
    public static ServerInfo serverInfo = new ServerInfo();
    public static Configuration config = Config.getConfig();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
        Config.preInit();
    }
   
    
    
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.func_150260_c();
        
        	//Catches /locraw
            if(message.contains("{\"server\":")) {
            	isAwaitingLocation = false;
            	serverInfo.reset();
            	Gson g = new Gson();
        		serverInfo = g.fromJson(message, ServerInfo.class);
        		
            	serverInfo.json = message;
            	System.out.println(serverInfo.debug());
        		e.setCanceled(true);
        		
        		
        		//Starts autowho bedwars timer
        		if(isAwaitingAutowho && Config.enableAutowho) {
        			if(serverInfo.gametype.equals("BEDWARS") && !(serverInfo.mode.equals("default"))) {
                		ticksLeftAutowho = 25;
                		countingTicksAutowho = true;
                		isAwaitingAutowho = false;
                	}
        		}
        		
        		//Starts autowho skywars timer
        		if(isAwaitingAutowho && Config.enableAutowho) {
        			if(serverInfo.gametype.equals("SKYWARS") && !(serverInfo.mode.equals("default"))) {
                		ticksLeftAutowhoSkywars = 25;
                		countingTicksAutowhoSkywars = true;
                		isAwaitingAutowho = false;
                	}
        		}
        	}
            
//            if(message.contains("Your new API key is")) {
//            	Config.apiKey = message.substring(message.length()-36);
//            	config.getCategory(Configuration.CATEGORY_GENERAL).get("api_key").set(message.substring(message.length()-36));
//            	config.save();
//            	System.out.println("API KEY IS: " + message.substring(message.length()-36));
//            }
    }
    
    
    public void skywarsAutowho() {
    	List<EntityPlayer> players = Minecraft.func_71410_x().field_71439_g.field_70170_p.field_73010_i;
    	StringBuilder sb = new StringBuilder();
    	sb.append("ONLINE: ");
    	for(int i = 0; i<players.size(); i++) {
    		sb.append(players.get(i).getDisplayNameString() + ", ");
    	}
    	String online = sb.toString().trim();
    	online = online.substring(0, online.length()-1);
    	
    	Minecraft.func_71410_x().field_71439_g.func_145747_a(new ChatComponentText(online));
    	
    	
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
    			Minecraft.func_71410_x().field_71439_g.func_71165_d("/who");
    			countingTicksAutowho = false;
    		}
    	}
    }
    
    
  //AUTOWHO
    boolean countingTicksAutowhoSkywars = false;
    int ticksLeftAutowhoSkywars;
    
    @SubscribeEvent
    public void startAutoWhoSkywars(ClientTickEvent e) {
    	if(countingTicksAutowhoSkywars) {
    		if(ticksLeftAutowhoSkywars > 0) {
    			ticksLeftAutowhoSkywars--;
    			
    		}else {
    			skywarsAutowho();
    			countingTicksAutowhoSkywars = false;
    		}
    	}
    }
    
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void deleteJSON(ClientChatReceivedEvent e) {
        if(Config.enableJsonRemover) {
        	String message = e.message.func_150260_c();
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
    			Minecraft.func_71410_x().field_71439_g.func_71165_d("/locraw");
    		}
    		isAwaitingLocation = false;
        }
    }
    
}
