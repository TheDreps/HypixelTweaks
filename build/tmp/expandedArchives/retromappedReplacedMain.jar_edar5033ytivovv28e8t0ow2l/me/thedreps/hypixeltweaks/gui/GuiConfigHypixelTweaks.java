package me.thedreps.hypixeltweaks.gui;

import java.util.List;

import me.thedreps.hypixeltweaks.HypixelTweaks;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

public class GuiConfigHypixelTweaks extends GuiConfig{

	public GuiConfigHypixelTweaks(GuiScreen parent) {
		super(parent, new ConfigElement(HypixelTweaks.config.getCategory(Configuration.CATEGORY_GENERAL))
				.getChildElements(),
				HypixelTweaks.MODID,
				false,
				false,
				"Choose the features you like");
		titleLine2 = HypixelTweaks.config.getConfigFile().getAbsolutePath();
		
	}
	
	@Override
    public void func_73866_w_()
    {
        // You can add buttons and initialize fields here
        super.func_73866_w_();
    }

    
    @Override
    public void func_73863_a(int mouseX, int mouseY, float partialTicks)
    {
        // You can do things like create animations, draw additional elements, etc. here
        super.func_73863_a(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void func_146284_a(GuiButton button)
    {
        // You can process any additional buttons you may have added here
        super.func_146284_a(button);
    }
	

}
