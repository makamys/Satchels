package makamys.satchels;

import cpw.mods.fml.common.network.IGuiHandler;
import makamys.satchels.item.ItemPouch;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
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
			return new ContainerChest(player.inventory, ItemPouch.getInventory(player.getHeldItem(), world));
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
			return new GuiChest(player.inventory, ItemPouch.getInventory(player.getHeldItem(), world));
		default:
			throw new IllegalArgumentException("Unknown container ID: " + ID);
		}
	}

}
