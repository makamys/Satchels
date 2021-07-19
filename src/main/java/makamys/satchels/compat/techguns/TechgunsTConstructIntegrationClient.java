package makamys.satchels.compat.techguns;

import java.util.List;

import makamys.satchels.Satchels;
import tconstruct.client.tabs.AbstractTab;
import tconstruct.client.tabs.TabRegistry;
import techguns.plugins.tconstruct.ITechgunsTConstructIntegration;

public class TechgunsTConstructIntegrationClient extends TechgunsTConstructIntegration implements ITechgunsTConstructIntegration {

	private static Class<?> tgInventoryTabClass;
	
	static {
		try {
			tgInventoryTabClass = Class.forName("techguns.plugins.tconstruct.TGInventoryTab");
		} catch (ClassNotFoundException e) {
			Satchels.LOGGER.error("Couldn't find Techguns inventory tab class");
			e.printStackTrace();
		}
	}
	
	public void init() {
		super.init();
		try {
			TabRegistry.registerTab((AbstractTab) tgInventoryTabClass.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			Satchels.LOGGER.error("Failed to register Techguns inventory tab");
			e.printStackTrace();
		}
	}

   public void addTabs(int guiLeft, int guiTop, List buttonList) {
      TabRegistry.updateTabValues(guiLeft, guiTop, tgInventoryTabClass);
      TabRegistry.addTabsToList(buttonList);
   }
}
