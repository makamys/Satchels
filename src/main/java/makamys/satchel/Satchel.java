package makamys.satchel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid = Satchel.MODID, version = Satchel.VERSION)
public class Satchel
{
    public static final String MODID = "satchel";
    public static final String VERSION = "0.0";

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	MinecraftForge.EVENT_BUS.register(this);
    }
    
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
    	System.out.println(event.gui);
    	if(event.gui != null && event.gui.getClass() == GuiInventory.class && !(event.gui instanceof GuiSatchelInventory)){
			event.gui = new GuiSatchelInventory(Minecraft.getMinecraft().thePlayer);
    	}
    }
}
