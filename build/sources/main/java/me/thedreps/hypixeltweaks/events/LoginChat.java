package me.thedreps.hypixeltweaks.events;

import me.thedreps.hypixeltweaks.ConfigHandler;
import me.thedreps.hypixeltweaks.update.UpdateError;
import me.thedreps.hypixeltweaks.reference.UpdateInfo;
import me.thedreps.hypixeltweaks.update.UpdateSuccess;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")

public class LoginChat {
    public static boolean hasRunThisSession = false;


    @SubscribeEvent
    public void onPlayerLogin(ChunkEvent.Load e){
        if(!hasRunThisSession){
            hasRunThisSession = true;
            if(ConfigHandler.updateCheck){
                if(UpdateInfo.updateErred){
                    UpdateError.UpdateError(e);
                }else if(UpdateInfo.updateAvailable){
                    UpdateSuccess.updateSuccess(e);
                }
            }
        }
    }
}
