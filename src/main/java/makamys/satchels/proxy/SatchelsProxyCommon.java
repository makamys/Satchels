package makamys.satchels.proxy;

import codechicken.lib.inventory.InventoryUtils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Packets.MessageSyncEquipment;
import makamys.satchels.Satchels;
import makamys.satchels.inventory.ContainerSatchels;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;

public class SatchelsProxyCommon {
	
	public void init() {
		
	}
	
	public void postInit() {
		
	}
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
    	Entity entity = event.entity;
    	if(entity instanceof EntityPlayer) {
    		entity.registerExtendedProperties("satchels", new EntityPropertiesSatchels());
    	}
    }
	
	// Adapted from tconstruct.armor.TinkerArmorEvents#joinWorld
    @SubscribeEvent
    public void onJoinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP) {	
            EntityPlayerMP player = (EntityPlayerMP)event.entity;
            
            EntityPropertiesSatchels satchelsProps = EntityPropertiesSatchels.fromPlayer(player);
            NBTTagCompound tag = new NBTTagCompound();
            satchelsProps.saveNBTData(tag);
            ((ContainerSatchels)player.inventoryContainer).redoSlots(!player.capabilities.isCreativeMode);
            
            Satchels.networkWrapper.sendTo(new MessageSyncEquipment(tag), player);
        }
    }
    
    // Adapted from tconstruct.armor.player.TPlayerHandler#playerDrops
    @SubscribeEvent
    public void onPlayerDrops(PlayerDropsEvent event) {
    	EntityPropertiesSatchels props = EntityPropertiesSatchels.fromPlayer(event.entityPlayer);
    	
        if (event.entityPlayer.capturedDrops != event.drops) {
            event.entityPlayer.capturedDrops.clear();
        }
    	
        event.entityPlayer.captureDrops = true;
        props.dropItems();
        event.entityPlayer.captureDrops = false;
        
        if (event.entityPlayer.capturedDrops != event.drops) {
            event.drops.addAll(event.entityPlayer.capturedDrops);
        }
    }
	
}
