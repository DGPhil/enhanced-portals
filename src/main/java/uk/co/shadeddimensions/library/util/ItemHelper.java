package uk.co.shadeddimensions.library.util;

import uk.co.shadeddimensions.ep3.item.ItemLocationCard;
import uk.co.shadeddimensions.ep3.item.ItemPaintbrush;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import buildcraft.api.tools.IToolWrench;
import cofh.api.energy.IEnergyContainerItem;

public class ItemHelper
{
    public static boolean isEnergyContainerItem(ItemStack stack)
    {
        return stack != null && stack.getItem() instanceof IEnergyContainerItem;
    }

    public static boolean isWrench(ItemStack stack)
    {
        return stack == null ? false : stack.getItem() instanceof IToolWrench;
    }
    
    public static boolean isPaintbrush(ItemStack stack)
    {
        return stack == null ? false : stack.isItemEqual(new ItemStack(ItemPaintbrush.instance));
    }

    public static boolean isLocationCard(ItemStack stack)
    {
        return stack == null ? false : stack.isItemEqual(new ItemStack(ItemLocationCard.instance));
    }
}
