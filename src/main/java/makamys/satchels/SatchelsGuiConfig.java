package makamys.satchels;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;

public class SatchelsGuiConfig extends GuiConfig {

	public SatchelsGuiConfig(GuiScreen parentScreen) {
		super(parentScreen, getConfigElements(), Satchels.MODID, false, false, "Satchels Configuration");
	}
	
	private static List<IConfigElement> getConfigElements() {
		List<IConfigElement> list = new ArrayList<>();
		
		for(String category : ConfigSatchels.config.getCategoryNames()) {
			list.add(new ConfigElement(ConfigSatchels.config.getCategory(category)));
		}
		return list;
	}

}
