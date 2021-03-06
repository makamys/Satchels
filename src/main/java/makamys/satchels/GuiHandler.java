package makamys.satchels;

import cpw.mods.fml.common.network.IGuiHandler;
import makamys.satchels.gui.GuiChestGeneric;
import makamys.satchels.gui.GuiEquipment;
import makamys.satchels.inventory.ContainerChestGeneric;
import makamys.satchels.inventory.ContainerEquipment;
import makamys.satchels.item.ItemPouch;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    public static final int ID_EQUIPMENT = 0;
    public static final int ID_POUCH = 1;
    
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
        case ID_EQUIPMENT:
            return new ContainerEquipment(player.inventory, !player.worldObj.isRemote, player);
        case ID_POUCH:
            return ItemPouch.constructUpgradesContainer(player.inventory, ItemPouch.getInventory(player.getHeldItem(), world));
        default:
            throw new IllegalArgumentException("Unknown container ID: " + ID);
        }
        
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch(ID) {
        case ID_EQUIPMENT:
            return new GuiEquipment(player);
        case ID_POUCH:
            return new GuiChestGeneric(player.inventory, ItemPouch.getInventory(player.getHeldItem(), world));
        default:
            throw new IllegalArgumentException("Unknown container ID: " + ID);
        }
    }

}
