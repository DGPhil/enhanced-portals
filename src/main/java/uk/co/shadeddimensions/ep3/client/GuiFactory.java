package uk.co.shadeddimensions.ep3.client;

import java.util.HashSet;
import java.util.Set;

import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.client.gui.GuiConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.client.IModGuiFactory;

public class GuiFactory implements IModGuiFactory
{
    @Override
    public void initialize(Minecraft minecraftInstance)
    {
        
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass()
    {
        return GuiConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories()
    {
        Set<RuntimeOptionCategoryElement> set = new HashSet<RuntimeOptionCategoryElement>();
        set.add(new RuntimeOptionCategoryElement("EnhancedPortals", "Testing"));
        return set;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
