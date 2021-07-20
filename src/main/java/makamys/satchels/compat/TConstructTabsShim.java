package makamys.satchels.compat;

import cpw.mods.fml.common.Loader;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.MinecraftForge;
import tconstruct.client.tabs.InventoryTabVanilla;
import tconstruct.client.tabs.TabRegistry;

public class TConstructTabsShim {
	
	public static void postInit() {
		if(!Loader.isModLoaded("TConstruct") && TabRegistry.getTabList().isEmpty()) {
			TabRegistry.registerTab(new InventoryTabVanilla());
		}
		
		// These two mods unconditionally register TabRegistry at startup
		if(!Loader.isModLoaded("TConstruct") && !Loader.isModLoaded("GalacticraftCore") && !Launch.blackboard.containsKey("tconstruct.tabRegistryRegistered")) {
			MinecraftForge.EVENT_BUS.register(new TabRegistry());
			Launch.blackboard.put("tconstruct.tabRegistryRegistered", true);
		}
	}
	
}
