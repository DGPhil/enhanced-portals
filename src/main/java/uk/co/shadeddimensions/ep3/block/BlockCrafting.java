package uk.co.shadeddimensions.ep3.block;

import javax.swing.Icon;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class BlockCrafting extends Block
{
    public static BlockCrafting instance;
    public static final int UNFILLED_STABILIZER = 0;

    IIcon dbsEmpty;
    
    public BlockCrafting()
    {
        super(Material.rock);
        instance = this;
        setCreativeTab(Reference.creativeTab);
        setHardness(5);
        setResistance(2000);
        setStepSound(soundTypeMetal);
    }

    @Override
    public IIcon getIcon(IBlockAccess blockAccess, int x, int y, int z, int side)
    {
        int meta = blockAccess.getBlockMetadata(x, y, z);

        if (meta == 0)
        {
            return dbsEmpty;
        }

        return super.getIcon(blockAccess, x, y, z, side);
    }

    @Override
    public IIcon getIcon(int side, int meta)
    {
        if (meta == 0)
        {
            return dbsEmpty;
        }

        return super.getIcon(side, meta);
    }

    @Override
    public void registerBlockIcons(IIconRegister register)
    {
        dbsEmpty = register.registerIcon("enhancedportals:dbs_empty");
    }
}
