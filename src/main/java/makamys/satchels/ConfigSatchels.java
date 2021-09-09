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

import com.google.common.collect.Lists;

import codechicken.lib.colour.Colour;
import codechicken.lib.colour.ColourRGBA;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.LoaderState;
import makamys.mclib.config.item.BackpackConfigHelper;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.common.config.Configuration;

public class ConfigSatchels {
    
    public static Configuration config;
    
    private static File configFile;
    private static WatchService watcher;
    
    
    public static Colour pouchSlotColor; // TODO
    public static Colour pouchBgColor;
    public static Colour satchelSlotColor; // TODO
    public static Colour satchelBgColor;
    
    public static boolean hotSwap;
    public static boolean satchelsTab;
    
    public static int pouchUpgradeWeight;
    
    public static boolean drawSatchel;
    public static boolean drawSatchelStrap;
    public static boolean drawLeftPouch;
    public static boolean drawRightPouch;
    
    public static boolean compatTechguns;

    public static String satchelIngredient1;
    public static String satchelIngredient2;

    public static BackpackConfigHelper backpackHelper;
    
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
        hotSwap = config.getBoolean("hotSwap", "_general", false, "Apply changes made in the config file immediately.\nOff by default because it could potentially cause poor performance on certain platforms.\nUseful for tweaking the GUI.");
        satchelsTab = config.getBoolean("satchelsTab", "_general", true, "Add Satchels tab to inventory GUI");
        
        pouchSlotColor = getColor(config, "pouchSlotColor", "interface colors", "FFB266", "Not implemented yet!");
        pouchBgColor = getColor(config, "pouchBgColor", "interface colors", "FFB266", "");
        satchelSlotColor = getColor(config, "satchelSlotColor", "interface colors", "FFBF99", "Not implemented yet!");
        satchelBgColor = getColor(config, "satchelBgColor", "interface colors", "FFBF99", "");
        
        pouchUpgradeWeight = config.getInt("pouchUpgradeWeight", "world generation", 7, 0, Integer.MAX_VALUE, "The weight of the pouch upgrade in the dungeon loot table.\nIncrease this to make them more common, or decrease to make them rarer.\nFor reference, saddles have a weight of 10 while golden apples have a weight of 1.\nBased on my testing, a weight of 10 with no other mods present roughly corresponds to an average of 1 item per dungeon, and it scales linearly from there.\nYou might want to bump this up if you have many other mods adding loot, or if this is a multiplayer server.");
        
        drawSatchel = config.getBoolean("drawSatchel", "player model", true, "");
        drawSatchelStrap = config.getBoolean("drawSatchelStrap", "player model", true, "");
        drawLeftPouch = config.getBoolean("drawLeftPouch", "player model", true, "");
        drawRightPouch = config.getBoolean("drawRightPouch", "player model", true, "");
        
        satchelIngredient1 = config.getString("satchelIngredient1", "recipes", "diamond_block", "The ingredient in the center of the bottom row of the satchel crafting recipe");
        satchelIngredient2 = config.getString("satchelIngredient2", "recipes", "slime_ball", "The ingredient in the left and right of the center row of the satchel crafting recipe");
        
        compatTechguns = config.getBoolean("compatTechguns", "compatibility", true, "Force Techguns to use vertical tabs (using TConstruct's API) even if TConstruct is not present.");
        
        config.getCategory("_general").setComment("Note: Changes in this file will get applied when the game is paused, or immediately if the hotSwap option is enabled.");
        
        if(Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            backpackHelper = new BackpackConfigHelper(Lists.newArrayList(config.getStringList("itemBlacklist", "inventory", BackpackConfigHelper.NON_NESTABLE_BACKPACK_BLACKLIST, "Items that aren't allowed in satchels or pouches" + BackpackConfigHelper.CONFIG_DESCRIPTION_SUFFIX)));
        }
        
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
