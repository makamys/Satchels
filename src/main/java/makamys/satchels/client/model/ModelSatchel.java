package makamys.satchels.client.model;

import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelSatchel extends ModelWearable {
	
	public static ModelWearable instance = new ModelSatchel();
	
	public ModelSatchel(){
		bipedBody = new ModelRenderer(this);
		{
			ModelRenderer satchel = new ModelRenderer(this);
			float x0 = -7.5f;
			float y0 = 13;
			float z0 = -4;
			int dx = 2;
			int dy = 7;
			int dz = 8;
			
			satchel.addBox(0, 0, 0, dx, dy, dz);
			satchel.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(satchel);
			satchel.rotateAngleZ = 0.05f;
		}
		{
			ModelRenderer strap = new ModelRenderer(this);
			float x0 = -6f;
			float y0 = 13;
			float z0 = -4;
			int dx = 1;
			int dy = -19;
			int dz = 8;
			
			strap.addBox(0, 0, 0, dx, dy, dz);
			strap.setRotationPoint(x0-0.2f, y0+0.4f, z0);
			bipedBody.addChild(strap);
			strap.rotateAngleZ = 0.75f;
		}
	}
	
	@Override
	protected float preRender(Entity entity, boolean hasChestplate, EntityPropertiesSatchels props) {
		this.bipedBody.offsetY = hasChestplate ? -0.08f : 0f;
		return hasChestplate ? 1/20f : 1/24f;
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
