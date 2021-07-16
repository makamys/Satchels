package makamys.satchels;

import codechicken.lib.inventory.InventorySimple;
import net.minecraft.item.ItemStack;

public class InventorySimpleNotifying extends InventorySimple {

	public Runnable callback;
	
	public InventorySimpleNotifying(int size) {
		super(size);
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		super.setInventorySlotContents(slot, stack);
		if(callback != null) {
			callback.run();
		}
	};

}
