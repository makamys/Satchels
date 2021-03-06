package makamys.satchels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.google.common.primitives.Ints;

import codechicken.lib.inventory.InventoryRange;
import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import makamys.mclib.ccl.inventory.InventoryUtils2;
import makamys.satchels.inventory.ContainerSatchels;
import makamys.satchels.inventory.InventoryAggregate;
import makamys.satchels.inventory.InventorySimpleNotifying;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemSatchel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityPropertiesSatchels implements IExtendedEntityProperties {
    
    public static final int SATCHEL_MAX_SLOTS = 9;
    public static final int POUCH_MAX_SLOTS = 8;
    public static final int POUCH_INITIAL_SLOTS = 3;
    
    private static final int SLOT_SATCHEL = 0;
    private static final int SLOT_LEFT_POUCH = 1;
    private static final int SLOT_RIGHT_POUCH = 2;
    
    public static final Predicate<ItemStack> satchelsSlotPredicate = (stack) -> ConfigSatchels.backpackHelper.isAllowed(stack);
    
    public InventorySimpleNotifying equipment = new InventorySimpleNotifying(3, (stack) -> updateInventories(stack)) {
        public boolean isItemValidForSlot(int i, ItemStack itemstack) {
            return  (i == SLOT_SATCHEL && itemstack.getItem() instanceof ItemSatchel) ||
                    ((i == SLOT_LEFT_POUCH || i == SLOT_RIGHT_POUCH) && itemstack.getItem() instanceof ItemPouch);
        };
    };
    
    public InventorySimple satchel = new InventorySimple(SATCHEL_MAX_SLOTS, "container.satchel");
    public InventorySimple leftPouch = new InventorySimple(POUCH_MAX_SLOTS, "container.leftPouch");
    public InventorySimple rightPouch = new InventorySimple(POUCH_MAX_SLOTS, "container.rightPouch");
    public InventoryAggregate aggregate = new InventoryAggregate(satchel, leftPouch, rightPouch);
    
    public EntityPlayer player;
    
    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound satchelsTag = new NBTTagCompound();
        
        if(!SatchelsUtils.isInventoryEmpty(equipment)) {
            satchelsTag.setTag("Equipment", InventoryUtils.writeItemStacksToTag(equipment.items));
        }
        if(!SatchelsUtils.isInventoryEmpty(satchel)) {
            satchelsTag.setTag("Satchel", InventoryUtils.writeItemStacksToTag(satchel.items));
        }
        if(!SatchelsUtils.isInventoryEmpty(leftPouch)) {
            satchelsTag.setTag("LeftPouch", InventoryUtils.writeItemStacksToTag(leftPouch.items));
        }
        if(!SatchelsUtils.isInventoryEmpty(rightPouch)) {
            satchelsTag.setTag("RightPouch", InventoryUtils.writeItemStacksToTag(rightPouch.items));
        }
        
        if(!satchelsTag.func_150296_c().isEmpty()) {
            compound.setTag("Satchels", satchelsTag);
        }
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        if(compound.hasKey("Satchels")) {
            NBTTagCompound satchelsTag = compound.getCompoundTag("Satchels");
            if(satchelsTag.hasKey("Equipment")) {
                SatchelsUtils.clearInventory(equipment);
                InventoryUtils.readItemStacksFromTag(equipment.items, satchelsTag.getTagList("Equipment", 10));
            }   
            if(satchelsTag.hasKey("Satchel")) {
                SatchelsUtils.clearInventory(satchel);
                InventoryUtils.readItemStacksFromTag(satchel.items, satchelsTag.getTagList("Satchel", 10));
            }
            if(satchelsTag.hasKey("LeftPouch")) {
                SatchelsUtils.clearInventory(leftPouch);
                InventoryUtils.readItemStacksFromTag(leftPouch.items, satchelsTag.getTagList("LeftPouch", 10));
            }
            if(satchelsTag.hasKey("RightPouch")) {
                SatchelsUtils.clearInventory(rightPouch);
                InventoryUtils.readItemStacksFromTag(rightPouch.items, satchelsTag.getTagList("RightPouch", 10));
            }
            updateInventories(null);
        }
    }
    
    public void dropItems() {
        dropItems(equipment);
    }
    
    public void dropItems(IInventory inv) {
        for(int i = 0; i < inv.getSizeInventory(); i++) {
            dropStack(inv, i);
        }
    }
    
    public ItemStack equip(ItemStack stack) {
        stack.stackSize = InventoryUtils.insertItem(equipment, stack, false);
        return stack;
    }

    @Override
    public void init(Entity entity, World world) {
        player = (EntityPlayer)entity;
    }
    
    public void updateInventories(ItemStack stack) {
        if(stack != null) {
            player.worldObj.playSoundEffect(player.posX, player.posY, player.posZ, "satchels:item.armor.equip_leather", 1f, 1f);
        }
        
        ContainerSatchels container = ((ContainerSatchels)player.inventoryContainer);
        container.redoSlots();
        for(int i = 0; i < leftPouch.getSizeInventory(); i++) {
            if(i >= getLeftPouchSlotCount() || !satchelsSlotPredicate.test(leftPouch.getStackInSlot(i))) {
                dropStack(leftPouch, i);
            }
        }
        for(int i = 0; i < rightPouch.getSizeInventory(); i++) {
            if(i >= getRightPouchSlotCount() || !satchelsSlotPredicate.test(rightPouch.getStackInSlot(i))) {
                dropStack(rightPouch, i);
            }
        }
        for(int i = 0; i < satchel.getSizeInventory(); i++) {
            if(i >= getSatchelSlotCount() || !satchelsSlotPredicate.test(satchel.getStackInSlot(i))) {
                dropStack(satchel, i);
            }
        }
    }
    
    private void dropStack(IInventory inv, int i) {
        if(inv.getStackInSlot(i) != null) {
            player.func_146097_a(inv.getStackInSlot(i), true, false);
            inv.setInventorySlotContents(i, null);
        }
    }
    
    public int getLeftPouchSlotCount() {
        return ItemPouch.getSlotCount(equipment.getStackInSlot(SLOT_LEFT_POUCH));
    }
    
    public int getRightPouchSlotCount() {
        return ItemPouch.getSlotCount(equipment.getStackInSlot(SLOT_RIGHT_POUCH));
    }
    
    public boolean hasSatchel() {
        return getSatchelSlotCount() > 0;
    }
    
    public boolean hasLeftPouch() {
        return getLeftPouchSlotCount() > 0;
    }
    
    public boolean hasRightPouch() {
        return getRightPouchSlotCount() > 0;
    }
    
    public int getSatchelSlotCount() {
        return equipment.getStackInSlot(SLOT_SATCHEL) != null ? SATCHEL_MAX_SLOTS : 0;
    }
    
    public static EntityPropertiesSatchels fromPlayer(EntityPlayer player) {
        return (EntityPropertiesSatchels)player.getExtendedProperties("satchels");
    }
    
    public boolean preAddItemStackToInventory(final ItemStack stack) {
        return addItemStackToInventory(stack, 0);
    }
    
    public boolean postAddItemStackToInventory(final ItemStack stack) {
        return addItemStackToInventory(stack, 1);
    }
    
    private boolean addItemStackToInventory(final ItemStack stack, int pass) {
        if(stack == null || stack.stackSize == 0 || stack.getItem() == null) return false;
        
        int originalSize = stack.stackSize;
        int left = InventoryUtils2.insertItem(getEnabledInventoryRange(), stack, false, pass);
        stack.stackSize = left; 
        return stack.stackSize != originalSize;
    }
    
    public InventoryRange getEnabledInventoryRange() {
        InventoryRange range = new InventoryRange(aggregate);
        List<Integer> slots = new ArrayList<>();
        if(hasSatchel()) {
            for(int i = 0; i < satchel.getSizeInventory(); i++) {
                slots.add(aggregate.toGlobalIdx(satchel, i));
            }
        }
        if(hasLeftPouch()) {
            for(int i = 0; i < getLeftPouchSlotCount(); i++) {
                slots.add(aggregate.toGlobalIdx(leftPouch, i));
            }
        }
        if(hasRightPouch()) {
            for(int i = 0; i < getRightPouchSlotCount(); i++) {
                slots.add(aggregate.toGlobalIdx(rightPouch, i));
            }
        }
        range.slots = Ints.toArray(slots);
        return range;
    }

    public void copyFrom(EntityPropertiesSatchels from) {
        SatchelsUtils.copyInventory(from.equipment, equipment);
        SatchelsUtils.copyInventory(from.satchel, satchel);
        SatchelsUtils.copyInventory(from.leftPouch, leftPouch);
        SatchelsUtils.copyInventory(from.rightPouch, rightPouch);
    }

}
