package uk.co.shadeddimensions.library.gui.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.shadeddimensions.library.util.ComparableItemStack;

/**
 * Slot which is restricted to a specific item and maximum amount.
 * 
 * @author King Lemming
 * 
 */
public class SlotSpecificItem extends Slot
{
    final ComparableItemStack stack;
    int slotStackLimit = -1;

    public SlotSpecificItem(IInventory inventory, int slotIndex, int x, int y, ItemStack stack)
    {
        super(inventory, slotIndex, x, y);

        this.stack = new ComparableItemStack(stack);
    }

    @Override
    public int getSlotStackLimit()
    {
        if (slotStackLimit <= 0)
        {
            return inventory.getInventoryStackLimit();
        }
        return slotStackLimit;
    }

    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return this.stack.isItemEqual(new ComparableItemStack(stack));
    }

    public SlotSpecificItem setSlotStackLimit(int slotStackLimit)
    {
        this.slotStackLimit = slotStackLimit;
        return this;
    }
}
