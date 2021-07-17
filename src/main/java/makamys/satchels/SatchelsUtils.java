package makamys.satchels;

import net.minecraft.inventory.IInventory;

public class SatchelsUtils {
	
	public static boolean isInventoryEmpty(IInventory inventory) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			if(inventory.getStackInSlot(i) != null) {
				return false;
			}
		}
		return true;
	}
	
	public static void clearInventory(IInventory inventory) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			inventory.setInventorySlotContents(i, null);
		}
	}
	
}
