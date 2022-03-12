package makamys.satchels.inventory;

import codechicken.lib.inventory.InventoryUtils;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemSatchel;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ContainerEquipment extends ContainerPlayerExtended {
    
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
        
        EntityPropertiesSatchels satchelProps = EntityPropertiesSatchels.fromPlayer(p_i1819_3_);
        
        addSlotToContainer(satchelSlot = new SlotCustom(satchelProps.equipment, 0, 115, 22).setBackgroundIconR(ItemSatchel.emptyIcon));
        addSlotToContainer(leftPouchSlot = new SlotCustom(satchelProps.equipment, 1, 101, 58).setBackgroundIconR(ItemPouch.emptyIcon));
        addSlotToContainer(rightPouchSlot = new SlotCustom(satchelProps.equipment, 2, 131, 58).setBackgroundIconR(ItemPouch.emptyIcon));
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);
        
        if (slot != null && slot.getHasStack()) {
            boolean inserted = false;
            
            ItemStack itemstack1 = slot.getStack();
            
            if(slot.slotNumber >= 9 && slot.slotNumber < 45) {
                EntityPropertiesSatchels satchelProps = EntityPropertiesSatchels.fromPlayer(p_82846_1_);
                
                int originalSize = itemstack1.stackSize;
                int left = InventoryUtils.insertItem(satchelProps.equipment, itemstack1, false);
                if(left != originalSize) {
                    itemstack1.stackSize = left;
                    inserted = true;
                }
            }
            
            if(inserted) {
                if (itemstack1.stackSize == 0) {
                    slot.putStack((ItemStack)null);
                } else {
                    slot.onSlotChanged();
                }

                slot.onPickupFromSlot(p_82846_1_, itemstack1);
            } else {
                return super.transferStackInSlot(p_82846_1_, p_82846_2_);
            }
        }

        return itemstack;
    }
}
