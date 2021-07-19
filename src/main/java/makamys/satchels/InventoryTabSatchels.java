package makamys.satchels;

import makamys.satchels.Satchels.MessageOpenContainer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import tconstruct.client.tabs.AbstractTab;
import tconstruct.client.tabs.TabRegistry;

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
