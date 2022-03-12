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
        this(original, false);
    }
    
    public SlotDisabled(Slot original, boolean copy) {
        super(!copy ? EMPTY_INVENTORY : original.inventory, !copy ? 0 : original.getSlotIndex(), !copy ? -1000 : original.xDisplayPosition, !copy ? -1000 : original.yDisplayPosition);
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
