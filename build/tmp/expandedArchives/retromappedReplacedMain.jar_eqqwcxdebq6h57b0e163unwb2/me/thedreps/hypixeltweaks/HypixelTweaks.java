package me.thedreps.hypixeltweaks;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = HypixelTweaks.MODID, version = HypixelTweaks.VERSION)
public class HypixelTweaks
{
    public static final String MODID = "hypixeltweaks";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent e) {
        String message = e.message.func_150260_c();
            if(message.contains("{\"server\":")) {
                     e.setCanceled(true);
                     
            }else if(message.contains("        ")) {
            	Minecraft.func_71410_x().field_71439_g.func_71165_d("/who");
            }
    }
}
