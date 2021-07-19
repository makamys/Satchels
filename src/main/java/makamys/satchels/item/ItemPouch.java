package makamys.satchels.item;

import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;

public class ItemPouch extends Item {
	
	public ItemPouch() {
		setMaxStackSize(1);
		setUnlocalizedName(Satchels.MODID + "." + "pouch");
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("pouch");
	}
	
   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
	   super.itemIcon = iconRegister.registerIcon("satchels:pouch");
   }
   
   public static IInventory getInventory(ItemStack stack) {
	   if(stack == null || stack.stackTagCompound == null || !(stack.getItem() instanceof ItemPouch)) {
		   return null;
	   }
	   NBTTagList tag = stack.stackTagCompound.getTagList("Inventory", 10);
	   if(tag == null) {
		   return null;
	   }
	   InventorySimple inventory = new InventorySimple(EntityPropertiesSatchels.POUCH_MAX_SLOTS);
	   InventoryUtils.readItemStacksFromTag(inventory.items, tag);
	   return inventory;
   }
   
   public static int getSlotCount(ItemStack stack) {
	   if(stack == null || !(stack.getItem() instanceof ItemPouch)) {
		   return 0;
	   }
	   IInventory inventory = getInventory(stack);
	   int slots = EntityPropertiesSatchels.POUCH_INITIAL_SLOTS;
	   if(inventory != null) {
		   for(int i = 0; i < inventory.getSizeInventory(); i++) {
			   ItemStack invStack = inventory.getStackInSlot(i);
			   if(invStack != null && invStack.getItem() instanceof ItemPouchUpgrade) {
				   slots++;
			   }
		   }
	   }
	   return slots;
   }
   
}
