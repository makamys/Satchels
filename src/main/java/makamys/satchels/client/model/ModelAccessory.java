package makamys.satchels.client.model;

import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;

public abstract class ModelAccessory {
    
    protected final float DEFAULT_SCALE_MOD = 16f/24f;
    
    protected final RenderPlayer rp;
    
    public ModelAccessory(RenderPlayer rp){
        this.rp = rp;
    }
    
    public void preRender(RenderPlayerEvent.Specials.Pre event) {
        
    }
    
}
