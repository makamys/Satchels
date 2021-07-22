package makamys.satchels;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

public class Packets {
	
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
			EntityPlayer player = getClientPlayer();
			EntityPropertiesSatchels satchelsProps = EntityPropertiesSatchels.fromPlayer(player);
			satchelsProps.loadNBTData(message.tag);
			return null;
		}
		
		@SideOnly(Side.CLIENT)
		private EntityPlayer getClientPlayer() {
			return Minecraft.getMinecraft().thePlayer;
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
	
}
