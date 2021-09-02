package makamys.satchels.inventory;

import codechicken.lib.inventory.InventorySimple;
import net.minecraft.item.ItemStack;

public class InventorySimpleNotifying extends InventorySimple {

	public Runnable callback;
	
	public InventorySimpleNotifying(int size, Runnable callback) {
		super(size);
		this.callback = callback;
	}
	
	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		boolean changed = false;
		if(!ItemStack.areItemStacksEqual(getStackInSlot(slot), stack)) {
			changed = true;
		}
		super.setInventorySlotContents(slot, stack);
		if(changed && callback != null) {
			callback.run();
		}
	};

}
