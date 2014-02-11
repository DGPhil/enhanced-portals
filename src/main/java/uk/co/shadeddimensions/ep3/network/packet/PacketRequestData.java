package uk.co.shadeddimensions.ep3.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;

public class PacketRequestData extends PacketEP
{
    int x, y, z;

    public PacketRequestData()
    {

    }

    public PacketRequestData(TileEP tile)
    {
        x = tile.xCoord;
        y = tile.yCoord;
        z = tile.zCoord;
    }
    
    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(x);
        buffer.writeInt(y);
        buffer.writeInt(z);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {

    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        TileEntity tile = ((EntityPlayer) player).worldObj.getTileEntity(x, y, z);

        if (tile != null && tile instanceof TileEP)
        {
            EnhancedPortals.packetPipeline.sendTo(new PacketTileUpdate((TileEP) tile), (EntityPlayerMP) player);
        }
    }
}
