package makamys.satchels.compat;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.relauncher.ReflectionHelper;
import makamys.satchels.ConfigSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import tconstruct.client.tabs.AbstractTab;
import tconstruct.client.tabs.TabRegistry;

public class TechgunsCompat {
    
    private static Class<?> tgInventoryTabClass;
    private static Class<?> tgGuiInventoryClass;
    private static Class<?> tgGuiTabButtonClass;
    private static boolean doCompat;
    
    public static void postInit() {
        doCompat = ConfigSatchels.compatTechguns && Loader.isModLoaded("Techguns") && !Loader.isModLoaded("TConstruct");
        if(doCompat) {
            try {
                tgInventoryTabClass = Class.forName("techguns.plugins.tconstruct.TGInventoryTab");
                tgGuiInventoryClass = Class.forName("techguns.gui.playerinventory.GuiTGPlayerInventory");
                tgGuiTabButtonClass = Class.forName("techguns.gui.playerinventory.TGGuiTabButton");
                TabRegistry.registerTab((AbstractTab) tgInventoryTabClass.newInstance());
            } catch (Exception e) {
                Satchels.LOGGER.error("Failed to set up Techguns compat");
                e.printStackTrace();
            }
        }
    }
    
    public static void postInitGui(InitGuiEvent.Post event) {
        if(doCompat) {
            event.buttonList.removeIf(b -> tgGuiTabButtonClass.isInstance(b));
            if(tgGuiInventoryClass.isInstance(event.gui)) {
                int guiLeft = ReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.gui, "guiLeft", "field_147003_i");
                int guiTop = ReflectionHelper.getPrivateValue(GuiContainer.class, (GuiContainer)event.gui, "guiTop", "field_147009_r");
                TabRegistry.updateTabValues(guiLeft, guiTop, tgInventoryTabClass);
                TabRegistry.addTabsToList(event.buttonList);
            }
            
        }
    }
    
}
