package makamys.satchels.client.model;

import java.util.Arrays;
import java.util.List;

import makamys.satchels.ConfigSatchels;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ModelSatchel extends ModelWearable {
	
	public static ModelWearable instance;
	ModelRenderer satchel;
	ModelRenderer strap;
	
	private static final ResourceLocation TEXTURE = new ResourceLocation(Satchels.MODID, "textures/models/satchel.png");
	
	public ModelSatchel(RenderPlayer rp){
	    super(rp);
		{
			satchel = new AccessoryModelRenderer(rp.modelBipedMain).setTexture(TEXTURE).setScaleMod(16f / 24f);
			satchel.textureWidth = satchel.textureHeight = 32;
			float x0 = -7.5f;
			float y0 = 13;
			float z0 = 4;
			int dx = 8;
			int dy = 7;
			int dz = 2;
			
			satchel.addBox(0, 0, 0, dx, dy, dz);
			satchel.setRotationPoint(x0, y0, z0);
			rp.modelBipedMain.bipedBody.addChild(satchel);
			satchel.rotateAngleZ = 0.05f;
			satchel.rotateAngleY = (float)(Math.PI / 2f);
		}
		{
			strap = new AccessoryModelRenderer(rp.modelBipedMain, 0, 9).setTexture(TEXTURE).setScaleMod((16f / 24f) * 1.001f);
			strap.textureWidth = strap.textureHeight = 32;
			float x0 = 7.5f;
			float y0 = -0.1f;
			float z0 = -4;
			int dx = 8;
			int dy = 19;
			int dz = 1;
			
			strap.addBox(0, 0, 0, dx, dy, dz);
			strap.setRotationPoint(x0-0.2f, y0+0.4f, z0);
			rp.modelBipedMain.bipedBody.addChild(strap);
			strap.rotateAngleY = -(float)(Math.PI / 2f);
			strap.rotateAngleZ = 0.75f;
		}
	}
	/*
	@Override
	protected float getScalePreRender(Entity entity, ModelBiped biped, boolean hasChestplate, EntityPropertiesSatchels props) {
		
	}
	
	@Override
	protected List<ModelBiped> getBipeds() {
		return Arrays.asList(satchelBiped, strapBiped);
	}*/

	@Override
	public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
        boolean hasChestplate = false;//event.entityPlayer.getCurrentArmor(2) != null;
        
        boolean draw = EntityPropertiesSatchels.fromPlayer(event.entityPlayer).hasSatchel();
        for(ModelRenderer amr : Arrays.asList(satchel, strap)) {
            amr.isHidden = draw && (amr == satchel ? !ConfigSatchels.drawSatchel : (!ConfigSatchels.drawSatchel || !ConfigSatchels.drawSatchelStrap));
            amr.offsetY = hasChestplate ? -0.08f : 0f;
            
            //float scale = hasChestplate ? 1/20f : 1/24f;
            
            //return scale * (biped == satchelBiped ? 1f : 1.01f);
        }
    }
	
}
