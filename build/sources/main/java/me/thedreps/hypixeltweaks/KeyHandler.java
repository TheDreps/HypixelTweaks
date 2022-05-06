package me.thedreps.hypixeltweaks;

import me.thedreps.hypixeltweaks.reference.Reference;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.input.Keyboard;

public class KeyHandler {

    public static KeyBinding[] keyBindings;

    public KeyHandler() {

        keyBindings = new KeyBinding[1];

        // instantiate the key bindings
        keyBindings[0] = new KeyBinding("Walk", Keyboard.KEY_CAPITAL, Reference.MOD_ID);

        ClientRegistry.registerKeyBinding(keyBindings[0]);
    }
}
