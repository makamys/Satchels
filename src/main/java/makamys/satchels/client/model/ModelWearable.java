package makamys.satchels.client.model;

import makamys.satchels.EntityPropertiesSatchels;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public abstract class ModelWearable extends ModelBiped {
	
	protected final float DEFAULT_SCALE = 1/24f;
	
	@Override
	public void render(Entity entity, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_,
			float p_78088_6_, float scale) {
		EntityPlayer player = (EntityPlayer)entity;
		
		this.isSneak = entity.isSneaking();
		this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, scale, entity);
		
		boolean hasChestplate = player.getCurrentArmor(2) != null;
		this.bipedBody.render(preRender(entity, hasChestplate, EntityPropertiesSatchels.fromPlayer((EntityPlayer)entity)));
	}
	
	protected float preRender(Entity entity, boolean hasChestplate, EntityPropertiesSatchels props) {
		return DEFAULT_SCALE;
	}
	
	public void renderPlayer(RenderPlayerEvent.Specials.Pre event) {
		EntityPropertiesSatchels props = EntityPropertiesSatchels.fromPlayer(event.entityPlayer);
		if(shouldRender(props)) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(getTexture());
			setLivingAnimations(event.entityPlayer, 0, 0, 0);
			render(event.entityPlayer, 0, 0, 0, 0, 0, 0);
		}
	}
	
	protected abstract ResourceLocation getTexture();
	protected abstract boolean shouldRender(EntityPropertiesSatchels props);
	
}
