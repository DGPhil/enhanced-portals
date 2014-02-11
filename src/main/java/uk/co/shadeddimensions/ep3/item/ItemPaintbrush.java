package uk.co.shadeddimensions.ep3.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class ItemPaintbrush extends Item
{
    public static ItemPaintbrush instance;
    
    public static IIcon texture;

    public ItemPaintbrush()
    {
        super();
        instance = this;
        setCreativeTab(Reference.creativeTab);
        setUnlocalizedName("nanobrush");
        setMaxStackSize(1);
    }

    @Override
    public IIcon getIconFromDamage(int par1)
    {
        return texture;
    }

    @Override
    public void registerIcons(IIconRegister register)
    {
        texture = register.registerIcon("enhancedportals:paintbrush");
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player)
    {
        return true;
    }
}
