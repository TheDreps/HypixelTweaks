package me.thedreps.hypixeltweaks.events;

import me.thedreps.hypixeltweaks.ConfigHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@SuppressWarnings("unused")
public class DeleteJson {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void deleteJSON(ClientChatReceivedEvent e) {
        if(ConfigHandler.enableJsonRemover) {
            String message = e.message.getUnformattedText();
            if(message.contains("{\"server\":")) {
                e.setCanceled(true);
            }
        }
    }


}
