package me.thedreps.hypixeltweaks.update;

import me.thedreps.hypixeltweaks.events.LoginChat;
import me.thedreps.hypixeltweaks.reference.UpdateInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.world.ChunkEvent;

public class UpdateSuccess {
    public static void updateSuccess(ChunkEvent.Load e){
        if(Minecraft.getMinecraft().thePlayer == null){
            LoginChat.hasRunThisSession = false;
            return; //Player not loaded yet
        }

        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + UpdateInfo.updateLN1));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.RED + UpdateInfo.updateLN2 + " " + UpdateInfo.updateArray[0] + ":"));
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(EnumChatFormatting.AQUA + UpdateInfo.githubReleases));
    }
}
