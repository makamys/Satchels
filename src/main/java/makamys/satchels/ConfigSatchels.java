package makamys.satchels;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
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
	
    private static File configFile;
    private static WatchService watcher;
	
    public static void init() {
    	configFile = new File(Launch.minecraftHome, "config/satchels.cfg");
    	reload();
    	
        try {
            registerWatchService();
        } catch(IOException e) {
            System.out.println("Failed to register watch service: " + e + " (" + e.getMessage() + "). Changes to the config file will not be reflected");
        }
    }
    
	public static void reload() {
		config = new Configuration(configFile);
        
        config.load();
        reparse();
	}
	
	public static void reloadIfChanged() {
		if(watcher != null) {
            WatchKey key = watcher.poll();
            
            if(key != null) {
                for(WatchEvent<?> event: key.pollEvents()) {
                    if(event.context().toString().equals(configFile.getName())) {
                        reload();
                    }
                }
                key.reset();
            }
        }
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
	
	private static void registerWatchService() throws IOException {
        watcher = FileSystems.getDefault().newWatchService();
        configFile.toPath().getParent().register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
    }
}
