package uk.co.shadeddimensions.ep3.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import uk.co.shadeddimensions.ep3.item.ItemWrench;
import uk.co.shadeddimensions.ep3.lib.Reference;

public class CreativeTabEP3 extends CreativeTabs
{
    public CreativeTabEP3()
    {
        super(Reference.ID);
    }

    @Override
    public Item getTabIconItem()
    {
        return ItemWrench.instance;
    }
}
