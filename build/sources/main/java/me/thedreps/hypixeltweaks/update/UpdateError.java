package me.thedreps.hypixeltweaks.update;

import me.thedreps.hypixeltweaks.events.LoginChat;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.ChunkEvent;

public class UpdateError {
    public static void UpdateError(ChunkEvent.Load e){
        if(Minecraft.getMinecraft().thePlayer == null){
            LoginChat.hasRunThisSession = false;
            return; //Player not loaded yet
        }
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "Hypixel Tweaks ERROR"));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + "There was an error checking for updates"));
    }
}
