package makamys.satchel;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;

public class ContainerSatchel extends ContainerPlayer {

	public ContainerSatchel(EntityPlayer player) {
		super(player.inventory, !player.worldObj.isRemote, player);
		addSlotToContainer(new Slot(player.getInventoryEnderChest(), 0, -16, 10));
		
		for(Object slot : this.inventorySlots) {
			((Slot)slot).xDisplayPosition += 16;
		}
	}

}
