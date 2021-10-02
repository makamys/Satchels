package makamys.satchels.inventory;

import static makamys.satchels.gui.GuiSatchelsInventory.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Iterables;
import com.google.common.collect.Sets;

import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import makamys.satchels.SatchelsUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSatchels extends ContainerPlayerExtended {
    
    public List<Slot> leftPouchSlots = new ArrayList<>();
    public List<Slot> rightPouchSlots = new ArrayList<>();
    public List<Slot> satchelSlots = new ArrayList<>();
    
    public boolean dirty;
    
    public EntityPropertiesSatchels satchelProps;
    
    Map<Slot, Pair<Integer, Integer>> originalSlotPositions;
    
    private boolean shiftArmorSlots;
    
    private boolean enableExtra;
    
    public ContainerSatchels(EntityPlayer player) {
        super(player.inventory, !player.worldObj.isRemote, player);
        
        satchelProps = EntityPropertiesSatchels.fromPlayer(player);
        
        originalSlotPositions = new HashMap<>();
        redoSlots(false);
    }
    
    public void redoSlots() {
        Satchels.LOGGER.debug("redoSlots " + enableExtra);
        removeAllExtraSlots();
        
        if(enableExtra) {
            List<Slot> moddedSlots = inventorySlots.size() > 45 ? new ArrayList<>(inventorySlots.subList(45, inventorySlots.size())) : new ArrayList<>();
            inventorySlots.removeAll(moddedSlots);
            // we move modded slots (e.g. TrashSlot) after ours, to keep our slot number always the same
            
            int bottomY = 138-18;
            
            for(int row = 0; row < EntityPropertiesSatchels.POUCH_MAX_SLOTS; row++) {
                Slot slot = new SlotCustom(satchelProps.leftPouch, row, -16+2+4, bottomY - row * 18, EntityPropertiesSatchels.satchelsSlotPredicate, 64);
                leftPouchSlots.add(slot);
                addSlotToContainer(slot);
                setEnabled(leftPouchSlots, row, row < satchelProps.getLeftPouchSlotCount());
            }
            for(int row = 0; row < EntityPropertiesSatchels.POUCH_MAX_SLOTS; row++) {
                Slot slot = new SlotCustom(satchelProps.rightPouch, row, 8 + 9 * 18 + 6-2-4, bottomY - row * 18, EntityPropertiesSatchels.satchelsSlotPredicate, 64);
                rightPouchSlots.add(slot);
                addSlotToContainer(slot);
                setEnabled(rightPouchSlots, row, row < satchelProps.getRightPouchSlotCount());
            }
            
            IInventory satchelInv = satchelProps.satchel;
            for(int i = 0; i < EntityPropertiesSatchels.SATCHEL_MAX_SLOTS; i++) {
                Slot slot = new SlotCustom(satchelInv, i, 8 + i * 18, 66, EntityPropertiesSatchels.satchelsSlotPredicate, 64);
                satchelSlots.add(slot);
                addSlotToContainer(slot);
                setEnabled(satchelSlots, i, satchelProps.hasSatchel());
            }
            
            inventorySlots.addAll(moddedSlots);
        }
        
        shiftArmorSlots = leftPouchSlots.stream().anyMatch(s -> SatchelsUtils.isPointInRange(s.yDisplayPosition, playerY, playerY + playerH));
        
        for(Object slotObj : this.inventorySlots) {
            Slot slot = (Slot)slotObj;
            if(!(slot instanceof SlotDisabled)) {
                Pair<Integer, Integer> originalPosition = originalSlotPositions.get(slot);
                if(originalPosition == null) {
                    originalPosition = Pair.of(slot.xDisplayPosition, slot.yDisplayPosition);
                    originalSlotPositions.put(slot, originalPosition);
                }
                slot.xDisplayPosition = originalPosition.getLeft() + 16;
                slot.yDisplayPosition = originalPosition.getRight() + (slot.slotNumber >= 9 && satchelProps.hasSatchel() ? 18 : 0);
                
                if(slot.slotNumber >= 5 && slot.slotNumber < 9){
                    slot.xDisplayPosition += getArmorXOffset();
                }
            }
        }
        fixSlotNumbers();
    }
    
    public void redoSlots(boolean enableExtra) {
        this.enableExtra = enableExtra;
        redoSlots();
    }
    
    public void setEnabled(List<Slot> list, int index, boolean enabled) {
        Slot slot = list.get(index);
        
        Slot newSlot = null;
        if(!(slot instanceof SlotDisabled) && !enabled) {
            if(slot.getHasStack()) {
                if(!satchelProps.player.worldObj.isRemote) {
                    satchelProps.player.func_146097_a(slot.getStack(), true, false);
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
    
    /** MUST be followed by a call to fixSlotNumbers() */
    private void removeAllExtraSlots() {
        Set<Slot> extraSlots = Sets.newHashSet(Iterables.concat(leftPouchSlots, rightPouchSlots, satchelSlots));
        for(int i = 0; i < inventorySlots.size(); i++) {
            Slot slot = (Slot)inventorySlots.get(i);
            if(extraSlots.contains(slot)) {
                inventorySlots.remove(i);
                inventoryItemStacks.remove(i);
                originalSlotPositions.remove(slot);
                i--;
            }
        }
        leftPouchSlots.clear();
        rightPouchSlots.clear();
        satchelSlots.clear();
    }
    
    private void fixSlotNumbers() {
        for(int i = 0; i < inventorySlots.size(); i++) {
            ((Slot)inventorySlots.get(i)).slotNumber = i;
        }
    }
    
    public List<Slot> getEnabledLeftPouchSlots() {
        return leftPouchSlots.subList(0, satchelProps.getLeftPouchSlotCount());
    }
    
    
    public List<Slot> getEnabledRightPouchSlots() {
        return rightPouchSlots.subList(0, satchelProps.getRightPouchSlotCount());
    }
    
    public int getArmorXOffset() {
        return shiftArmorSlots ? 2 : 0;
    }
    
    public List<Slot> getExtraSlots(){
        return StreamSupport.stream(Iterables.concat(satchelSlots, leftPouchSlots, rightPouchSlots).spliterator(), false)
                .filter(s -> !(s instanceof SlotDisabled))
                .collect(Collectors.toList());
    }

}
