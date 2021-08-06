package makamys.satchels.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import makamys.satchels.EntityPropertiesSatchels;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

@Mixin(InventoryPlayer.class)
public class MixinInventoryPlayer {
    
    boolean myAddItemStackToInventoryResult;
    
    @Inject(method = "addItemStackToInventory", at = @At("HEAD"))
    private void preAddItemStackToInventory(final ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        EntityPlayer player = ((InventoryPlayer)(Object)this).player;
        EntityPropertiesSatchels props = EntityPropertiesSatchels.fromPlayer(player);
        myAddItemStackToInventoryResult = props.preAddItemStackToInventory(stack);
    }
    
    @Inject(method = "addItemStackToInventory", at = @At("RETURN"), cancellable = true)
    private void postAddItemStackToInventory(final ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        EntityPlayer player = ((InventoryPlayer)(Object)this).player;
        EntityPropertiesSatchels props = EntityPropertiesSatchels.fromPlayer(player);
        myAddItemStackToInventoryResult |= props.postAddItemStackToInventory(stack);
        cir.setReturnValue(cir.getReturnValue() | myAddItemStackToInventoryResult);
    }
    
}
