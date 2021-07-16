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
	
	public InventorySimpleNotifying equipment = new InventorySimpleNotifying(3);
	
	public InventorySimple satchel;
	public InventorySimple leftPouch;
	public InventorySimple rightPouch;
	
	EntityPlayer player;
	
	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound satchelsTag = new NBTTagCompound();
		
		if(equipment != null) {
			satchelsTag.setTag("Equipment", InventoryUtils.writeItemStacksToTag(equipment.items));
		}
		if(satchel != null) {
			satchelsTag.setTag("Satchel", InventoryUtils.writeItemStacksToTag(satchel.items));
		}
		if(leftPouch != null) {
			satchelsTag.setTag("LeftPouch", InventoryUtils.writeItemStacksToTag(leftPouch.items));
		}
		if(rightPouch != null) {
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
				equipment = new InventorySimpleNotifying(3);
				InventoryUtils.readItemStacksFromTag(equipment.items, satchelsTag.getTagList("Equipment", 10));
			}	
			if(satchelsTag.hasKey("Satchel")) {
				satchel = new InventorySimple(SATCHEL_MAX_SLOTS);
				InventoryUtils.readItemStacksFromTag(equipment.items, satchelsTag.getTagList("Satchel", 10));
			}
			if(satchelsTag.hasKey("LeftPouch")) {
				leftPouch = new InventorySimple(POUCH_MAX_SLOTS);
				InventoryUtils.readItemStacksFromTag(equipment.items, satchelsTag.getTagList("LeftPouch", 10));
			}
			if(satchelsTag.hasKey("RightPouch")) {
				rightPouch = new InventorySimple(POUCH_MAX_SLOTS);
				InventoryUtils.readItemStacksFromTag(equipment.items, satchelsTag.getTagList("RightPouch", 10));
			}
			equipment.callback = new Runnable() {
				
				@Override
				public void run() {
					updateInventories();
				}
			};
			updateInventories();
		}
	}

	@Override
	public void init(Entity entity, World world) {
		player = (EntityPlayer)entity;
	}
	
	public void updateInventories() {
		ItemStack satchelItem = equipment.getStackInSlot(SLOT_SATCHEL);
		if(satchelItem == null) {
			satchel = null;
			// TODO drop items
		} else {
			satchel = new InventorySimple(SATCHEL_MAX_SLOTS);
		}
	}

}
