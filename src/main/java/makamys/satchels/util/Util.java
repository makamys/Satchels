package makamys.satchels.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static makamys.satchels.Satchels.LOGGER;

public class Util {
    
    public static Object resolveItemOrBlockOr(String str, Object fallback){
        Object obj = Item.itemRegistry.getObject(str);
        if(obj == null) {
            obj = Block.blockRegistry.getObject(str);
        }
        if(obj == null) {
            LOGGER.debug("Couldn't resolve item/block " + str + ", falling back to default " + fallback);
            obj = fallback;
        }
        
        LOGGER.debug("Resolved " + str + " to " + obj);
        return obj;
    }
    
}
