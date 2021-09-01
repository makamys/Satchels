package makamys.satchels.proxy;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.ConfigSatchels;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.GuiHandler;
import makamys.satchels.Packets.MessageInventoryOpened;
import makamys.satchels.Packets.MessageOpenContainer;
import makamys.satchels.client.model.ModelPouch;
import makamys.satchels.client.model.ModelSatchel;
import makamys.satchels.Satchels;
import makamys.satchels.compat.TConstructTabsShim;
import makamys.satchels.compat.TechgunsCompat;
import makamys.satchels.gui.GuiSatchelsInventory;
import makamys.satchels.gui.InventoryTabSatchels;
import makamys.satchels.inventory.ContainerSatchels;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemPouchUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import tconstruct.client.tabs.TabRegistry;

public class SatchelsProxyClient extends SatchelsProxyCommon {
	
	KeyBinding openEquipment = new KeyBinding("Open Equipment", Keyboard.KEY_P, "Satchels");
	
	@Override
	public void init() {
		super.init();
		ClientRegistry.registerKeyBinding(openEquipment);
	}
	
	@Override
	public void postInit() {
		super.postInit();
		if(ConfigSatchels.satchelsTab || Loader.isModLoaded("Techguns")) {
	    	TConstructTabsShim.postInit();
	    	if(ConfigSatchels.satchelsTab) {
	    		TabRegistry.registerTab(new InventoryTabSatchels());
	    	}
	    	TechgunsCompat.postInit();
    	}
	}
	
	@SubscribeEvent
    public void postInitGui(InitGuiEvent.Post event) {
    	TechgunsCompat.postInitGui(event);
    }
	
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
    	if(event.gui != null && event.gui.doesGuiPauseGame()) {
    		ConfigSatchels.reloadIfChanged();
    	}
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    	if(event.gui != null && event.gui.getClass() == GuiInventory.class && player.inventoryContainer instanceof ContainerSatchels
    			&& !(event.gui instanceof GuiSatchelsInventory) && !player.capabilities.isCreativeMode){
			event.gui = new GuiSatchelsInventory(player);
			Satchels.networkWrapper.sendToServer(new MessageInventoryOpened());
    	}
    }
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if(ConfigSatchels.hotSwap) {
			ConfigSatchels.reloadIfChanged();
		}
    	if(openEquipment.isPressed()) {
    		Satchels.networkWrapper.sendToServer(new MessageOpenContainer(GuiHandler.ID_EQUIPMENT));
    	}
    }
	
	@SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
    	if(event.itemStack.getItem() instanceof ItemPouch) {
    		int slots = ItemPouch.getSlotCount(event.itemStack);
    		event.toolTip.add((slots > EntityPropertiesSatchels.POUCH_INITIAL_SLOTS ? "" + EnumChatFormatting.YELLOW : "") + slots + " slots");
    	} else if(event.itemStack.getItem() instanceof ItemPouchUpgrade) {
			event.toolTip.add("Adds 1 slot to a pouch");
    	}
    }
    
    // Adapted from mcft.copy.betterstorage.proxy.ClientProxy#onRenderPlayerSpecialsPre by copygirl
	@SubscribeEvent
	public void onRenderPlayerSpecialsPre(RenderPlayerEvent.Specials.Pre event) {
	    if(ModelSatchel.instance == null) {
            ModelSatchel.instance = new ModelSatchel(event.renderer);
        }
	    if(ModelPouch.instance == null) {
	        ModelPouch.instance = new ModelPouch(event.renderer);
	    }
	    ModelSatchel.instance.preRender(event);
		ModelPouch.instance.preRender(event);
	}
	
}
