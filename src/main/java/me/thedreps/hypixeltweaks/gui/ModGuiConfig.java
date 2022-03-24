package me.thedreps.hypixeltweaks.gui;

import me.thedreps.hypixeltweaks.reference.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;

import static me.thedreps.hypixeltweaks.ConfigHandler.getConfig;


public class ModGuiConfig extends GuiConfig{

	public ModGuiConfig(GuiScreen guiScreen) {
		super(guiScreen,
                new ConfigElement(getConfig().getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(),
                Reference.MOD_ID,
                false,
                false,
                GuiConfig.getAbridgedConfigPath(getConfig().toString()));
	}
}
