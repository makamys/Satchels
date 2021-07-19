package makamys.satchels;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.ChestGenHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemPouchUpgrade;

@Mod(modid = Satchels.MODID, version = Satchels.VERSION)
public class Satchels
{	
    public static final String MODID = "satchels";
    public static final String VERSION = "0.0";

    @Instance(MODID)
	public static Satchels instance;
    
    KeyBinding openEquipment = new KeyBinding("Open Equipment", Keyboard.KEY_P, "Satchels");
    
    public static SimpleNetworkWrapper networkWrapper;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    	SatchelsItems.init();
    	ConfigSatchels.init();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	instance = this;
    	
    	ChestGenHooks.addItem(ChestGenHooks.DUNGEON_CHEST, new WeightedRandomChestContent(new ItemStack(SatchelsItems.pouch_upgrade), 1, 1, 4));
    	
    	MinecraftForge.EVENT_BUS.register(this);
    	FMLCommonHandler.instance().bus().register(this);
    	ClientRegistry.registerKeyBinding(openEquipment);
    	NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
    	
		networkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
		networkWrapper.registerMessage(HandlerOpenContainer.class, MessageOpenContainer.class, 0, Side.SERVER);
		networkWrapper.registerMessage(HandlerSyncEquipment.class, MessageSyncEquipment.class, 1, Side.CLIENT);
    }
    
    public static class HandlerOpenContainer implements IMessageHandler<MessageOpenContainer, IMessage> {

		@Override
		public IMessage onMessage(MessageOpenContainer message, MessageContext ctx) {
			EntityPlayer player = ctx.getServerHandler().playerEntity;
			player.openGui(Satchels.instance, message.id, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
			return null;
		}
    	
    }
    
    public static class MessageOpenContainer implements IMessage {

    	public int id;
    	
    	public MessageOpenContainer() {}
    	
    	public MessageOpenContainer(int id) {
			this.id = id;
		}
    	
		@Override
		public void fromBytes(ByteBuf buf) {
			id = buf.readInt();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeInt(id);
		}
    	
    }
    
    public static class HandlerSyncEquipment implements IMessageHandler<MessageSyncEquipment, IMessage> {

		@Override
		public IMessage onMessage(MessageSyncEquipment message, MessageContext ctx) {
			EntityPlayer player = Minecraft.getMinecraft().thePlayer;
			EntityPropertiesSatchels satchelsProps = (EntityPropertiesSatchels)player.getExtendedProperties("satchels");
			satchelsProps.loadNBTData(message.tag);
			return null;
		}
    	
    }
    
    public static class MessageSyncEquipment implements IMessage {

    	public NBTTagCompound tag;
    	
    	public MessageSyncEquipment() {}
    	
    	public MessageSyncEquipment(NBTTagCompound tag) {
    		this.tag = tag;
    	}
    	
		@Override
		public void fromBytes(ByteBuf buf) {
			tag = ByteBufUtils.readTag(buf);
		}

		@Override
		public void toBytes(ByteBuf buf) {
			ByteBufUtils.writeTag(buf, tag);
		}
    	
    }
    
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onGuiOpen(GuiOpenEvent event) {
    	if(event.gui != null && event.gui.doesGuiPauseGame()) {
    		ConfigSatchels.reloadIfChanged();
    	}
    	EntityPlayer player = Minecraft.getMinecraft().thePlayer;
    	if(event.gui != null && event.gui.getClass() == GuiInventory.class && player.inventoryContainer instanceof ContainerSatchels
    			&& !(event.gui instanceof GuiSatchelsInventory)){
			event.gui = new GuiSatchelsInventory(player);
    	}
    }
    
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
    	Entity entity = event.entity;
    	if(entity instanceof EntityPlayer) {
    		entity.registerExtendedProperties("satchels", new EntityPropertiesSatchels());
    	}
    }
	
	@SubscribeEvent
	public void onClientTick(ClientTickEvent event) {
		if(ConfigSatchels.hotSwap) {
			ConfigSatchels.reloadIfChanged();
		}
    	if(openEquipment.isPressed()) {
    		networkWrapper.sendToServer(new MessageOpenContainer(GuiHandler.ID_EQUIPMENT));
    	}
    }
	
	// Adapted from tconstruct.armor.TinkerArmorEvents#joinWorld
    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event)
    {
        if (event.entity instanceof EntityPlayerMP)
        {	
            EntityPlayerMP player = (EntityPlayerMP)event.entity;
            
            EntityPropertiesSatchels satchelsProps = (EntityPropertiesSatchels)player.getExtendedProperties("satchels");
            NBTTagCompound tag = new NBTTagCompound();
            satchelsProps.saveNBTData(tag);
            
            networkWrapper.sendTo(new MessageSyncEquipment(tag), player);
        }
        
    }
    
    @SubscribeEvent
    public void onItemTooltip(ItemTooltipEvent event) {
    	if(event.itemStack.getItem() instanceof ItemPouch) {
    		int slots = ItemPouch.getSlotCount(event.itemStack);
    		event.toolTip.add((slots > EntityPropertiesSatchels.POUCH_INITIAL_SLOTS ? "" + EnumChatFormatting.YELLOW : "") + slots + " slots");
    	} else if(event.itemStack.getItem() instanceof ItemPouchUpgrade) {
			event.toolTip.add("Adds 1 slot to a pouch");
    	}
    }
	
	public static void postPlayerConstructor(EntityPlayer player) {
		player.openContainer = player.inventoryContainer = new ContainerSatchels(player); 
	}
}
