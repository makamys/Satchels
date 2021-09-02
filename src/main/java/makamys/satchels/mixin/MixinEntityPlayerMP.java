package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import makamys.satchels.Satchels;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldSettings;

@Mixin(EntityPlayerMP.class)
public abstract class MixinEntityPlayerMP {
	
	@Inject(method = "setGameType", at = @At("RETURN"))
    private void preSetGameType(WorldSettings.GameType gameType, CallbackInfo ci) {
		Satchels.onGameTypeChanged(gameType, (EntityPlayer)(Object)this);
	}
	
}
