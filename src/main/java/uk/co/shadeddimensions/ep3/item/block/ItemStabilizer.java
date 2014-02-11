package uk.co.shadeddimensions.ep3.item.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import uk.co.shadeddimensions.ep3.block.BlockStabilizer;
import uk.co.shadeddimensions.ep3.lib.Localization;

public class ItemStabilizer extends ItemBlock
{
    public ItemStabilizer(Block par1)
    {
        super(par1);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4)
    {
        list.add(Localization.getBlockString("multiblockStructure"));
        list.add(EnumChatFormatting.DARK_GRAY + Localization.getBlockString("dbsSize"));
        list.add(EnumChatFormatting.DARK_GRAY + Localization.getBlockString("eachRowAddsToTheMaximum"));
        list.add(EnumChatFormatting.DARK_GRAY + Localization.getBlockString("activePortalConnections"));
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return BlockStabilizer.instance.getBlockTextureFromSide(0);
    }

    @Override
    public int getMetadata(int par1)
    {
        return par1;
    }
}
