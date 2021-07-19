package makamys.satchels.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.Satchels;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

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
	
}
