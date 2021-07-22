package makamys.satchels.client.model;

import java.util.Arrays;
import java.util.List;

import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelSatchel extends ModelWearable {
	
	public static ModelWearable instance = new ModelSatchel();
	ModelBiped satchelBiped;
	ModelRenderer satchel;
	ModelBiped strapBiped;
	ModelRenderer strap;
	public ModelSatchel(){
		bipedBody = new ModelRenderer(this, 0, 0);
		{
			satchelBiped = new ModelBiped();
			satchel = new ModelRenderer(satchelBiped);
			satchel.textureWidth = satchel.textureHeight = 32;
			float x0 = -5.5f;
			float y0 = 13;
			float z0 = -4;
			int dx = 8;
			int dy = 7;
			int dz = 2;
			
			satchel.addBox(0, 0, 0, dx, dy, dz);
			satchel.setRotationPoint(x0, y0, z0);
			satchelBiped.bipedBody.addChild(satchel);
			satchel.rotateAngleZ = 0.05f;
			satchel.rotateAngleY = -(float)(Math.PI / 2f);
		}
		{
			strapBiped = new ModelBiped();
			strap = new ModelRenderer(strapBiped, 0, 9);
			strap.textureWidth = strap.textureHeight = 32;
			float x0 = 7.5f;
			float y0 = -0.1f;
			float z0 = -4;
			int dx = 8;
			int dy = 19;
			int dz = 1;
			
			strap.addBox(0, 0, 0, dx, dy, dz);
			strap.setRotationPoint(x0-0.2f, y0+0.4f, z0);
			strapBiped.bipedBody.addChild(strap);
			strap.rotateAngleY = -(float)(Math.PI / 2f);
			strap.rotateAngleZ = 0.75f;
		}
	}
	
	@Override
	protected float getScalePreRender(Entity entity, ModelBiped biped, boolean hasChestplate, EntityPropertiesSatchels props) {
		ModelRenderer model = biped == satchelBiped ? satchel : strap;
		model.offsetY = hasChestplate ? -0.08f : 0f;
		float scale = hasChestplate ? 1/20f : 1/24f;
		
		return scale * (biped == satchelBiped ? 1f : 1.01f);
	}
	
	@Override
	protected List<ModelBiped> getBipeds() {
		return Arrays.asList(satchelBiped, strapBiped);
	}

	@Override
	protected ResourceLocation getTexture() {
		return new ResourceLocation(Satchels.MODID, "textures/models/satchel.png");
	}

	@Override
	protected boolean shouldRender(EntityPropertiesSatchels props) {
		return props.hasSatchel();
	}
	
}
