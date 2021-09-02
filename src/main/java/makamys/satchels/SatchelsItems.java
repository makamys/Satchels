package makamys.satchels;

import cpw.mods.fml.common.registry.GameRegistry;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemPouchUpgrade;
import makamys.satchels.item.ItemSatchel;
import makamys.satchels.util.Util;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SatchelsItems {
	
	public static final Item satchel = initItem(new ItemSatchel());
	public static final Item pouch = initItem(new ItemPouch());
	public static final Item pouch_upgrade = initItem(new ItemPouchUpgrade());
	
	public static void init() {
    	// this is called just so the fields get initialized
	}
	
	public static void postInit() {
		registerRecipes();
	}
	
	private static void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(satchel), new Object[] {"S S", "2L2", "L1L", 'L', Items.leather, 'S', Items.string, '1', Util.resolveItemOrBlockOr(ConfigSatchels.satchelIngredient1, Blocks.diamond_block), '2', Util.resolveItemOrBlockOr(ConfigSatchels.satchelIngredient2, Items.slime_ball)});
		GameRegistry.addShapedRecipe(new ItemStack(pouch), new Object[] {"SLS", "L L", "SLS", 'L', Items.leather, 'S', Items.string});
	}
	
	private static Item initItem(Item item) {
    	String name = item.getUnlocalizedName();
    	int firstDot = name.lastIndexOf('.');
    	GameRegistry.registerItem(item, name.substring(firstDot + 1));
    	return item;
    }
	
}
