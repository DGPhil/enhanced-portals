package uk.co.shadeddimensions.ep3.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class ItemPaintbrush extends Item
{
    public static ItemPaintbrush instance;

    public ItemPaintbrush()
    {
        super();
        instance = this;
        setCreativeTab(Reference.creativeTab);
        setUnlocalizedName("nanobrush");
        setTextureName("enhancedportals:paintbrush");
        setMaxStackSize(1);
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
    {
        return true;
    }
}
