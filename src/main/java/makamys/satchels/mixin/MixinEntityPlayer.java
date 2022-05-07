package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import makamys.satchels.Satchels;
import net.minecraft.entity.player.EntityPlayer;

@Mixin(EntityPlayer.class)
public class MixinEntityPlayer {
    
    @Inject(method = "<init>*", at=@At("RETURN"))
    public void postPlayerConstructor(CallbackInfo ci) {
        Satchels.postPlayerConstructor((EntityPlayer)(Object)this);
    }
    
}
