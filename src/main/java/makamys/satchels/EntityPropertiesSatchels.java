package makamys.satchels;

import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class EntityPropertiesSatchels implements IExtendedEntityProperties {
	
	public static final int SATCHEL_MAX_SLOTS = 9;
	public static final int POUCH_MAX_SLOTS = 8;
	
	private InventorySimple equipment = new InventorySimple(3);
	
	private InventorySimple satchel;
	private InventorySimple leftPouch;
	private InventorySimple rightPouch;
	
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
				equipment = new InventorySimple(3);
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
		}
	}

	@Override
	public void init(Entity entity, World world) {
		
	}

}
