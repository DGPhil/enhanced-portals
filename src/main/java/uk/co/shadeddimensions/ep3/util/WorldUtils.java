package uk.co.shadeddimensions.ep3.util;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;

public class WorldUtils
{
    public static void markForUpdate(TileEntity tile)
    {
        markForUpdate(tile.getWorldObj(), tile.xCoord, tile.yCoord, tile.zCoord);
    }

    public static void markForUpdate(World world, int posX, int posY, int posZ)
    {
        world.markBlockForUpdate(posX, posY, posZ);
    }
    
    public static ChunkCoordinates getChunkCoordinatesOffset(ChunkCoordinates c, ForgeDirection d)
    {
        if (c == null)
        {
            return null;
        }
        
        return new ChunkCoordinates(c.posX + d.offsetX, c.posY + d.offsetY, c.posZ + d.offsetZ);
    }
    
    public static ChunkCoordinates getChunkCoordinatesOffset(TileEP tile, ForgeDirection d)
    {
        return getChunkCoordinatesOffset(tile.getChunkCoordinates(), d);
    }
    
    public static TileEntity getTileEntity(World world, ChunkCoordinates c)
    {
        if (c == null)
        {
            return null;
        }
        
        return world.getBlockTileEntity(c.posX, c.posY, c.posZ);
    }
    
    public static TileEntity getTileEntity(World world, ChunkCoordinates c, ForgeDirection d)
    {
        if (c == null)
        {
            return null;
        }
        
        return world.getBlockTileEntity(c.posX + d.offsetX, c.posY + d.offsetY, c.posZ + d.offsetZ);
    }
    
    public static TileEntity getTileEntity(TileEP tile, ForgeDirection d)
    {
        return getTileEntity(tile.worldObj, tile.getChunkCoordinates(), d);
    }
    
    public static int getHighestPowerState(TileEP tile)
    {
        byte highest = 0;

        for (int i = 0; i < 6; i++)
        {
            ChunkCoordinates c = getChunkCoordinatesOffset(tile.getChunkCoordinates(), ForgeDirection.getOrientation(i));
            byte power = (byte) tile.worldObj.getIndirectPowerLevelTo(c.posX, c.posY, c.posZ, i);

            if (power > highest)
            {
                highest = power;
            }
        }

        return highest;
    }

    public static boolean isAirBlock(World world, ChunkCoordinates c, ForgeDirection d)
    {
        return isAirBlock(world, getChunkCoordinatesOffset(c, d));
    }
    
    public static boolean isAirBlock(TileEP tile, ForgeDirection orientation)
    {
        return isAirBlock(tile.worldObj, tile.getChunkCoordinates(), orientation);
    }

    public static boolean isAirBlock(World world, ChunkCoordinates c)
    {
        return world.isAirBlock(c.posX, c.posY, c.posZ);
    }
}
