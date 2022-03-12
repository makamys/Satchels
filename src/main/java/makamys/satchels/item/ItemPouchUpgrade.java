package makamys.satchels.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.Satchels;
import makamys.satchels.gui.TooltippedItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemPouchUpgrade extends Item implements TooltippedItem {
    
    public static IIcon backgroundIcon;
    
    public ItemPouchUpgrade() {
        setUnlocalizedName(Satchels.MODID + "." + "pouch_upgrade");
        setCreativeTab(CreativeTabs.tabTools);
        setTextureName("pouch_upgrade");
    }
    
   @SideOnly(Side.CLIENT)
   public void registerIcons(IIconRegister iconRegister) {
       super.itemIcon = iconRegister.registerIcon("satchels:pouch_upgrade");
       backgroundIcon = iconRegister.registerIcon("satchels:empty_pouch_upgrade_slot");
   }
   
   @SideOnly(Side.CLIENT)
   public boolean hasEffect(ItemStack p_77636_1_) {
       return true;
   }
   
   public EnumRarity getRarity(ItemStack p_77613_1_) {
       return EnumRarity.uncommon;
   }

    @Override
    public void getTooltips(List<String> linesNormal, List<String> linesDetails, ItemTooltipEvent event) {
        linesDetails.add(I18n.format("tooltip.satchels.pouchUpgrade"));
    }
}
