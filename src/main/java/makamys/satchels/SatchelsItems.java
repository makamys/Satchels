package makamys.satchels;

import cpw.mods.fml.common.registry.GameRegistry;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemSatchel;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SatchelsItems {
	
	public static final Item satchel = initItem(new ItemSatchel());
	public static final Item pouch = initItem(new ItemPouch());
	
	public static void init() {
    	registerRecipes();
	}
	
	private static void registerRecipes() {
		GameRegistry.addShapedRecipe(new ItemStack(satchel), new Object[] {"L L", "LDL", "LLL", 'L', Items.leather, 'D', Items.diamond});
		GameRegistry.addShapedRecipe(new ItemStack(pouch), new Object[] {" L ", "LSL", " L ", 'L', Items.leather, 'S', Items.string});
	}
	
	private static Item initItem(Item item) {
    	String name = item.getUnlocalizedName();
    	int firstDot = name.indexOf('.');
    	GameRegistry.registerItem(item, name.substring(firstDot + 1));
    	return item;
    }
	
}
