package makamys.satchels.inventory;

import codechicken.lib.inventory.InventorySimple;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotDisabled extends Slot {

	private static final IInventory EMPTY_INVENTORY = new InventorySimple(1, "container.emptyInventory");
	
	public Slot original;
	
	public SlotDisabled(Slot original) {
		super(EMPTY_INVENTORY, 0, -1000, -1000);
		this.original = original;
		if(original != null) {
			this.slotNumber = original.slotNumber;
		}
	}
	
	@Override
	public boolean canTakeStack(EntityPlayer player) {
		return false;
	}
	
	public boolean isItemValid(ItemStack stack) {
        return false;
    }
	
	@Override
	public boolean func_111238_b() { // canBeHovered
		return false;
	}

}
