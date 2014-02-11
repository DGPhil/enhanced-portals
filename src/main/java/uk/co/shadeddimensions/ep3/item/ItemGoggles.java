package uk.co.shadeddimensions.ep3.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class ItemGoggles extends ItemArmor
{
    public static ItemGoggles instance;
    
    public ItemGoggles()
    {
        super(ArmorMaterial.CLOTH, EnhancedPortals.proxy.glassesRenderIndex, 0);
        instance = this;
        setCreativeTab(Reference.creativeTab);
        setUnlocalizedName("glasses");
        setTextureName("enhancedportals:glasses");
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return "enhancedportals:textures/models/armor/glasses.png";
    }

    @Override
    public boolean isBookEnchantable(ItemStack itemstack1, ItemStack itemstack2)
    {
        return false;
    }
}
