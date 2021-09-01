package makamys.satchels.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import makamys.satchels.ConfigSatchels;
import makamys.satchels.Satchels;
import makamys.satchels.inventory.ContainerSatchels;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;

public class GuiSatchelsInventory extends GuiInventory {

	boolean movedButtons = false;
	
	ContainerSatchels satchelsSlots;
	
	private int originalYSize;
	Map<GuiButton, Pair<Integer, Integer>> originalButtonPositions = new HashMap<>();
	
	public static final int playerX = 25,
							playerY = 7,
							playerW = 54,
							playerH = 72;
	
	protected static final ResourceLocation sidebar_corners = new ResourceLocation(Satchels.MODID, "textures/gui/container/sidebar_corners.png"); 
	
	
	public GuiSatchelsInventory(EntityPlayer p_i1094_1_) {
		super(p_i1094_1_);
		this.satchelsSlots = (ContainerSatchels)p_i1094_1_.inventoryContainer;
		this.xSize += 2*16;
		originalYSize = this.ySize;
	}
	
	@Override
	public void initGui() {
		super.initGui();
		//if(satchelsSlots.dirty) {
		redoGui();
		//}
	}
	
	private void redoGui() {
		this.ySize = originalYSize + (satchelsSlots.satchelProps.hasSatchel() ? 16 : 0);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
		this.fontRendererObj.drawString(I18n.format("container.crafting", new Object[0]), 86 + 16, 16, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		boolean hasSatchel = satchelsSlots.satchelProps.hasSatchel();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147001_a);
        int k = this.guiLeft + 16;
        int l = this.guiTop;
        
        int playerXOff = satchelsSlots.getArmorXOffset();
        
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, playerY);
        
        this.drawTexturedModalRect(k, l + playerY, 0, playerY, 7, playerH);
        this.drawTexturedModalRect(k + 7, l + playerY, 3, playerY, playerXOff, playerH);
        this.drawTexturedModalRect(k + 7 + playerXOff, l + playerY, 7, playerY, 18, playerH);
        this.drawTexturedModalRect(k + playerX + playerXOff, l + playerY, playerX, playerY, playerW / 2, playerH);
        this.drawTexturedModalRect(k + playerX + playerXOff + playerW / 2, l + playerY, playerX + playerW / 2 + playerXOff, playerY, playerW - playerW / 2 - playerXOff, playerH);
        this.drawTexturedModalRect(k + playerX + playerW, l + playerY, playerX + playerW, playerY, this.xSize - (playerX + playerW), playerH);
        
        this.drawTexturedModalRect(k, l + playerY + playerH, 0, playerY + playerH, this.xSize, 2);
        
        if(hasSatchel) {
	        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
	        ConfigSatchels.satchelBgColor.glColour();
	        this.drawTexturedModalRect(k, l+80+1, 0, 80+1, this.xSize, 18+2);
	        GL11.glPopAttrib();
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(k, l + 80 + (hasSatchel ? 18 + 3 : 0), 0, 80 + (hasSatchel ? 3 : 0), this.xSize, this.ySize - 80 - (hasSatchel ? 16 + 3 : 0));
        
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
        ConfigSatchels.pouchBgColor.glColour();
        for(int side = 0; side < 2; side++) {
        	List<Slot> pouchSlots = side == 0 ? satchelsSlots.getEnabledLeftPouchSlots() : satchelsSlots.getEnabledRightPouchSlots();
	        if(!pouchSlots.isEmpty()) {
	        	Slot first = pouchSlots.get(0);
	        	int no = pouchSlots.size();
	        	
	        	boolean left = side == 0;
	        	
	        	int xOff = left ? -24 : -17;
	        	
	        	int firstX = k+first.xDisplayPosition+xOff;
	        	int firstY = l+first.yDisplayPosition-1;
	        	
	        	int u = left ? 0 : 151;
	        	int uOff = left ? 0 : 7;
	        	int edgeXOff = left ? 18 : 0;
	        	
	        	int bottomCornerU = left ? 0 : 7;
	        	int bottomCornerV = 0;
	        	int topCornerU = left ? 0 : 7;
	        	int topCornerV = no == 3 ? 7 : 14;
	        	
	        	this.drawTexturedModalRect(firstX+uOff, firstY-18*no+11, u + uOff, 0, 18, 7);
	        	
	            for(int i = 0; i < no; i++) {
	                this.drawTexturedModalRect(firstX, firstY-18*i, u, 141, 25, 18);
	            }
	            this.drawTexturedModalRect(firstX+uOff, firstY+18, u + uOff, 141+18, 18, 7);
	            
	            if(no == 8 && !hasSatchel) {
	            	this.drawTexturedModalRect(firstX+edgeXOff, firstY-18*no+11, left ? 169 : 0, 0, 7, 7);
	            }
	            
	            GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);
	            this.mc.getTextureManager().bindTexture(sidebar_corners);
	            this.drawTexturedModalRect(firstX+edgeXOff, firstY-18*no+11, topCornerU, topCornerV, 7, 7);
	            this.drawTexturedModalRect(firstX+edgeXOff, firstY+18, bottomCornerU, bottomCornerV, 7, 7);
	            GL11.glPopAttrib();
	        }
        }
        
        GL11.glPopAttrib();
        
        func_147046_a(k + playerX + 26, l + playerY + 68, 30, (float)(k + 51) - (float)p_146976_2_, (float)(l + 75 - 50) - (float)p_146976_3_, this.mc.thePlayer);
        
        for(int i = 0; i < this.buttonList.size(); i++) {
            GuiButton button = (GuiButton)this.buttonList.get(i);
            int supposedTop = (this.height - this.originalYSize) / 2;
            button.yPosition = getOriginalButtonPosition(button).getRight() + (guiTop - supposedTop);
        }
	}
	
	private Pair<Integer, Integer> getOriginalButtonPosition(GuiButton button) {
	    Pair<Integer, Integer> originalPos = originalButtonPositions.get(button);
	    if(originalPos == null) {
	        originalButtonPositions.put(button, (originalPos = Pair.of(button.xPosition, button.yPosition)));
	    }
	    return originalPos;
	}

}
