package makamys.satchels.client.model;

import makamys.satchels.Satchels;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class ModelPouch extends ModelBiped {
	
	public static ModelBase instance = new ModelPouch();
	public static ResourceLocation texture = new ResourceLocation(Satchels.MODID, "textures/models/satchel.png");
	
	public ModelPouch(){
		bipedBody = new ModelRenderer(this);
		{
			ModelRenderer pouch = new ModelRenderer(this);
			float x0 = 6f;
			float y0 = 15;
			float z0 = -3;
			int dx = 2;
			int dy = 4;
			int dz = 5;
			
			pouch.addBox(0, 0, 0, dx, dy, dz);
			pouch.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouch);
			pouch.rotateAngleZ = 0.05f;
		}
		{
			ModelRenderer pouch = new ModelRenderer(this);
			float x0 = -6.5f;
			float y0 = 15;
			float z0 = 5f;
			int dx = 5;
			int dy = 4;
			int dz = -2;
			
			pouch.addBox(0, 0, 0, dx, dy, dz);
			pouch.setRotationPoint(x0, y0, z0);
			bipedBody.addChild(pouch);
			//pouch.rotateAngleZ = 0.05f;
		}
	}
	
	@Override
	public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float scale) {
		EntityPlayer player = (EntityPlayer)p_78088_1_;
		
		this.isSneak = p_78088_1_.isSneaking();
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, p_78088_1_);
		
		boolean chestplate = false;//player.getCurrentArmor(2) != null;
		this.bipedBody.offsetY = chestplate ? -0.08f : 0f;
		this.bipedBody.render(chestplate ? 1/20f : 1/24f);
	}
	
}
