package uk.co.shadeddimensions.ep3.tileentity.portal;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.network.packet.PacketTileUpdate;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;
import uk.co.shadeddimensions.ep3.util.WorldUtils;

public class TilePortalPart extends TileEP
{
    ChunkCoordinates portalController;
    TileController cachedController;

    public boolean activate(EntityPlayer player, ItemStack stack)
    {
        return false;
    }

    public void breakBlock(Block oldBlock, int oldMeta)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.connectionTerminate();
        }
    }

    public TileController getPortalController()
    {
        if (cachedController != null)
        {
            return cachedController;
        }

        TileEntity tile = WorldUtils.getTileEntity(worldObj, portalController);

        if (tile != null && tile instanceof TileController)
        {
            cachedController = (TileController) tile;
            return cachedController;
        }

        return null;
    }

    /**
     * Called when this block is placed in the world.
     * 
     * @param entity
     * @param stack
     */
    public void onBlockPlaced(EntityLivingBase entity, ItemStack stack)
    {
        for (int i = 0; i < 6; i++)
        {
            TileEntity tile = WorldUtils.getTileEntity(this, ForgeDirection.getOrientation(i));

            if (tile != null && tile instanceof TilePortalPart)
            {
                ((TilePortalPart) tile).onNeighborPlaced(entity, xCoord, yCoord, zCoord);
            }
        }
    }

    /**
     * Called when a portal part gets placed next to this one. Is used to notify
     * the Portal Controller to dismantle the structure.
     * 
     * @param x
     * @param y
     * @param z
     */
    public void onNeighborPlaced(EntityLivingBase entity, int x, int y, int z)
    {
        TileController controller = getPortalController();

        if (controller != null)
        {
            controller.deconstruct();
        }
    }

    @Override
    public void packetFill(ByteBuf buffer)
    {
        if (portalController == null)
        {
            buffer.writeBoolean(false);
        }
        else
        {
            buffer.writeBoolean(true);
            buffer.writeInt(portalController.posX);
            buffer.writeInt(portalController.posY);
            buffer.writeInt(portalController.posZ);
        }
    }

    @Override
    public void packetUse(ByteBuf buffer)
    {
        if (buffer.readBoolean())
        {
            portalController = new ChunkCoordinates(buffer.readInt(), buffer.readInt(), buffer.readInt());
        }
        else
        {
            portalController = null;
        }

        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);

        if (compound.hasKey("Controller"))
        {
            NBTTagCompound controller = compound.getCompoundTag("Controller");
            portalController = new ChunkCoordinates(controller.getInteger("X"), controller.getInteger("Y"), controller.getInteger("Z"));
        }
    }

    /**
     * Sets the Portal Controller to the specified coordinates, and sends an update packet.
     * @param c
     */
    public void setPortalController(ChunkCoordinates c)
    {
        portalController = c;
        markDirty();
        EnhancedPortals.packetPipeline.sendToAllAround(new PacketTileUpdate(this), this);
    }

    @Override
    public void writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);

        if (portalController != null)
        {
            NBTTagCompound controller = new NBTTagCompound();
            controller.setInteger("X", portalController.posX);
            controller.setInteger("Y", portalController.posY);
            controller.setInteger("Z", portalController.posZ);
            compound.setTag("Controller", controller);
        }
    }
}
