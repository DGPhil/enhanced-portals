package uk.co.shadeddimensions.ep3.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;

public class PacketTileUpdate extends PacketEP
{
    ByteBuf buff;
    TileEP tile;
    int x, y, z;
    
    public PacketTileUpdate()
    {
        
    }
    
    public PacketTileUpdate(TileEP tile)
    {
        this.tile = tile;
    }
    
    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        buffer.writeInt(tile.xCoord);
        buffer.writeInt(tile.yCoord);
        buffer.writeInt(tile.zCoord);
        
        tile.packetFill(buffer);
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
            ((TileEP) tile).packetUse(buff);
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        // We're never going to update the server with a packet like this.
    }
}
