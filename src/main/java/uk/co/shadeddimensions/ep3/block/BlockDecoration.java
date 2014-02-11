package uk.co.shadeddimensions.ep3.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.shadeddimensions.ep3.lib.Reference;
import uk.co.shadeddimensions.library.ct.ConnectedTextures;

public class BlockDecoration extends Block
{
    public static BlockDecoration instance;
    
    public static final int BLOCK_TYPES = 2;
    ConnectedTextures[] connectedTextures;

    public BlockDecoration()
    {
        super(Material.rock);
        instance = this;
        setHardness(3);
        setStepSound(soundTypeMetal);
        setCreativeTab(Reference.creativeTab);
    }

    @Override
    public int damageDropped(int par1)
    {
        return par1;
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int face)
    {
        int meta = blockAccess.getBlockMetadata(x, y, z);
        return meta == 0 ? connectedTextures[0].getIconForSide(blockAccess, x, y, z, face) : meta == 1 ? connectedTextures[1].getIconForSide(blockAccess, x, y, z, face) : null;
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        return meta == 0 ? connectedTextures[0].getBaseIcon() : meta == 1 ? connectedTextures[1].getBaseIcon() : null;
    }

    @Override
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
    {
        return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
    }
    
    @Override
    public void getSubBlocks(Item item, CreativeTabs tab, List list)
    {
        for (int i = 0; i < BLOCK_TYPES; i++)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        connectedTextures = new ConnectedTextures[2];
        connectedTextures[0] = BlockFrame.connectedTextures.copy(this, 0);
        connectedTextures[1] = BlockStabilizer.connectedTextures.copy(this, 1);
    }
}
