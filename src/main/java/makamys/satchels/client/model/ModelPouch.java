package makamys.satchels.client.model;

import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelPouch extends ModelWearable {
	
	public static ModelWearable instance = new ModelPouch();
	
	private ModelRenderer pouchLeft;
	private ModelRenderer pouchRight;
	
	public ModelPouch(){
		bipedBody = new ModelRenderer(this);
		{
			pouchLeft = new ModelRenderer(this);
			pouchLeft.textureWidth = pouchLeft.textureHeight = 16;
			float x0 = 8f;
			float y0 = 15;
			float z0 = -3;
			int dx = 5;
			int dy = 5;
			int dz = 2;
			
			pouchLeft.addBox(0, 0, 0, dx, dy, dz);
			pouchLeft.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouchLeft);
			pouchLeft.rotateAngleY = -(float)(Math.PI / 2f);
		}
		{
			pouchRight = new ModelRenderer(this);
			pouchRight.textureWidth = pouchRight.textureHeight = 16;
			float x0 = -1.5f;
			float y0 = 15;
			float z0 = 5f;
			int dx = 5;
			int dy = 5;
			int dz = 2;
			
			pouchRight.addBox(0, 0, 0, dx, dy, dz);
			pouchRight.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouchRight);
			pouchRight.rotateAngleY = (float)(Math.PI);
		}
	}

	@Override
	protected ResourceLocation getTexture() {
		return new ResourceLocation(Satchels.MODID, "textures/models/pouch.png");
	}
	
	@Override
	protected float getScalePreRender(Entity entity, ModelBiped biped, boolean hasChestplate, EntityPropertiesSatchels props) {
		pouchLeft.isHidden = !props.hasLeftPouch();
		pouchRight.isHidden = !props.hasRightPouch();
		return DEFAULT_SCALE;
	}

	@Override
	protected boolean shouldRender(EntityPropertiesSatchels props) {
		return props.hasLeftPouch() || props.hasRightPouch();
	}
	
}
