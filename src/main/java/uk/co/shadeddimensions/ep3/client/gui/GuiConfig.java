package uk.co.shadeddimensions.ep3.client.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.Tessellator;

import org.lwjgl.opengl.GL11;

import uk.co.shadeddimensions.library.gui.GuiBase;
import uk.co.shadeddimensions.library.gui.element.ElementBase;
import uk.co.shadeddimensions.library.gui.element.ElementButton;
import uk.co.shadeddimensions.library.gui.element.ElementScrollBar;
import uk.co.shadeddimensions.library.gui.element.ElementScrollPanel;
import uk.co.shadeddimensions.library.gui.element.ElementText;
import uk.co.shadeddimensions.library.util.GuiUtils;

public class GuiConfig extends GuiBase
{
    GuiScreen parentScreen;

    public GuiConfig(GuiScreen parent)
    {
        parentScreen = parent;
    }

    @Override
    public void drawGuiBackgroundLayer(float f, int mouseX, int mouseY)
    {
        double left = 0, right = width, top = 35, b0 = 0, bottom = height - 50;
        Tessellator tessellator = Tessellator.instance;

        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f1 = 32.0F;
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(2105376);
        tessellator.addVertexWithUV((double)left, (double)bottom, 0.0D, (double)((float)left / f1), (double)((float)(bottom) / f1));
        tessellator.addVertexWithUV((double)right, (double)bottom, 0.0D, (double)((float)right / f1), (double)((float)(bottom) / f1));
        tessellator.addVertexWithUV((double)right, (double)top, 0.0D, (double)((float)right / f1), (double)((float)(top) / f1));
        tessellator.addVertexWithUV((double)left, (double)top, 0.0D, (double)((float)left / f1), (double)((float)(top) / f1));
        tessellator.draw();
        
        super.drawGuiBackgroundLayer(f, mouseX, mouseY);

        this.overlayBackground(0, 35, 255, 255);
        this.overlayBackground(height - 50, height, 255, 255);
    }

    @Override
    public void drawGuiForegroundLayer(int mouseX, int mouseY)
    {
        super.drawGuiForegroundLayer(mouseX, mouseY);

        getFontRenderer().drawStringWithShadow("EnhancedPortals 3 Configuration", GuiUtils.getCenteredOffset(this, "EnhancedPortals 3 Configuration", xSize), -guiTop + 20, 0xFFFFFF);
    }

    private void overlayBackground(int p_148136_1_, int p_148136_2_, int p_148136_3_, int p_148136_4_)
    {
        int left = 0;
        Tessellator tessellator = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(Gui.optionsBackground);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_I(4210752, p_148136_4_);
        tessellator.addVertexWithUV((double)left, (double)p_148136_2_, 0.0D, 0.0D, (double)((float)p_148136_2_ / f));
        tessellator.addVertexWithUV((double)(left + this.width), (double)p_148136_2_, 0.0D, (double)((float)this.width / f), (double)((float)p_148136_2_ / f));
        tessellator.setColorRGBA_I(4210752, p_148136_3_);
        tessellator.addVertexWithUV((double)(left + this.width), (double)p_148136_1_, 0.0D, (double)((float)this.width / f), (double)((float)p_148136_1_ / f));
        tessellator.addVertexWithUV((double)left, (double)p_148136_1_, 0.0D, 0.0D, (double)((float)p_148136_1_ / f));
        tessellator.draw();
    }

    @Override
    public void initGui()
    {
        super.initGui();
        ElementScrollPanel panel = new ElementScrollPanel(this, -90, -guiTop + 35, xSize * 2, height - 80);

        int y = 10;
        panel.addElement(new ElementText(this, 0, y, "General", null, 0xFFFFFF, true));
        panel.addElement(new ElementButton(this, 0, y + 15, 110, "glyphType", "Glyphs: Normal"));
        
        y += 50;
        panel.addElement(new ElementText(this, 0, y, "Portal", null, 0xFFFFFF, true));
        panel.addElement(new ElementButton(this, 0, y + 15, 110, "glyphType", "TODO: Sound Slider!"));
        panel.addElement(new ElementButton(this, 115, y + 15, 110, "destroyBlocks", "Destroy Blocks: YES"));
        panel.addElement(new ElementButton(this, 230, y + 15, 110, "forceOverlays", "Force Overlays: NO"));
        panel.addElement(new ElementButton(this, 0, y + 40, 110, "particles", "Particles: YES"));
        
        y += 70;
        panel.addElement(new ElementText(this, 0, y, "Power", null, 0xFFFFFF, true));
        panel.addElement(new ElementButton(this, 0, y + 15, 110, "powerRequired", "Required: YES"));
        panel.addElement(new ElementButton(this, 115, y + 15, 110, "glyphType", "TODO: Slider!"));
        
        y += 50;
        panel.addElement(new ElementText(this, 0, y, "Teleportation", null, 0xFFFFFF, true));
        panel.addElement(new ElementButton(this, 0, y + 15, 110, "fastCooldown", "Fast Cooldown: NO"));
        
        ElementBase lastElement = panel.getElements().get(panel.getElements().size() - 1);
        panel.addElement(new ElementText(this, 0, lastElement.getRelativeY() + lastElement.getHeight() + 10, "", null));
        addElement(panel);
        addElement(new ElementScrollBar(this, panel.getRelativeX() + panel.getWidth() + 5, -guiTop + 37, 5, panel.getHeight() - 7, panel));

        buttonList.add(new GuiButton(0, width / 2 - 100, height - 40, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            getMinecraft().currentScreen = parentScreen;
        }
        else if (button.id == 1)
        {
            String str = "Normal";

            if (button.displayString.contains(str))
            {
                str = "Alternate";
            }

            button.displayString = "Glyphs: " + str;
        }
    }

    @Override
    protected void keyTyped(char character, int index)
    {
        if (index == 1)
        {
            getMinecraft().currentScreen = parentScreen;
        }
    }
}
