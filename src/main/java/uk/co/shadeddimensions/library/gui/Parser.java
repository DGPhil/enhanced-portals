package uk.co.shadeddimensions.library.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.shadeddimensions.library.gui.element.ElementBase;
import uk.co.shadeddimensions.library.gui.element.ElementBaseContainer;
import uk.co.shadeddimensions.library.gui.element.ElementCrafting;
import uk.co.shadeddimensions.library.gui.element.ElementScrollPanel;
import uk.co.shadeddimensions.library.gui.element.ElementText;

/***
 * Turns a string into multiple GUI elements for use within a {@link ElementBaseContainer} or {@link ElementScrollPanel}.
 * 
 * @author Alz454
 */
public class Parser
{
    FontRenderer fontRenderer;
    IGuiBase parentGui;
    ArrayList<ElementBase> parsedElements;
    int maxWidth, currentX, currentY;

    public Parser(IGuiBase parent)
    {
        parsedElements = new ArrayList<ElementBase>();
        parentGui = parent;
        maxWidth = -1;
        currentX = currentY = 0;
        fontRenderer = Minecraft.getMinecraft().fontRenderer;
    }

    public ArrayList<ElementBase> parse(String string)
    {
        return parsedElements;
    }

    @SuppressWarnings("unchecked")
    private void parseText(String string)
    {
        if (maxWidth != -1 && fontRenderer.getStringWidth(string) > maxWidth)
        {
            List<String> list = fontRenderer.listFormattedStringToWidth(string, maxWidth);

            for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); currentY += fontRenderer.FONT_HEIGHT)
            {
                parsedElements.add(new ElementText(parentGui, currentX, currentY, iterator.next(), null, 0xFFFFFF, false));
            }
        }
        else
        {
            parsedElements.add(new ElementText(parentGui, currentX, currentY, string, null, 0xFFFFFF, false));
            currentX += fontRenderer.getStringWidth(string);
        }
    }

    public Parser setMaxWidth(int width)
    {
        maxWidth = width;
        return this;
    }
}
