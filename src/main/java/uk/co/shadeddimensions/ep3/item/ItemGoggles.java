package uk.co.shadeddimensions.ep3.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import uk.co.shadeddimensions.ep3.EnhancedPortals;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class ItemGoggles extends ItemArmor
{
    public static ItemGoggles instance;
    IIcon overlay;
    
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
    public int getColor(ItemStack par1ItemStack)
    {
        NBTTagCompound nbttagcompound = par1ItemStack.getTagCompound();

        if (nbttagcompound == null)
        {
            return 0xFFFFFF;
        }
        else
        {
            NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("display");
            return nbttagcompound1 == null ? 0xFFFFFF : (nbttagcompound1.hasKey("color", 3) ? nbttagcompound1.getInteger("color") : 0xFFFFFF);
        }
    }
    
    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }
    
    @Override
    public void registerIcons(IIconRegister register)
    {
        super.registerIcons(register);
        overlay = register.registerIcon("enhancedportals:glasses_1");
    }
    
    @Override
    public IIcon getIconFromDamageForRenderPass(int damage, int pass)
    {
        if (pass == 1)
        {
            return overlay;
        }
        
        return super.getIconFromDamageForRenderPass(damage, pass);
    }
}
