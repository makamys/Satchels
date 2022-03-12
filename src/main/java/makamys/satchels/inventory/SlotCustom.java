package makamys.satchels.inventory;

import java.util.function.Predicate;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class SlotCustom extends Slot {

    Predicate<ItemStack> predicate;
    
    int limit = -1;
    
    public SlotCustom(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_, Predicate<ItemStack> predicate, int stackLimit) {
        super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
        this.predicate = predicate;
        this.limit = stackLimit;
    }
    
    @Override
    public boolean isItemValid(ItemStack p_75214_1_) {
        return predicate.test(p_75214_1_);
    }
    
    @Override
    public int getSlotStackLimit() {
        return limit != -1 ? limit : super.getSlotStackLimit();
    }
    
    public SlotCustom setBackgroundIconR(IIcon icon) {
        super.setBackgroundIcon(icon);
        return this;
    }
    
}