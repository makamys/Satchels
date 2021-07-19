package makamys.satchels.item;

import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.GuiHandler;
import makamys.satchels.Satchels;
import makamys.satchels.Satchels.MessageOpenContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;

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
   
   public static IInventory getInventory(ItemStack stack, World world) {
	   if(stack == null || !(stack.getItem() instanceof ItemPouch)) {
		   return null;
	   }
	   return new InventoryPouch(stack, world);
   }
   
   public static int getSlotCount(ItemStack stack) {
	   if(stack == null || !(stack.getItem() instanceof ItemPouch)) {
		   return 0;
	   }
	   IInventory inventory = getInventory(stack, null);
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
   
   @Override
	public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_,
			int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
	   Satchels.networkWrapper.sendToServer(new MessageOpenContainer(GuiHandler.ID_POUCH));
	   return true;
	}
   
   public static class InventoryPouch extends InventorySimple {
	   
	   private ItemStack stack;
	   private World world;
	   
	   public InventoryPouch(ItemStack stack, World world) {
		   super(EntityPropertiesSatchels.POUCH_MAX_SLOTS, stack.getDisplayName());
		   this.stack = stack;
		   this.world = world;
		   
		   NBTTagList tag = stack.stackTagCompound != null ? stack.stackTagCompound.getTagList("Inventory", 10) : null;
		   if(tag != null) {
			   InventoryUtils.readItemStacksFromTag(items, tag);
		   }
	   }
	   
		@Override
		public void closeInventory() {
			if(world != null && !world.isRemote) {
				stack.setTagInfo("Inventory", InventoryUtils.writeItemStacksToTag(items));
			}
		}
   }
}
