package me.thedreps.hypixeltweaks.events;

import me.thedreps.hypixeltweaks.HypixelTweaks;
import me.thedreps.hypixeltweaks.utility.LogHelper;
import me.thedreps.hypixeltweaks.utility.PlayerToXYZ;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

@SuppressWarnings("unused")
public class AutowhoManager {

    public static void startAutowho(int delay){
        ticksLeft = delay;
        countingTicksAutowho = true;
        HypixelTweaks.isAwaitingAutowho = false;
    }

    private static boolean countingTicksAutowho = false;
    private static int ticksLeft;

    @SubscribeEvent
    public void autowhoTimer(TickEvent.ClientTickEvent e) {
        if(countingTicksAutowho) {
            if(ticksLeft > 0) {
                ticksLeft--;

            }else {
                autowho();
                countingTicksAutowho = false;
            }
        }
    }

    private void autowho() {
        String me = Minecraft.getMinecraft().thePlayer.getName();
        List<EntityPlayer> players = Minecraft.getMinecraft().theWorld.playerEntities;
        StringBuilder sb = new StringBuilder();
        sb.append("ONLINE: ");

        double myX = 0;
        double myZ = 0;

        for (EntityPlayer player : players) {
            if(player.getName().equalsIgnoreCase(me)){
                PlayerToXYZ pXYZ = new PlayerToXYZ(player);
                myX = pXYZ.getX();
                myZ = pXYZ.getZ();
            }
        }

        for (EntityPlayer player : players) {
            PlayerToXYZ pXYZ = new PlayerToXYZ(player);
            if(pXYZ.getX() == myX && pXYZ.getZ() == myZ && !(player.getName().equalsIgnoreCase(me))){
                continue;
            }
            sb.append(player.getDisplayNameString()).append(", ");
        }
        String online = sb.toString().trim();
        online = online.substring(0, online.length()-1);

        LogHelper.info("[CHAT] " + online);

    }






}
