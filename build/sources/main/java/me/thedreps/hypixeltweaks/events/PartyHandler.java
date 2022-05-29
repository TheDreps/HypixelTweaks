package me.thedreps.hypixeltweaks.events;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PartyHandler {

    @SubscribeEvent
    public void onPlayerChatEvent(ClientChatReceivedEvent e){

        String message = e.message.getUnformattedText();

    }




}