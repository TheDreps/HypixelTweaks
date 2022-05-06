//package me.thedreps.hypixeltweaks.events;
//
//import me.thedreps.hypixeltweaks.KeyHandler;
//import net.minecraft.client.Minecraft;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import net.minecraftforge.fml.common.gameevent.TickEvent;
//import org.lwjgl.input.Keyboard;
//
//public class Walking {
//
//    @SubscribeEvent
//    public void onTickEvent(TickEvent.PlayerTickEvent e){
//        int key = KeyHandler.keyBindings[0].getKeyCode();
//        boolean isDown = Keyboard.isKeyDown(key);
//
//        if(Minecraft.getMinecraft().gameSettings.keyBindForward.isKeyDown()){
//            if(e.player.isSneaking()){
//                e.player.setSprinting(false);
//            }else{
//                e.player.setSprinting(!isDown);
//            }
//        }
//    }
//}
