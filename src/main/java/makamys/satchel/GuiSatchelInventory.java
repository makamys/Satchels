package makamys.satchel;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;

public class GuiSatchelInventory extends GuiInventory {

	public GuiSatchelInventory(EntityPlayer p_i1094_1_) {
		super(p_i1094_1_);
		this.xSize += 2*16;
	}
	
	@Override
	public void initGui() {
		super.initGui();
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86 + 16, 16, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147001_a);
        int k = this.guiLeft + 16;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        func_147046_a(k + 51, l + 75, 30, (float)(k + 51) - (float)p_146976_2_, (float)(l + 75 - 50) - (float)p_146976_3_, this.mc.thePlayer);
	}

}
