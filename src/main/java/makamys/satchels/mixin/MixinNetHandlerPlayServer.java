package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import makamys.satchels.Satchels;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.C16PacketClientStatus;

@Mixin(NetHandlerPlayServer.class)
public class MixinNetHandlerPlayServer {
	
	@Inject(method = "processClientStatus", at = @At("HEAD"))
	public void preProcessClientStatus(C16PacketClientStatus status, CallbackInfo ci) {
		Satchels.preProcessClientStatus(status, ((NetHandlerPlayServer)(Object)this).playerEntity);
	}
	
}
