package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import makamys.satchels.Satchels;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.world.WorldSettings;

@Mixin(ItemInWorldManager.class)
public abstract class MixinItemInWorldManager {
    
    @Inject(method = "setGameType", at = @At("RETURN"))
    private void postSetGameType(WorldSettings.GameType gameType, CallbackInfo ci) {
        Satchels.onGameTypeChanged(gameType, ((ItemInWorldManager)(Object)this).thisPlayerMP);
    }   
}    
