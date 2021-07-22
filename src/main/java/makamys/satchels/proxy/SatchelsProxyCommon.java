package makamys.satchels.proxy;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Packets.MessageSyncEquipment;
import makamys.satchels.Satchels;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;

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
            
            Satchels.networkWrapper.sendTo(new MessageSyncEquipment(tag), player);
        }
    }
	
}
