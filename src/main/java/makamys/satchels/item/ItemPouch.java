package makamys.satchels.item;

import java.util.List;
import java.util.function.Predicate;

import codechicken.lib.inventory.InventorySimple;
import codechicken.lib.inventory.InventoryUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.GuiHandler;
import makamys.satchels.Satchels;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import makamys.satchels.Packets.MessageOpenContainer;

public class ItemPouch extends ItemEquippable {
    
    public static final Predicate<ItemStack> acceptedContentsPredicate = (stack) -> stack != null && stack.getItem() instanceof ItemPouchUpgrade;
    
    public static IIcon emptyIcon;
    
    public ItemPouch() {
        setMaxStackSize(1);
        setUnlocalizedName(Satchels.MODID + "." + "pouch");
        setCreativeTab(CreativeTabs.tabTools);
        setTextureName("pouch");
    }
    
   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
       super.itemIcon = iconRegister.registerIcon("satchels:pouch");
       emptyIcon = iconRegister.registerIcon("satchels:empty_equipment_slot_pouch");
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
   
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
       if(player.isSneaking()) {
           Satchels.networkWrapper.sendToServer(new MessageOpenContainer(GuiHandler.ID_POUCH));
           return stack;
       } else {
           return super.onItemRightClick(stack, world, player);
       }
    }
   
   public static class InventoryPouch extends InventorySimple {
       
       private ItemStack stack;
       private World world;
       
       public InventoryPouch(ItemStack stack, World world) {
           super(EntityPropertiesSatchels.POUCH_MAX_SLOTS - EntityPropertiesSatchels.POUCH_INITIAL_SLOTS, stack.getDisplayName());
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
        
        @Override
        public int getInventoryStackLimit() {
            return 1;
        }
        
        @Override
        public boolean isItemValidForSlot(int i, ItemStack itemstack) {
            return itemstack != null && itemstack.getItem() instanceof ItemPouchUpgrade;
        }
   }

    @Override
    public void getTooltips(List<String> linesNormal, List<String> linesDetails, ItemTooltipEvent event) {
        int slots = ItemPouch.getSlotCount(event.itemStack);
        linesNormal.add((slots > EntityPropertiesSatchels.POUCH_INITIAL_SLOTS ? "" + EnumChatFormatting.YELLOW : "") + I18n.format("tooltip.satchels.pouch.slotCount", slots));
        
        linesDetails.add(I18n.format("tooltip.satchels.pouch.sneakUseHint"));
        
        super.getTooltips(linesNormal, linesDetails, event);
    }
}
