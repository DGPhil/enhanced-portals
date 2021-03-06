package uk.co.shadeddimensions.ep3.client.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.shadeddimensions.ep3.item.ItemLocationCard;
import uk.co.shadeddimensions.library.util.ItemHelper;

public class SlotDBS extends Slot
{
    public SlotDBS(IInventory inventory, int x, int y, int z)
    {
        super(inventory, x, y, z);
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return ItemHelper.isEnergyContainerItem(stack) || stack.itemID == ItemLocationCard.ID;
    }
}
