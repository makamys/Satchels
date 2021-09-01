package makamys.satchels;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import makamys.mclib.updatechecklibhelper.UpdateCheckLibHelper;
import makamys.satchels.Packets.HandlerOpenContainer;
import makamys.satchels.Packets.HandlerSyncEquipment;
import makamys.satchels.Packets.MessageOpenContainer;
import makamys.satchels.Packets.MessageSyncEquipment;
import makamys.satchels.inventory.ContainerSatchels;
import makamys.satchels.proxy.SatchelsProxyCommon;

@Mod(modid = Satchels.MODID, version = Satchels.VERSION, dependencies = "after:Techguns; required-after:CodeChickenCore")
public class Satchels
{	
    public static final String MODID = "satchels";
    public static final String VERSION = "@VERSION@";

    @Instance(MODID)
	public static Satchels instance;
    
    @SidedProxy(clientSide = "makamys.satchels.proxy.SatchelsProxyClient", serverSide = "makamys.satchels.proxy.SatchelsProxyCommon")
    public static SatchelsProxyCommon proxy;
    
    public static SimpleNetworkWrapper networkWrapper;
    
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    
    private static final UpdateCheckLibHelper UCL_HELPER = new UpdateCheckLibHelper("@UCL_VERSION@", "@UPDATE_URL@");
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	SatchelsItems.init();
    	ConfigSatchels.init();
    	UCL_HELPER.preInit();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	instance = this;
    	
    	ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(SatchelsItems.pouch_upgrade), 1, 1, ConfigSatchels.pouchUpgradeWeight));
    	
    	MinecraftForge.EVENT_BUS.register(proxy);
    	FMLCommonHandler.instance().bus().register(proxy);
    	NetworkRegistry.INSTANCE.registerGuiHandler(Satchels.instance, new GuiHandler());
    	proxy.init();
    	
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		networkWrapper.registerMessage(HandlerOpenContainer.class, MessageOpenContainer.class, 0, Side.SERVER);
		networkWrapper.registerMessage(HandlerSyncEquipment.class, MessageSyncEquipment.class, 1, Side.CLIENT);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit();
    }
	
	public static void postPlayerConstructor(EntityPlayer player) {
		player.openContainer = player.inventoryContainer = new ContainerSatchels(player); 
	}
	
	public static void preProcessClientStatus(C16PacketClientStatus status, EntityPlayerMP player) {
		if(status.func_149435_c() == C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT && !player.capabilities.isCreativeMode) {
			((ContainerSatchels)player.inventoryContainer).redoSlots(true);
		}
	}
}
