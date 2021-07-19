package makamys.satchels;

import java.io.File;
import java.util.regex.Pattern;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

public class ConfigSatchels {
	
	public static Configuration config;
	
	public static Colour pouchSlotColor; // TODO
	public static Colour pouchBgColor;
	public static Colour satchelSlotColor; // TODO
	public static Colour satchelBgColor;
	
	public static void reload() {
		config = new Configuration(new File(Launch.minecraftHome, "config/satchels.cfg"));
        
        config.load();
        reparse();
	}
	
	public static void reparse() {
		pouchSlotColor = getColor(config, "Pouch slot color", "Interface - Colors", "FFB266", "");
		pouchBgColor = getColor(config, "Pouch background color", "Interface - Colors", "FFB266", "");
		satchelSlotColor = getColor(config, "Satchel slot color", "Interface - Colors", "FFBF99", "");
		satchelBgColor = getColor(config, "Satchel background color", "Interface - Colors", "FFBF99", "");
		
		if (config.hasChanged()) 
        {
            config.save();
        }
	}
	
	private static Colour getColor(Configuration config, String name, String category, String defaultValue, String comment) {
		Pattern colorPattern = Pattern.compile("(0x)?[0-9a-fA-F]{6}");
		String str = config.getString(name, category, defaultValue, comment, colorPattern);
		str.replace("0x", "");
		int rgb = Integer.valueOf(str, 16);
		return new ColourRGBA((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, 0xFF);
	}
}
