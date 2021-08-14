package makamys.satchels.client.model;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;

public class AccessoryModelRenderer extends ModelRenderer {

    public float scaleMod;
    public ResourceLocation texture;
    
    public AccessoryModelRenderer(ModelBase base) {
        super(base);
    }
    
    public AccessoryModelRenderer(ModelBase base, int i, int j) {
        super(base, i, j);
    }
    
    public AccessoryModelRenderer(ModelBase base, String str) {
        super(base, str);
    }
    
    public AccessoryModelRenderer setScaleMod(float scaleMod) {
        this.scaleMod = scaleMod;
        return this;
    }
    
    public AccessoryModelRenderer setTexture(ResourceLocation texture) {
        this.texture = texture;
        return this;
    }
    
    public void render(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        super.render(scale * scaleMod);
        
        GL11.glPopAttrib();
    }

    public void renderWithRotation(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        super.renderWithRotation(scale * scaleMod);
        
        GL11.glPopAttrib();
    }

    public void postRender(float scale) {
        GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
        
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        super.postRender(scale * scaleMod);
        
        GL11.glPopAttrib();
    }

}
