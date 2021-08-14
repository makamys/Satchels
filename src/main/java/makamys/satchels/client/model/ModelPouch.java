package makamys.satchels.client.model;

import makamys.satchels.ConfigSatchels;
import makamys.satchels.EntityPropertiesSatchels;
import makamys.satchels.Satchels;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class ModelPouch extends ModelAccessory {
	
	public static ModelAccessory instance;
	
	private AccessoryModelRenderer pouchLeft;
	private AccessoryModelRenderer pouchRight;
	
	private ResourceLocation TEXTURE = new ResourceLocation(Satchels.MODID, "textures/models/pouch.png");
	
	public ModelPouch(RenderPlayer rp){
	    super(rp);
		{
			pouchLeft = new AccessoryModelRenderer(rp.modelBipedMain).setTexture(TEXTURE).setScaleMod(DEFAULT_SCALE_MOD);
			pouchLeft.textureWidth = pouchLeft.textureHeight = 16;
			float x0 = 8f;
			float y0 = 15;
			float z0 = -3;
			int dx = 5;
			int dy = 5;
			int dz = 2;
			
			pouchLeft.addBox(0, 0, 0, dx, dy, dz);
			pouchLeft.setRotationPoint(x0, y0, z0);
			rp.modelBipedMain.bipedBody.addChild(pouchLeft);
			pouchLeft.rotateAngleY = -(float)(Math.PI / 2f);
		}
		{
			pouchRight = new AccessoryModelRenderer(rp.modelBipedMain).setTexture(TEXTURE).setScaleMod(DEFAULT_SCALE_MOD);
			pouchRight.textureWidth = pouchRight.textureHeight = 16;
			float x0 = -1.5f;
			float y0 = 15;
			float z0 = 5f;
			int dx = 5;
			int dy = 5;
			int dz = 2;
			
			pouchRight.addBox(0, 0, 0, dx, dy, dz);
			pouchRight.setRotationPoint(x0, y0, z0);
			rp.modelBipedMain.bipedBody.addChild(pouchRight);
			pouchRight.rotateAngleY = (float)(Math.PI);
		}
	}
	
	@Override
    public void preRender(RenderPlayerEvent.Specials.Pre event) {
	    EntityPropertiesSatchels props = EntityPropertiesSatchels.fromPlayer(event.entityPlayer);
	    pouchLeft.isHidden = !props.hasLeftPouch() || !ConfigSatchels.drawLeftPouch;
        pouchRight.isHidden = !props.hasRightPouch() || !ConfigSatchels.drawRightPouch;
    }
	
}
