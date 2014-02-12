package uk.co.shadeddimensions.ep3.tileentity;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChunkCoordinates;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.network.packet.PacketRequestData;
import uk.co.shadeddimensions.ep3.util.WorldCoordinates;

public class TileEP extends TileEntity
{
    @Override
    public boolean canUpdate()
    {
        return false;
    }

    public ChunkCoordinates getChunkCoordinates()
    {
        return new ChunkCoordinates(xCoord, yCoord, zCoord);
    }

    public WorldCoordinates getWorldCoordinates()
    {
        return new WorldCoordinates(getChunkCoordinates(), worldObj.provider.dimensionId);
    }

    public void packetFill(ByteBuf buffer)
    {

    }

    public void packetGui(NBTTagCompound tag, EntityPlayer player)
    {

    }

    public void packetGuiFill(ByteBuf buffer)
    {

    }

    public void packetGuiUse(ByteBuf buffer)
    {

    }

    public void packetUse(ByteBuf buffer)
    {

    }

    @Override
    public void validate()
    {
        super.validate();

        if (worldObj.isRemote)
        {
            EnhancedPortals.packetPipeline.sendToServer(new PacketRequestData(this));
        }
    }
}
