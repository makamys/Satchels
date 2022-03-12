package makamys.satchels.inventory;

import java.util.function.Predicate;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerChestGeneric extends Container
{
    private IInventory chestInventory;
    private int rowSize;

    public ContainerChestGeneric(IInventory p_i1806_1_, IInventory p_i1806_2_, Predicate<ItemStack> acceptPredicate, int maxStackSize) {
        this(p_i1806_1_, p_i1806_2_, acceptPredicate, maxStackSize, 9);
    }
    
    public ContainerChestGeneric(IInventory p_i1806_1_, IInventory p_i1806_2_, Predicate<ItemStack> acceptPredicate, int maxStackSize, int rowSize)
    {
        this.chestInventory = p_i1806_2_;
        this.rowSize = rowSize = Math.min(rowSize, p_i1806_2_.getSizeInventory());
        if(p_i1806_2_.getSizeInventory() % rowSize != 0) {
            throw new IllegalArgumentException("Non-rectangular inventory. " + p_i1806_2_.getSizeInventory() + " slots, " + rowSize + " columns.");
        }
        
        int numRows = (int)Math.ceil(p_i1806_2_.getSizeInventory() / (float)rowSize);
        p_i1806_2_.openInventory();
        int i = (numRows - 4) * 18;
        int j;
        int k; 
        
        for (j = 0; j < numRows; ++j)
        {
            for (k = 0; k < rowSize; ++k)
            {
                if(k + j * rowSize < chestInventory.getSizeInventory()) {
                    this.addSlotToContainer(new SlotCustom(p_i1806_2_, k + j * rowSize, 8 + k * 18, 18 + j * 18, acceptPredicate, maxStackSize));
                }
            }
        }

        for (j = 0; j < 3; ++j)
        {
            for (k = 0; k < 9; ++k)
            {
                this.addSlotToContainer(new Slot(p_i1806_1_, k + j * 9 + 9, 8 + k * 18, 103 + j * 18 + i));
            }
        }

        for (j = 0; j < 9; ++j)
        {
            this.addSlotToContainer(new Slot(p_i1806_1_, j, 8 + j * 18, 161 + i));
        }
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_)
    {
        return this.chestInventory.isUseableByPlayer(p_75145_1_);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer p_82846_1_, int p_82846_2_)
    {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(p_82846_2_);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (p_82846_2_ < this.chestInventory.getSizeInventory())
            {
                if (!this.mergeItemStack(itemstack1, this.chestInventory.getSizeInventory(), this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 0, this.chestInventory.getSizeInventory(), false))
            {
                return null;
            }

            if (itemstack1.stackSize == 0)
            {
                slot.putStack((ItemStack)null);
            }
            else
            {
                slot.onSlotChanged();
            }
        }

        return itemstack;
    }

    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer p_75134_1_)
    {
        super.onContainerClosed(p_75134_1_);
        this.chestInventory.closeInventory();
    }

    /**
     * Return this chest container's <s>lower</s> UPPER chest inventory.
     */
    public IInventory getLowerChestInventory()
    {
        return this.chestInventory;
    }
    
    public int getRowSize() {
        return this.rowSize;
    }
}