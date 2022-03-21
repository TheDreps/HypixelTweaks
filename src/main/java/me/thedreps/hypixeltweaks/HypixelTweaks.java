package me.thedreps.hypixeltweaks;

import com.google.gson.Gson;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;

@Mod(modid = HypixelTweaks.MODID, version = HypixelTweaks.VERSION)
public class HypixelTweaks
{
    public static final String MODID = "hypixeltweaks";
    public static final String VERSION = "1.0";
    
    public static ServerInfo serverInfo = new ServerInfo();
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
//    boolean countingTicks = false;
//    int ticksLeft;
    
//    @SubscribeEvent
//    public void startAutoWho(ClientTickEvent e) {
//    	if(countingTicks) {
//    		if(ticksLeft > 0) {
//    			ticksLeft--;
//    			
//    		}else {
//    			Minecraft.getMinecraft().thePlayer.sendChatMessage("/who");
//    			countingTicks = false;
//    		}
//    	}
//    }
    
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.getUnformattedText();
            if(message.contains("{\"server\":")) {
            	serverInfo.reset();
            	Gson g = new Gson();
        		serverInfo = g.fromJson(message, ServerInfo.class);
        		
        		Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(serverInfo.server));
        		
        		e.setCanceled(true);
            }
    }
    
    boolean isAwaitingLocation = true;
    
    @SubscribeEvent
    public void onWorldChange(WorldEvent.Load event) {
      // Mark the current world as eligible for "/locraw" to be re-run.
    	serverInfo.reset();
      isAwaitingLocation = true;
    }
    
    
    @SubscribeEvent
    public void onChunkLoad(ChunkEvent.Load event) {
    	if(isAwaitingLocation) {
        	Minecraft.getMinecraft().thePlayer.sendChatMessage("/locraw");
        	isAwaitingLocation = false;
        }
    }
    
}
