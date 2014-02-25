package uk.co.shadeddimensions.ep3.tileentity.portal;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import uk.co.shadeddimensions.ep3.lib.Localization;
import uk.co.shadeddimensions.ep3.network.GuiHandler;
import uk.co.shadeddimensions.library.util.ItemHelper;

public class TileNetworkInterface extends TileFrame //implements IPeripheral
{
	@Override
	public boolean activate(EntityPlayer player, ItemStack stack)
	{
		if (player.isSneaking())
		{
			return false;
		}

		TileController controller = getPortalController();

		if (stack != null && controller != null && controller.isFinalized())
		{
			if (ItemHelper.isWrench(stack) && !player.isSneaking())
			{
				if (controller.getIdentifierUnique() == null)
				{
					if (!worldObj.isRemote)
					{
					    player.addChatMessage(new ChatComponentText(Localization.getErrorString("noUidSet")));
					}
				}
				else
				{
					GuiHandler.openGui(player, controller, GuiHandler.NETWORK_INTERFACE);
				}
			}
			else if (ItemHelper.isPaintbrush(stack))
			{
				GuiHandler.openGui(player, controller, GuiHandler.TEXTURE_FRAME);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean canUpdate()
	{
		return true;
	}

    @Override
    public void addDataToPacket(NBTTagCompound tag)
    {
        
    }

    @Override
    public void onDataPacket(NBTTagCompound tag)
    {
        
    }

	/*@Override
    public String getType()
    {
        return "Network Interface";
    }

    @Override
    public String[] getMethodNames()
    {
        return new String[] { "dial", "terminate" };
    }

    @Override
    public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws Exception
    {
        if (method == 0) // dial
        {
            getPortalController().connectionDial();
        }
        else if (method == 1) // terminate
        {
            getPortalController().connectionTerminate();
        }

        return null;
    }

    @Override
    public boolean canAttachToSide(int side)
    {
        return true;
    }

    @Override
    public void attach(IComputerAccess computer)
    {

    }

    @Override
    public void detach(IComputerAccess computer)
    {

    }*/
}
