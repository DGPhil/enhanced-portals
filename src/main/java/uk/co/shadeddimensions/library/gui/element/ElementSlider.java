package uk.co.shadeddimensions.library.gui.element;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import uk.co.shadeddimensions.library.gui.IGuiBase;

public class ElementSlider extends ElementButton
{
    ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    String initialText;
    float sliderValue;
    
    public ElementSlider(IGuiBase parent, int x, int y, int w, String id, String text, float initialValue)
    {
        super(parent, x, y, w, id, text);
        initialText = text;
        sliderValue = initialValue;
    }

    @Override
    public void draw()
    {
        if (this.visible)
        {
            displayText = initialText + ": " + (int) (sliderValue * 100) + "%";
            gui.getTextureManager().bindTexture(buttonTextures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(posX, posY, 0, 46, sizeX / 2, sizeY);
            this.drawTexturedModalRect(posX + sizeX / 2, posY, 199 - sizeX / 2, 46, 1 + sizeX / 2, sizeY);
            int l = 14737632;

            if (isDisabled())
            {
                l = -6250336;
            }
            else if (intersectsWith(gui.getMouseX(), gui.getMouseY()))
            {
                l = 16777120;
            }

            gui.getFontRenderer().drawStringWithShadow(displayText, posX + sizeX / 2 - gui.getFontRenderer().getStringWidth(displayText) / 2, posY + (sizeY - 8) / 2, l);
        }
    }
}
