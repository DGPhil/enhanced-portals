package uk.co.shadeddimensions.ep3.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import uk.co.shadeddimensions.ep3.lib.Localization;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class ItemEntityCard extends Item
{
    public static ItemEntityCard instance;
    
    IIcon texture;

    public ItemEntityCard()
    {
        super();
        instance = this;
        setCreativeTab(Reference.creativeTab);
        setUnlocalizedName("entityCard");
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer par2EntityPlayer, List list, boolean par4)
    {
        NBTTagCompound tag = stack.getTagCompound();

        if (tag != null)
        {
            list.add(EnumChatFormatting.GRAY + Localization.getItemString("contains"));

            NBTTagList tagList = tag.getTagList("entities", 9);

            for (int i = 0; i < 5; i++)
            {
                if (i >= tagList.tagCount())
                {
                    break;
                }

                NBTTagCompound t = (NBTTagCompound) tagList.getCompoundTagAt(i);
                String s = t.getString("Name");

                if (s.contains("item.item."))
                {
                    s = StatCollector.translateToLocal(s.replace("item.item.", "item.") + ".name");
                }
                else if (s.contains("item.tile."))
                {
                    s = StatCollector.translateToLocal(s.replace("item.tile.", "tile.") + ".name");
                }

                list.add(EnumChatFormatting.DARK_GRAY + " " + s);
            }

            if (tagList.tagCount() > 5)
            {
                list.add(EnumChatFormatting.GRAY + String.format(Localization.getItemString("andMore"), tagList.tagCount() - 5));
            }
        }
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        texture = par1IconRegister.registerIcon("enhancedportals:idCard");
    }
}
