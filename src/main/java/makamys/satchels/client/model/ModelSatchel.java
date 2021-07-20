package makamys.satchels.client.model;

import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class ModelSatchel extends ModelBiped {
	
	public static ModelBase instance = new ModelSatchel();
	public static ResourceLocation texture = new ResourceLocation(Satchels.MODID, "textures/models/satchel.png");
	
	public ModelSatchel(){
		bipedBody = new ModelRenderer(this);
		
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
	
	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float scale) {
		this.isSneak = p_78088_1_.isSneaking();
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, p_78088_1_);
		
		this.bipedBody.render(1/24f);
	}
	
}
