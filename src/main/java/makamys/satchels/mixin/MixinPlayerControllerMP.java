package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import makamys.satchels.Satchels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.world.WorldSettings;

@Mixin(PlayerControllerMP.class)
public abstract class MixinPlayerControllerMP {
    
    @Inject(method = "setGameType", at = @At("RETURN"))
    private void postSetGameType(WorldSettings.GameType gameType, CallbackInfo ci) {
        Satchels.onGameTypeChanged(gameType, Minecraft.getMinecraft().thePlayer);
    }
    
}
