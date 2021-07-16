package makamys.satchels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Satchels.MODID, version = Satchels.VERSION)
public class Satchels
{
    public static final String MODID = "satchels";
    public static final String VERSION = "0.0";

    KeyBinding openEquipment = new KeyBinding("Open Equipment", Keyboard.KEY_P, "Satchels");
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    	FMLCommonHandler.instance().bus().register(this);
    	ClientRegistry.registerKeyBinding(openEquipment);
    	//NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    }
    
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
    	System.out.println(event.gui);
    	if(event.gui != null && event.gui.getClass() == GuiInventory.class && !(event.gui instanceof GuiSatchelsInventory)){
			event.gui = new GuiSatchelsInventory(Minecraft.getMinecraft().thePlayer);
    	}
    }
    
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
    	Entity entity = event.entity;
    	if(entity instanceof EntityPlayer) {
    		entity.registerExtendedProperties("satchels", new EntityPropertiesSatchels());
    	}
    }
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
    	if(openEquipment.isPressed()) {
    		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    		//Minecraft.getMinecraft().thePlayer.openGui(this, 0, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
    		Minecraft.getMinecraft().displayGuiScreen(new GuiEquipment(player));
    	}
    }
	
	public static void postPlayerConstructor(EntityPlayer player) {
		player.inventoryContainer = player.openContainer = new ContainerSatchels(player);
	}
	
	
}
