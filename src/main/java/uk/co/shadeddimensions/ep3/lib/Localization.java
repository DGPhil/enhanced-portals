package uk.co.shadeddimensions.ep3.lib;

import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class Localization
{
    public static String getBlockString(String s)
    {
        return StatCollector.translateToLocal(Reference.ID + ".block." + s);
    }

    public static String getChatString(String s)
    {
        return StatCollector.translateToLocal(Reference.ID + ".chat." + s);
    }

    public static String getGuiString(String s)
    {
        return StatCollector.translateToLocal(Reference.ID + ".gui." + s).replace("<N>", "\n").replace("<MODVERSION>", Reference.VERSION);
    }

    public static String getItemString(String s)
    {
        return StatCollector.translateToLocal(Reference.ID + ".item." + s);
    }

    public static String getErrorString(String s)
    {
        return EnumChatFormatting.RED + StatCollector.translateToLocal(Reference.ID + ".error.prefix") + EnumChatFormatting.WHITE + StatCollector.translateToLocal(Reference.ID + ".error." + s);
    }

    public static String getSuccessString(String s)
    {
        return EnumChatFormatting.GREEN + StatCollector.translateToLocal(Reference.ID + ".success.prefix") + EnumChatFormatting.WHITE + StatCollector.translateToLocal(Reference.ID + ".success." + s);
    }
}
