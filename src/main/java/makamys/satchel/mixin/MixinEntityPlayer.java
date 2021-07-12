package makamys.satchel.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import makamys.satchel.ContainerSatchel;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
	
	@Inject(method = "<init>*", at=@At("RETURN"))
	public void postPlayerConstructor(CallbackInfo ci) {
		EntityPlayer dis = (EntityPlayer)(Object)this;
		
		dis.inventoryContainer = dis.openContainer = new ContainerSatchel(dis);
	}
	
}
