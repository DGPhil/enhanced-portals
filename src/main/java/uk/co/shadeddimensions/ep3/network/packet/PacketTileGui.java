package uk.co.shadeddimensions.ep3.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;

public class PacketTileGui extends PacketTileUpdate
{
    public PacketTileGui()
    {

    }

    public PacketTileGui(TileEP tile)
    {
        super(tile);
    }
    
    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(tile.xCoord);
        buffer.writeInt(tile.yCoord);
        buffer.writeInt(tile.zCoord);
        
        tile.packetGuiFill(buffer);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        x = buffer.readInt();
        y = buffer.readInt();
        z = buffer.readInt();
        buff = buffer;
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        TileEntity tile = player.worldObj.getTileEntity(x, y, z);
        
        if (tile != null && tile instanceof TileEP)
        {
            ((TileEP) tile).packetGuiUse(buff);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // We're never going to update the server with a packet like this.
    }
}
