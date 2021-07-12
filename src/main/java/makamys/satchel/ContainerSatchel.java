package makamys.satchel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSatchel extends ContainerPlayer {

	public ContainerSatchel(EntityPlayer player) {
		super(player.inventory, !player.worldObj.isRemote, player);
		
		int slotIdx = 0;
		
		for(int row = 0; row < 3; row++) {
			addSlotToContainer(new Slot(player.getInventoryEnderChest(), slotIdx++, -16+2, 10 + row * 18));
		}
		for(int row = 0; row < 3; row++) {
			addSlotToContainer(new Slot(player.getInventoryEnderChest(), slotIdx++, 8 + 9 * 18 + 6-2, 10 + row * 18));
		}
		
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player.getInventoryEnderChest(), slotIdx++, 8 + i * 18, 66));
		}
		
		for(int i = 0; i < this.inventorySlots.size(); i++) {
			Slot slot = (Slot)this.inventorySlots.get(i);
			slot.xDisplayPosition += 16;
			if(i >= 9) {
				slot.yDisplayPosition += 18;
			}
		}
	}

}
