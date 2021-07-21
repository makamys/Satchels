package makamys.satchels.client.model;

import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
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
			float x0 = 6f;
			float y0 = 15;
			float z0 = -3;
			int dx = 2;
			int dy = 4;
			int dz = 5;
			
			pouchLeft.addBox(0, 0, 0, dx, dy, dz);
			pouchLeft.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouchLeft);
			pouchLeft.rotateAngleZ = 0.05f;
		}
		{
			pouchRight = new ModelRenderer(this);
			float x0 = -6.5f;
			float y0 = 15;
			float z0 = 5f;
			int dx = 5;
			int dy = 4;
			int dz = -2;
			
			pouchRight.addBox(0, 0, 0, dx, dy, dz);
			pouchRight.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouchRight);
		}
	}

	@Override
	protected ResourceLocation getTexture() {
		return new ResourceLocation(Satchels.MODID, "textures/models/pouch.png");
	}
	
	@Override
	protected float preRender(Entity entity, boolean hasChestplate, EntityPropertiesSatchels props) {
		pouchLeft.isHidden = !props.hasLeftPouch();
		pouchRight.isHidden = !props.hasRightPouch();
		return super.preRender(entity, hasChestplate, props);
	}

	@Override
	protected boolean shouldRender(EntityPropertiesSatchels props) {
		return props.hasLeftPouch() || props.hasRightPouch();
	}
	
}
