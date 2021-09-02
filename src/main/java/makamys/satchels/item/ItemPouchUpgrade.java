package makamys.satchels.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.Satchels;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPouchUpgrade extends Item {
	
	public ItemPouchUpgrade() {
		setUnlocalizedName(Satchels.MODID + "." + "pouch_upgrade");
		setCreativeTab(CreativeTabs.tabTools);
		setTextureName("pouch_upgrade");
	}
	
   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
	   super.itemIcon = iconRegister.registerIcon("satchels:pouch_upgrade");
   }
   
   @SideOnly(Side.CLIENT)
   public boolean hasEffect(ItemStack p_77636_1_) {
       return true;
   }
   
   public EnumRarity getRarity(ItemStack p_77613_1_) {
       return EnumRarity.uncommon;
   }
}
