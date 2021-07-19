package makamys.satchels;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import codechicken.lib.vec.Vector3;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSatchels extends ContainerPlayer {

	public List<Slot> leftPouchSlots = new ArrayList<>();
	public List<Slot> rightPouchSlots = new ArrayList<>();
	public List<Slot> satchelSlots = new ArrayList<>();
	
	public boolean dirty;
	
	public EntityPropertiesSatchels satchelProps;
	
	List<Pair<Integer, Integer>> originalSlotPositions;
	
	public ContainerSatchels(EntityPlayer player) {
		super(player.inventory, !player.worldObj.isRemote, player);
		
		satchelProps = (EntityPropertiesSatchels)player.getExtendedProperties("satchels");
		
		int bottomY = 138-18;
		
		for(int row = 0; row < EntityPropertiesSatchels.POUCH_MAX_SLOTS; row++) {
			Slot slot = new Slot(satchelProps.leftPouch, row, -16+2+2, bottomY - row * 18);
			leftPouchSlots.add(slot);
			addSlotToContainer(slot);
		}
		for(int row = 0; row < EntityPropertiesSatchels.POUCH_MAX_SLOTS; row++) {
			Slot slot = new Slot(satchelProps.rightPouch, row, 8 + 9 * 18 + 6-2-2, bottomY - row * 18);
			rightPouchSlots.add(slot);
			addSlotToContainer(slot);
		}
		
		IInventory satchelInv = satchelProps.satchel;
		for(int i = 0; i < EntityPropertiesSatchels.SATCHEL_MAX_SLOTS; i++) {
			Slot slot = new Slot(satchelInv, i, 8 + i * 18, 66);
			satchelSlots.add(slot);
			addSlotToContainer(slot);
		}
		
		originalSlotPositions = new ArrayList<>();
		for(int i = 0; i < this.inventorySlots.size(); i++) {
			Slot slot = (Slot)this.inventorySlots.get(i);
			originalSlotPositions.add(Pair.of(slot.xDisplayPosition, slot.yDisplayPosition));
		}
		redoSlots();
	}
	
	public void redoSlots() {
		for(int i = 0; i < satchelSlots.size(); i++) {
			setEnabled(satchelSlots, i, satchelProps.hasSatchel());
		}
		
		for(int i = 0; i < leftPouchSlots.size(); i++) {
			setEnabled(leftPouchSlots, i, i < satchelProps.getLeftPouchSlotCount());
			setEnabled(rightPouchSlots, i, i < satchelProps.getRightPouchSlotCount());	
		}
		
		for(int i = 0; i < originalSlotPositions.size(); i++) {
			Slot slot = (Slot)this.inventorySlots.get(i);
			if(!(slot instanceof SlotDisabled)) {
				Pair<Integer, Integer> originalPosition = originalSlotPositions.get(i);
				slot.xDisplayPosition = originalPosition.getLeft() + 16;
				slot.yDisplayPosition = originalPosition.getRight() + (i >= 9 && satchelProps.hasSatchel() ? 18 : 0);
			}
		}
	}
	
	public void setEnabled(List<Slot> list, int index, boolean enabled) {
		Slot slot = list.get(index);
		
		Slot newSlot = null;
		if(!(slot instanceof SlotDisabled) && !enabled) {
			if(slot.getHasStack()) {
				if(!satchelProps.player.worldObj.isRemote) {
					SatchelsUtils.dropItemStack(slot.getStack(), satchelProps.player.worldObj, Vector3.fromEntityCenter(satchelProps.player));
				}
				slot.putStack(null);
			}
			
			newSlot = new SlotDisabled(slot);
		} else if((slot instanceof SlotDisabled) && enabled) {
			newSlot = ((SlotDisabled)slot).original;
		}
		
		if(newSlot != null) {
			list.set(index, newSlot);
			inventorySlots.set(slot.slotNumber, newSlot);
		}
	}
	
	public List<Slot> getEnabledLeftPouchSlots() {
		return leftPouchSlots.subList(0, satchelProps.getLeftPouchSlotCount());
	}
	
	
	public List<Slot> getEnabledRightPouchSlots() {
		return rightPouchSlots.subList(0, satchelProps.getRightPouchSlotCount());
	}

}
