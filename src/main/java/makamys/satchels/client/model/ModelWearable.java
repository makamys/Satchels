package makamys.satchels.client.model;

import java.util.Arrays;
import java.util.List;

import makamys.satchels.EntityPropertiesSatchels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public abstract class ModelWearable {
	
	protected final float DEFAULT_SCALE = 1/24f;
	
	protected final RenderPlayer rp;
	
	public ModelWearable(RenderPlayer rp){
	    this.rp = rp;
	}
	
	public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
		
	}
	
}
