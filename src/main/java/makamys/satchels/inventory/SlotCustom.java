package makamys.satchels.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotCustom extends Slot {
    
    public SlotCustom(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
    }
    
    @Override
    public boolean isItemValid(ItemStack stack) {
        return inventory.isItemValidForSlot(this.getSlotIndex(), stack);
    }
    
    public SlotCustom setBackgroundIconR(IIcon icon) {
        super.setBackgroundIcon(icon);
        return this;
    }
    
}
