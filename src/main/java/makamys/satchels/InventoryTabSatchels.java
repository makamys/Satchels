package makamys.satchels;

import net.minecraft.item.ItemStack;
import tconstruct.client.tabs.AbstractTab;
import makamys.satchels.Packets.MessageOpenContainer;

public class InventoryTabSatchels extends AbstractTab
{
    public InventoryTabSatchels()
    {
        super(0, 0, 0, new ItemStack(SatchelsItems.pouch));
    }

    @Override
    public void onTabClicked ()
    {
    	Satchels.networkWrapper.sendToServer(new MessageOpenContainer(GuiHandler.ID_EQUIPMENT));
    }

    @Override
    public boolean shouldAddToList ()
    {
        return true;
    }
}
