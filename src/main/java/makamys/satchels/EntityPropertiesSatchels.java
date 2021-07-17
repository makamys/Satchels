package makamys.satchels;

import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityPropertiesSatchels implements IExtendedEntityProperties {
	
	public static final int SATCHEL_MAX_SLOTS = 9;
	public static final int POUCH_MAX_SLOTS = 8;
	
	private static final int SLOT_SATCHEL = 0;
	private static final int SLOT_LEFT_POUCH = 1;
	private static final int SLOT_RIGHT_POUCH = 2;
	
	public InventorySimpleNotifying equipment = new InventorySimpleNotifying(3, () -> updateInventories());
	
	public InventorySimple satchel = new InventorySimple(SATCHEL_MAX_SLOTS);
	public InventorySimple leftPouch = new InventorySimple(POUCH_MAX_SLOTS);
	public InventorySimple rightPouch = new InventorySimple(POUCH_MAX_SLOTS);
	
	EntityPlayer player;
	
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
			updateInventories();
		}
	}

	@Override
	public void init(Entity entity, World world) {
		player = (EntityPlayer)entity;
	}
	
	public void updateInventories() {
		/*ItemStack satchelItem = equipment.getStackInSlot(SLOT_SATCHEL);
		if(satchelItem == null) {
			satchel = null;
			// TODO drop items
		} else {
			satchel = new InventorySimple(SATCHEL_MAX_SLOTS);
		}*/
		((ContainerSatchels)player.inventoryContainer).redoSlots();
	}
	
	public int getLeftPouchSlotCount() {
		// TODO
		return equipment.getStackInSlot(SLOT_LEFT_POUCH) != null ? 3 : 0;
	}
	
	public int getRightPouchSlotCount() {
		// TODO
		return equipment.getStackInSlot(SLOT_RIGHT_POUCH) != null ? 8 : 0;
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

}