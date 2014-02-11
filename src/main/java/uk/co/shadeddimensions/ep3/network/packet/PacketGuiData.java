package uk.co.shadeddimensions.ep3.network.packet;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.nbt.NBTTagCompound;
import uk.co.shadeddimensions.ep3.client.gui.GuiPortalController;
import uk.co.shadeddimensions.ep3.tileentity.TileEP;
import uk.co.shadeddimensions.library.container.ContainerBase;
import cpw.mods.fml.common.network.ByteBufUtils;

public class PacketGuiData extends PacketEP
{
    NBTTagCompound tag;

    public PacketGuiData()
    {

    }

    public PacketGuiData(NBTTagCompound t)
    {
        tag = t;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        ByteBufUtils.writeTag(buffer, tag);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
    {
        tag = ByteBufUtils.readTag(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player)
    {
        Container container = ((EntityPlayer) player).openContainer;
        
        if (container != null && container instanceof ContainerBase)
        {
            ((TileEP) ((ContainerBase) container).object).packetGui(tag, (EntityPlayer) player);
        }
        else if (container == null || container instanceof ContainerPlayer)
        {
            if (tag.hasKey("controller"))
            {
                if (Minecraft.getMinecraft().currentScreen instanceof GuiPortalController)
                {
                    ((GuiPortalController) Minecraft.getMinecraft().currentScreen).setWarningMessage(tag.getInteger("controller"));
                }
            }
        }
    }

    @Override
    public void handleServerSide(EntityPlayer player)
    {
        Container container = ((EntityPlayer) player).openContainer;
        
        if (container != null && container instanceof ContainerBase)
        {
            ((TileEP) ((ContainerBase) container).object).packetGui(tag, (EntityPlayer) player);
        }
    }
}
