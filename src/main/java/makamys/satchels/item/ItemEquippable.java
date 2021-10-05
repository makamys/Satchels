package makamys.satchels.item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import makamys.satchels.ConfigSatchels;
import makamys.satchels.Satchels;
import makamys.satchels.gui.TooltippedItem;
import makamys.satchels.proxy.SatchelsProxyClient;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.item.Item;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

public class ItemEquippable extends Item implements TooltippedItem {

    @Override
    public void getTooltips(List<String> linesNormal, List<String> linesDetails, ItemTooltipEvent event) {
        if(!ConfigSatchels.satchelsTab && (event.itemStack.getItem() instanceof ItemPouch || event.itemStack.getItem() instanceof ItemSatchel)) {
            if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                linesDetails.add("Equip in the equipment screen (press " + EnumChatFormatting.AQUA + GameSettings.getKeyDisplayString(((SatchelsProxyClient)Satchels.proxy).openEquipment.getKeyCode()) + EnumChatFormatting.GRAY + ")");
            }
        }
    }

}
