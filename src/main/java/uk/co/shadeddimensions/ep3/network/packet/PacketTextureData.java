package uk.co.shadeddimensions.ep3.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.network.ClientProxy;
import uk.co.shadeddimensions.ep3.tileentity.portal.TileDiallingDevice;
import uk.co.shadeddimensions.ep3.util.PortalTextureManager;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketTextureData extends PacketEP
{
    int id, x, y, z;
    PortalTextureManager ptm;

    public PacketTextureData()
    {
        id = -1;
    }

    public PacketTextureData(int i, int x, int y, int z)
    {
        id = i;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PacketTextureData(PortalTextureManager t)
    {
        id = -1;
        ptm = t;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        if (id > -1)
        {
            buffer.writeBoolean(true);
            buffer.writeInt(id);
            buffer.writeInt(x);
            buffer.writeInt(y);
            buffer.writeInt(z);
        }
        else
        {
            buffer.writeBoolean(false);
            NBTTagCompound data = new NBTTagCompound();

            if (ptm != null)
            {
                ptm.writeToNBT(data, "Texture");
            }
            
            ByteBufUtils.writeTag(buffer, data);
        }
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        if (buffer.readBoolean())
        {
            id = buffer.readInt();
            x = buffer.readInt();
            y = buffer.readInt();
            z = buffer.readInt();
        }
        else
        {
            NBTTagCompound data = ByteBufUtils.readTag(buffer);

            ptm = new PortalTextureManager();

            if (data.hasKey("Texture"))
            {
                ptm.readFromNBT(data, "Texture");
            }

            id = -1;
        }
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        if (id != -1)
        {
            return;
        }

        ClientProxy.dialEntryTexture = ptm;
        FMLClientHandler.instance().getClient().currentScreen.initGui(); // Force the UI elements to update to reflect the data that should be shown
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        TileDiallingDevice dial = (TileDiallingDevice) ((EntityPlayer) player).worldObj.getTileEntity(x, y, z);

        if (dial != null)
        {
            PortalTextureManager PTM = dial.glyphList.get(id).texture;
            EnhancedPortals.packetPipeline.sendTo(new PacketTextureData(PTM), (EntityPlayerMP) player);
        }
    }
}
