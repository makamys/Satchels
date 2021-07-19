package makamys.satchels.compat.techguns;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import makamys.satchels.ConfigSatchels;
import makamys.satchels.Satchels;
import techguns.plugins.tconstruct.ITechgunsTConstructIntegration;

public class TechGunsCompat {
	
	public static void postInit(FMLPostInitializationEvent event) {
		if(ConfigSatchels.compatTechguns && Loader.isModLoaded("Techguns") && !(Loader.isModLoaded("TConstruct"))) {
			try {
				Class<?> techgunsClass = Class.forName("techguns.Techguns");
				techgunsClass.getField("isTConstructLoaded").setBoolean(null, true);
				ITechgunsTConstructIntegration integration = event.getSide() == Side.CLIENT ?
						new TechgunsTConstructIntegrationClient() : new TechgunsTConstructIntegration();
				techgunsClass.getField("pluginTConstructIntegration").set(null, integration);
				integration.init();
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException
					| ClassNotFoundException e) {
				Satchels.LOGGER.error("Failed to set up Techguns compatibility");
				e.printStackTrace();
			}
		}
	}
	
}
