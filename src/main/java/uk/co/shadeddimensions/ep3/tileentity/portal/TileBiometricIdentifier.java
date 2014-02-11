package uk.co.shadeddimensions.ep3.tileentity.portal;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.network.GuiHandler;
import uk.co.shadeddimensions.ep3.network.packet.PacketTileGui;
import uk.co.shadeddimensions.ep3.util.EntityData;
import uk.co.shadeddimensions.ep3.util.WorldUtils;
import uk.co.shadeddimensions.library.util.ItemHelper;
import cpw.mods.fml.common.network.ByteBufUtils;

public class TileBiometricIdentifier extends TileFrame implements IInventory
{
    public long lastUpdateTime;
    public ArrayList<EntityData> entityList = new ArrayList<EntityData>();
    public boolean defaultPermissions = true, isActive = true;
    ItemStack[] inventory = new ItemStack[1];
    
    @Override
    public boolean activate(EntityPlayer player, ItemStack stack)
    {
    	if (player.isSneaking())
		{
			return false;
		}
    	
        TileController controller = getPortalController();

        if (stack != null && controller != null && controller.isFinalized())
        {
            if (ItemHelper.isWrench(stack) && !player.isSneaking())
            {
                GuiHandler.openGui(player, this, GuiHandler.BIOMETRIC_IDENTIFIER);
                return true;
            }
            else if (ItemHelper.isPaintbrush(stack))
            {
                GuiHandler.openGui(player, controller, GuiHandler.TEXTURE_FRAME);
                return true;
            }
        }

        return false;
    }

    public void applyBiometricFilters(int slotIndex, ItemStack s)
    {
        NBTTagCompound t = s.getTagCompound();
        NBTTagList l = t.getTagList("entities", 10);

        for (int i = 0; i < l.tagCount(); i++)
        {
            NBTTagCompound tag = (NBTTagCompound) l.getCompoundTagAt(i);
            EntityData entity = new EntityData(tag.getString("Name"), EntityData.getClassFromID(tag.getInteger("ID")), false, (byte) 0);
            entityList.add(entity);
            lastUpdateTime = System.currentTimeMillis();
        }
        
        markDirty();
    }

    public boolean canEntityTravel(Entity entity)
    {
        if (!isActive)
        {
            return true;
        }

        boolean skippedAll = true, decision = defaultPermissions;

        for (EntityData data : entityList)
        {
            int var = data.isEntityAcceptable(entity);

            if (var != -1)
            {
                skippedAll = false;
                decision = var == 1;
            }
        }

        return skippedAll ? defaultPermissions : decision;
    }

    @Override
    public boolean canUpdate()
    {
        return true;
    }

    public ArrayList<EntityData> copySendingEntityTypes()
    {
        ArrayList<EntityData> list = new ArrayList<EntityData>();

        for (EntityData data : entityList)
        {
            list.add(new EntityData(data.EntityDisplayName, data.EntityClass, data.disallow, data.checkType));
        }

        return list;
    }

    @Override
    public ItemStack decrStackSize(int i, int j)
    {
        ItemStack stack = getStackInSlot(i);

        if (stack != null)
        {
            if (stack.stackSize <= j)
            {
                setInventorySlotContents(i, null);
            }
            else
            {
                stack = stack.splitStack(j);

                if (stack.stackSize == 0)
                {
                    setInventorySlotContents(i, null);
                }
            }
        }
        
        return stack;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public String getInventoryName()
    {
        return "tile.ep3.frame.scanner.name";
    }

    /* IInventory */
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i)
    {
        return inventory[i];
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i)
    {
        return inventory[i];
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack)
    {
        return ItemHelper.isEnergyContainerItem(itemstack);
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer)
    {
        return true;
    }

    public void onNeighborBlockChange(Block block)
    {
        isActive = WorldUtils.getHighestPowerState(this) == 0;
    }

    @Override
    public void packetGui(NBTTagCompound tag, EntityPlayer player)
    {
    	if (tag.hasKey("invert"))
        {
            int id = tag.getInteger("invert");

            entityList.get(id).disallow = !entityList.get(id).disallow;
            lastUpdateTime = System.currentTimeMillis();
        }
        else if (tag.hasKey("type"))
        {
            int id = tag.getInteger("type");

            entityList.get(id).checkType++;

            if (entityList.get(id).checkType > 2)
            {
                entityList.get(id).checkType = 0;
                lastUpdateTime = System.currentTimeMillis();
            }
        }
        else if (tag.hasKey("remove"))
        {
            int id = tag.getInteger("remove");

            entityList.remove(id);
            lastUpdateTime = System.currentTimeMillis();
        }
        else if (tag.hasKey("default"))
        {
            defaultPermissions = !defaultPermissions;
        }
    	
    	EnhancedPortals.packetPipeline.sendTo(new PacketTileGui(this), (EntityPlayerMP) player);
    }

    @Override
    public void packetGuiFill(ByteBuf buffer)
    {
        buffer.writeBoolean(isActive);
        buffer.writeBoolean(defaultPermissions);
        buffer.writeByte(entityList.size());

        for (EntityData d : entityList)
        {
            ByteBufUtils.writeUTF8String(buffer, d.EntityDisplayName);
            buffer.writeInt(EntityData.getEntityID(d.EntityClass));
            buffer.writeBoolean(d.disallow);
            buffer.writeByte(d.checkType);
        }
    }

    @Override
    public void packetGuiUse(ByteBuf buffer)
    {
        isActive = buffer.readBoolean();
        defaultPermissions = buffer.readBoolean();
        entityList.clear();
        lastUpdateTime = System.currentTimeMillis();

        byte size = buffer.readByte();

        for (int i = 0; i < size; i++)
        {
            entityList.add(new EntityData(ByteBufUtils.readUTF8String(buffer), EntityData.getClassFromID(buffer.readInt()), buffer.readBoolean(), buffer.readByte()));
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tag)
    {
        super.readFromNBT(tag);

        isActive = tag.getBoolean("isActive");
        defaultPermissions = tag.getBoolean("notFoundSend");

        NBTTagList l = tag.getTagList("sendingEntityTypes", 10);
        for (int i = 0; i < l.tagCount(); i++)
        {
            EntityData d = new EntityData().readFromNBT((NBTTagCompound) l.getCompoundTagAt(i));

            if (d != null && d.EntityClass != null)
            {
                entityList.add(d);
            }
        }

        if (tag.hasKey("Inventory"))
        {
            NBTTagList tagList = tag.getTagList("Inventory", 10);

            for (int i = 0; i < tagList.tagCount(); i++)
            {
                NBTTagCompound t2 = (NBTTagCompound) tagList.getCompoundTagAt(i);
                byte slot = t2.getByte("Slot");

                if (slot >= 0 && slot < inventory.length)
                {
                    inventory[slot] = ItemStack.loadItemStackFromNBT(t2);
                }
            }
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack)
    {
        inventory[i] = itemstack;
    }

    @Override
    public void writeToNBT(NBTTagCompound tag)
    {
        super.writeToNBT(tag);

        tag.setBoolean("isActive", isActive);
        tag.setBoolean("notFoundSend", defaultPermissions);

        NBTTagList t = new NBTTagList();
        for (EntityData d : entityList)
        {
            NBTTagCompound tagCompound = new NBTTagCompound();
            d.saveToNBT(tagCompound);
            t.appendTag(tagCompound);
        }

        tag.setTag("sendingEntityTypes", t);

        NBTTagList itemList = new NBTTagList();

        for (int i = 0; i < inventory.length; i++)
        {
            ItemStack stack = inventory[i];

            if (stack != null)
            {
                NBTTagCompound t3 = new NBTTagCompound();
                t3.setByte("Slot", (byte) i);
                stack.writeToNBT(t3);
                itemList.appendTag(t3);
            }
        }

        tag.setTag("Inventory", itemList);
    }

    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }

    @Override
    public void openInventory()
    {
        
    }

    @Override
    public void closeInventory()
    {
        
    }
}
