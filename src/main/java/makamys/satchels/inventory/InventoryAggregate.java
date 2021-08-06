package makamys.satchels.inventory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

/** Aggregate of multiple inventories */

public class InventoryAggregate implements IInventory {

    List<IInventory> inventories;
    
    public InventoryAggregate(IInventory...inventories) {
        this.inventories = Arrays.stream(inventories).collect(Collectors.toList());
    }
    
    @Override
    public int getSizeInventory() {
        return inventories.stream().mapToInt(i -> i.getSizeInventory()).sum();
    }

    @Override
    public ItemStack getStackInSlot(int globalIdx) {
        Pair<IInventory, Integer> localIdx = toLocalIdx(globalIdx);
        if(localIdx != null) {
            return localIdx.getLeft().getStackInSlot(localIdx.getRight());
        } else {
            return null;
        }
    }
    
    public Pair<IInventory, Integer> toLocalIdx(int globalIdx) {
        int invStartGlobal = 0;
        for(int i = 0; i < inventories.size(); i++) {
            IInventory inv = inventories.get(i);
            if(globalIdx >= invStartGlobal && globalIdx < invStartGlobal + inv.getSizeInventory()) {
                return Pair.of(inv, globalIdx - invStartGlobal);
            }
            invStartGlobal += inv.getSizeInventory();
        }
        return Pair.of(null, -1);
    }

    @Override
    public ItemStack decrStackSize(int globalIdx, int arg) {
        Pair<IInventory, Integer> localIdx = toLocalIdx(globalIdx);
        if(localIdx != null) {
            return localIdx.getLeft().decrStackSize(localIdx.getRight(), arg);
        } else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int globalIdx) {
        Pair<IInventory, Integer> localIdx = toLocalIdx(globalIdx);
        if(localIdx != null) {
            return localIdx.getLeft().getStackInSlotOnClosing(localIdx.getRight());
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int globalIdx, ItemStack arg) {
        Pair<IInventory, Integer> localIdx = toLocalIdx(globalIdx);
        if(localIdx != null) {
            localIdx.getLeft().setInventorySlotContents(localIdx.getRight(), arg);
        }
    }

    @Override
    public String getInventoryName() {
        return "aggregate{" + String.join(",", inventories.stream().map(i -> i.getInventoryName()).toArray(String[]::new)) + "}";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return inventories.stream().anyMatch(i -> i.hasCustomInventoryName());
    }

    @Override
    public int getInventoryStackLimit() {
        return inventories.stream().mapToInt(i -> i.getInventoryStackLimit()).min().getAsInt();
    }

    @Override
    public void markDirty() {
        inventories.stream().forEach(i -> i.markDirty());
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return inventories.stream().allMatch(i -> i.isUseableByPlayer(player));
    }

    @Override
    public void openInventory() {
        inventories.stream().forEach(i -> i.openInventory());
    }

    @Override
    public void closeInventory() {
        inventories.stream().forEach(i -> i.closeInventory());
    }

    @Override
    public boolean isItemValidForSlot(int globalIdx, ItemStack arg) {
        Pair<IInventory, Integer> localIdx = toLocalIdx(globalIdx);
        if(localIdx != null) {
            return localIdx.getLeft().isItemValidForSlot(localIdx.getRight(), arg);
        } else {
            return false;
        }
    }

}
