package makamys.satchels.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;

public class DummyPlayer extends EntityPlayer {
	public DummyPlayer(EntityPlayer original) {
		super(original.worldObj, original.getGameProfile());
	}

	@Override
	public void addChatMessage(IChatComponent p_145747_1_) {
		
	}

	@Override
	public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
		return false;
	}

	@Override
	public ChunkCoordinates getPlayerCoordinates() {
		return null;
	}
}
