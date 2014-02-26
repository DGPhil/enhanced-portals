package uk.co.shadeddimensions.ep3.tileentity;

import java.util.ArrayList;

import cofh.api.energy.IEnergyHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.shadeddimensions.ep3.block.BlockStabilizer;
import uk.co.shadeddimensions.ep3.util.GeneralUtils;
import uk.co.shadeddimensions.ep3.util.WorldCoordinates;
import uk.co.shadeddimensions.ep3.util.WorldUtils;
import uk.co.shadeddimensions.library.util.ItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileStabilizer extends TileEP implements IEnergyHandler
{
    ChunkCoordinates mainBlock;
    int rows;
    boolean is3x3 = false;

    @SideOnly(Side.CLIENT)
    public boolean isFormed;

    public TileStabilizer()
    {
        mainBlock = null;
    }

    public boolean activate(EntityPlayer player)
    {
        if (worldObj.isRemote)
        {
            return true;
        }

        TileStabilizerMain main = getMainBlock();

        if (main != null)
        {
            return main.activate(player);
        }
        else
        {
            if (ItemHelper.isWrench(player.inventory.getCurrentItem()))
            {
                WorldCoordinates topLeft = getWorldCoordinates();

                while (topLeft.offset(ForgeDirection.WEST).getBlock() == BlockStabilizer.instance) // Get the westernmost block
                {
                    topLeft = topLeft.offset(ForgeDirection.WEST);
                }

                while (topLeft.offset(ForgeDirection.NORTH).getBlock() == BlockStabilizer.instance) // Get the northenmost block
                {
                    topLeft = topLeft.offset(ForgeDirection.NORTH);
                }

                while (topLeft.offset(ForgeDirection.UP).getBlock() == BlockStabilizer.instance) // Get the highest block
                {
                    topLeft = topLeft.offset(ForgeDirection.UP);
                }

                ArrayList<ChunkCoordinates> blocks = checkShapeThreeWide(topLeft); // 3x3

                if (blocks.isEmpty())
                {
                    blocks = checkShapeTwoWide(topLeft, true); // Try the 3x2 X axis

                    if (blocks.isEmpty())
                    {
                        blocks = checkShapeTwoWide(topLeft, false); // Try the 3x2 Z axis before failing
                    }
                }

                if (!blocks.isEmpty()) // success
                {
                    for (ChunkCoordinates c : blocks) // make sure we're not interrupting something
                    {
                        TileEntity tile = worldObj.getTileEntity(c.posX, c.posY, c.posZ);

                        if (tile instanceof TileStabilizer)
                        {
                            if (((TileStabilizer) tile).getMainBlock() != null)
                            {
                                TileStabilizerMain m = ((TileStabilizer) tile).getMainBlock();
                                m.deconstruct();
                            }
                        }
                        else if (tile instanceof TileStabilizerMain)
                        {
                            ((TileStabilizerMain) tile).deconstruct();
                            worldObj.setBlock(c.posX, c.posY, c.posZ, BlockStabilizer.instance, 0, 2);
                        }
                    }

                    for (ChunkCoordinates c : blocks)
                    {
                        TileEntity tile = worldObj.getTileEntity(c.posX, c.posY, c.posZ);

                        if (tile instanceof TileStabilizer)
                        {
                            TileStabilizer t = (TileStabilizer) tile;
                            t.mainBlock = topLeft;
                            WorldUtils.markForUpdate(t);
                        }
                    }

                    worldObj.setBlock(topLeft.posX, topLeft.posY, topLeft.posZ, BlockStabilizer.instance, 1, 3);

                    TileEntity tile = topLeft.getTileEntity();

                    if (tile instanceof TileStabilizerMain)
                    {
                        ((TileStabilizerMain) tile).setData(blocks, rows, is3x3);
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public void breakBlock(Block oldBlockID, int oldMetadata)
    {
        TileStabilizerMain main = getMainBlock();

        if (main == null)
        {
            return;
        }

        main.deconstruct();
    }

    ArrayList<ChunkCoordinates> checkShapeThreeWide(WorldCoordinates topLeft)
    {
        ArrayList<ChunkCoordinates> blocks = new ArrayList<ChunkCoordinates>();
        ChunkCoordinates heightChecker = new ChunkCoordinates(topLeft);
        rows = 0;

        while (worldObj.getBlock(heightChecker.posX, heightChecker.posY, heightChecker.posZ) == BlockStabilizer.instance)
        {
            heightChecker.posY--;
            rows++;
        }

        if (rows < 2)
        {
            rows = 0;
            return new ArrayList<ChunkCoordinates>();
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                for (int k = 0; k < rows; k++)
                {
                    if (worldObj.getBlock(topLeft.posX + i, topLeft.posY - k, topLeft.posZ + j) != BlockStabilizer.instance)
                    {
                        return new ArrayList<ChunkCoordinates>();
                    }

                    blocks.add(new ChunkCoordinates(topLeft.posX + i, topLeft.posY - k, topLeft.posZ + j));
                }
            }
        }

        is3x3 = true;
        return blocks;
    }

    ArrayList<ChunkCoordinates> checkShapeTwoWide(WorldCoordinates topLeft, boolean isX)
    {
        ArrayList<ChunkCoordinates> blocks = new ArrayList<ChunkCoordinates>();
        ChunkCoordinates heightChecker = new ChunkCoordinates(topLeft);
        rows = 0;

        while (worldObj.getBlock(heightChecker.posX, heightChecker.posY, heightChecker.posZ) == BlockStabilizer.instance)
        {
            heightChecker.posY--;
            rows++;
        }

        if (rows < 2)
        {
            rows = 0;
            return new ArrayList<ChunkCoordinates>();
        }

        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 2; j++)
            {
                for (int k = 0; k < rows; k++)
                {
                    if (worldObj.getBlock(topLeft.posX + (isX ? i : j), topLeft.posY - k, topLeft.posZ + (!isX ? i : j)) != BlockStabilizer.instance)
                    {
                        return new ArrayList<ChunkCoordinates>();
                    }

                    blocks.add(new ChunkCoordinates(topLeft.posX + (isX ? i : j), topLeft.posY - k, topLeft.posZ + (!isX ? i : j)));
                }
            }
        }

        is3x3 = false;
        return blocks;
    }

    /***
     * Gets the block that does all the processing for this multiblock. If that block is self, will return self.
     */
    public TileStabilizerMain getMainBlock()
    {
        if (mainBlock != null)
        {
            TileEntity tile = worldObj.getTileEntity(mainBlock.posX, mainBlock.posY, mainBlock.posZ);

            if (tile != null && tile instanceof TileStabilizerMain)
            {
                return (TileStabilizerMain) tile;
            }
        }

        return null;
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        NBTTagCompound tag = pkt.func_148857_g();
        isFormed = tag.getBoolean("formed");
        WorldUtils.markForUpdate(this);
    }
    
    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setBoolean("formed", mainBlock != null);
        
        return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 0, tag);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);
        mainBlock = GeneralUtils.loadChunkCoord(tag, "mainBlock");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);
        GeneralUtils.saveChunkCoord(tag, mainBlock, "mainBlock");
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate)
    {
        return getMainBlock().receiveEnergy(from, maxReceive, simulate);
    }

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate)
    {
        return getMainBlock().extractEnergy(from, maxExtract, simulate);
    }

    @Override
    public boolean canInterface(ForgeDirection from)
    {
        return mainBlock != null;
    }

    @Override
    public int getEnergyStored(ForgeDirection from)
    {
        return getMainBlock().getEnergyStored(from);
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from)
    {
        return getMainBlock().getMaxEnergyStored(from);
    }
}
