package makamys.satchels;

import codechicken.lib.vec.Vector3;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SatchelsUtils {
	
	public static boolean isInventoryEmpty(IInventory inventory) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			if(inventory.getStackInSlot(i) != null) {
				return false;
			}
		}
		return true;
	}
	
	public static void clearInventory(IInventory inventory) {
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			inventory.setInventorySlotContents(i, null);
		}
	}
	
	public static Vector3 vec3FromEntityEye(Entity entity) {
		return new Vector3(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ);
	}
	
	public static void dropItemStack(ItemStack stack, World world, Vector3 pos) {
		EntityItem entityitem = new EntityItem(world, pos.x, pos.y, pos.z, stack);
        entityitem.delayBeforeCanPickup = 10;
        world.spawnEntityInWorld(entityitem);
	}
	
	public static boolean isPointInRange(int x, int min, int max) {
		return x >= min && x < max;
	}
	
	public static boolean isPointInRectangle(int x, int y, int x0, int y0, int w, int h) {
		return isPointInRange(x, x0, x0 + w) && isPointInRange(y, y0, y0 + h);
	}
	
}
