package makamys.satchels;

import java.util.function.Predicate;

import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemSatchel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEquipment extends ContainerPlayer {
	
	Slot satchelSlot;
	Slot leftPouchSlot;
	Slot rightPouchSlot;

	public ContainerEquipment(InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer p_i1819_3_) {
		super(p_i1819_1_, p_i1819_2_, p_i1819_3_);
		
		for(int i = 0; i < 5; i++) {
			// disable crafting slots
			Slot slot = (Slot)inventorySlots.get(i);
			slot.xDisplayPosition = -1000;
		}
		
		EntityPropertiesSatchels satchelProps = (EntityPropertiesSatchels)p_i1819_3_.getExtendedProperties("satchels");
		
		Predicate<ItemStack> satchelPredicate = (stack) -> stack.getItem() instanceof ItemSatchel;
		Predicate<ItemStack> pouchPredicate = (stack) -> stack.getItem() instanceof ItemPouch;
		
		addSlotToContainer(satchelSlot = new CustomSlot(satchelProps.equipment, 0, 115, 22, satchelPredicate, 1));
		addSlotToContainer(leftPouchSlot = new CustomSlot(satchelProps.equipment, 1, 101, 58, pouchPredicate, 1));
		addSlotToContainer(rightPouchSlot = new CustomSlot(satchelProps.equipment, 2, 131, 58, pouchPredicate, 1));
	}
}
