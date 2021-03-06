package uk.co.shadeddimensions.ep3.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.ForgeDirection;
import uk.co.shadeddimensions.ep3.item.ItemGoggles;
import uk.co.shadeddimensions.ep3.network.CommonProxy;
import uk.co.shadeddimensions.ep3.portal.GlyphIdentifier;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

public class GeneralUtils
{
    public static boolean hasEnergyCost()
    {
        return CommonProxy.requirePower;
    }
    
    public static int getPowerMultiplier()
    {
        return hasEnergyCost() ? CommonProxy.powerMultiplier : 0;
    }

    public static boolean isWearingGoggles()
    {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT)
        {
            if (Minecraft.getMinecraft().thePlayer == null)
            {
                return false;
            }

            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.armorItemInSlot(3);
            return stack != null && stack.itemID == ItemGoggles.ID;
        }
        
        return false;
    }

    public static ChunkCoordinates loadChunkCoord(NBTTagCompound tagCompound, String string)
    {
        if (tagCompound.getTag(string) == null)
        {
            return null;
        }

        NBTTagCompound t = (NBTTagCompound) tagCompound.getTag(string);

        return t.getInteger("Y") == -1 ? null : new ChunkCoordinates(t.getInteger("X"), t.getInteger("Y"), t.getInteger("Z"));
    }

    public static ArrayList<ChunkCoordinates> loadChunkCoordList(NBTTagCompound tag, String name)
    {
        ArrayList<ChunkCoordinates> list = new ArrayList<ChunkCoordinates>();

        NBTTagList tagList = tag.getTagList(name);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound t = (NBTTagCompound) tagList.tagAt(i);

            list.add(new ChunkCoordinates(t.getInteger("X"), t.getInteger("Y"), t.getInteger("Z")));
        }

        return list;
    }

    public static WorldCoordinates loadWorldCoord(NBTTagCompound tagCompound, String string)
    {
        if (tagCompound.getTag(string) == null)
        {
            return null;
        }

        NBTTagCompound t = (NBTTagCompound) tagCompound.getTag(string);

        return t.getInteger("Y") == -1 ? null : new WorldCoordinates(t.getInteger("X"), t.getInteger("Y"), t.getInteger("Z"), t.getInteger("D"));
    }

    public static ArrayList<WorldCoordinates> loadWorldCoordList(NBTTagCompound tag, String name)
    {
        ArrayList<WorldCoordinates> list = new ArrayList<WorldCoordinates>();

        NBTTagList tagList = tag.getTagList(name);

        for (int i = 0; i < tagList.tagCount(); i++)
        {
            NBTTagCompound t = (NBTTagCompound) tagList.tagAt(i);

            list.add(new WorldCoordinates(t.getInteger("X"), t.getInteger("Y"), t.getInteger("Z"), t.getInteger("D")));
        }

        return list;
    }

    public static ChunkCoordinates offset(ChunkCoordinates coord, ForgeDirection dir)
    {
        return new ChunkCoordinates(coord.posX + dir.offsetX, coord.posY + dir.offsetY, coord.posZ + dir.offsetZ);
    }

    public static ChunkCoordinates readChunkCoord(DataInputStream stream) throws IOException
    {
        ChunkCoordinates c = new ChunkCoordinates(stream.readInt(), stream.readInt(), stream.readInt());

        return c.posY == -1 ? null : c;
    }

    public static GlyphIdentifier readGlyphIdentifier(DataInputStream stream) throws IOException
    {
        return new GlyphIdentifier(stream.readUTF());
    }

    public static void saveChunkCoord(NBTTagCompound tagCompound, ChunkCoordinates c, String string)
    {
        if (c == null)
        {
            return;
        }

        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("X", c.posX);
        t.setInteger("Y", c.posY);
        t.setInteger("Z", c.posZ);

        tagCompound.setTag(string, t);
    }

    public static void saveChunkCoordList(NBTTagCompound tag, List<ChunkCoordinates> list, String name)
    {
        NBTTagList tagList = new NBTTagList();

        for (ChunkCoordinates c : list)
        {
            NBTTagCompound t = new NBTTagCompound();
            t.setInteger("X", c.posX);
            t.setInteger("Y", c.posY);
            t.setInteger("Z", c.posZ);

            tagList.appendTag(t);
        }

        tag.setTag(name, tagList);
    }

    public static void saveWorldCoord(NBTTagCompound tagCompound, WorldCoordinates c, String string)
    {
        if (c == null)
        {
            return;
        }

        NBTTagCompound t = new NBTTagCompound();
        t.setInteger("X", c.posX);
        t.setInteger("Y", c.posY);
        t.setInteger("Z", c.posZ);
        t.setInteger("D", c.dimension);

        tagCompound.setTag(string, t);
    }

    public static void saveWorldCoordList(NBTTagCompound tag, List<WorldCoordinates> list, String name)
    {
        NBTTagList tagList = new NBTTagList();

        for (WorldCoordinates c : list)
        {
            NBTTagCompound t = new NBTTagCompound();
            t.setInteger("X", c.posX);
            t.setInteger("Y", c.posY);
            t.setInteger("Z", c.posZ);
            t.setInteger("D", c.dimension);

            tagList.appendTag(t);
        }

        tag.setTag(name, tagList);
    }

    public static void writeChunkCoord(DataOutputStream stream, ChunkCoordinates c) throws IOException
    {
        if (c == null)
        {
            stream.writeInt(0);
            stream.writeInt(-1);
            stream.writeInt(0);
        }
        else
        {
            stream.writeInt(c.posX);
            stream.writeInt(c.posY);
            stream.writeInt(c.posZ);
        }
    }

    public static void writeGlyphIdentifier(DataOutputStream stream, GlyphIdentifier i) throws IOException
    {
        stream.writeUTF(i == null ? "" : i.getGlyphString());
    }
}
