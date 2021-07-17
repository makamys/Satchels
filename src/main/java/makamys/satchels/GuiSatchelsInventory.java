package makamys.satchels;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

public class GuiSatchelsInventory extends GuiInventory {

	boolean movedButtons = false;
	
	ContainerSatchels satchelsSlots;
	
	private int originalYSize;
	List<Pair<Integer, Integer>> originalButtonPositions;
	
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
		float r = 1.0F;
		float g = 0.7F;
		float b = 0.4F;
		
		boolean hasSatchel = satchelsSlots.satchelProps.hasSatchel();
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(field_147001_a);
        int k = this.guiLeft + 16;
        int l = this.guiTop;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 80);
        
        if(hasSatchel) {
        	this.drawTexturedModalRect(k, l+80, 0, 80, this.xSize, 3);
	        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
	        GL11.glColor4f(r, g, b, 1.0F);
	        this.drawTexturedModalRect(k, l+80+1, 0, 80+1, this.xSize, 18+2);
	        GL11.glPopAttrib();
        }
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(k, l + 80 + (hasSatchel ? 18 + 3 : 0), 0, 80 + (hasSatchel ? 3 : 0), this.xSize, this.ySize - 80 - (hasSatchel ? 16 + 3 : 0));
        
        GL11.glPushAttrib(GL11.GL_CURRENT_BIT);
        GL11.glColor4f(r, g, b, 1.0F);
        for(int side = 0; side < 2; side++) {
        	List<Slot> pouchSlots = side == 0 ? satchelsSlots.getEnabledLeftPouchSlots() : satchelsSlots.getEnabledRightPouchSlots();
	        if(!pouchSlots.isEmpty()) {
	        	Slot first = pouchSlots.get(0);
	        	int no = pouchSlots.size();
	        	
	        	int xOff = side == 0 ? -24 : -17;
	        	
	        	int firstX = k+first.xDisplayPosition+xOff;
	        	int firstY = l+first.yDisplayPosition-1;
	        	
	        	int u = side == 0 ? 0 : 151;
	        	
	        	this.drawTexturedModalRect(firstX, firstY-18*no+12, u, 0, 25, 6);
	            for(int i = 0; i < no; i++) {
	                this.drawTexturedModalRect(firstX, firstY-18*i, u, 141, 25, 18);
	            }
	            this.drawTexturedModalRect(firstX, firstY+18, u, 141+18, 25, 7);
	        }
        }
        
        GL11.glPopAttrib();
        
        func_147046_a(k + 51, l + 75, 30, (float)(k + 51) - (float)p_146976_2_, (float)(l + 75 - 50) - (float)p_146976_3_, this.mc.thePlayer);
        
        if(originalButtonPositions == null) {
        	originalButtonPositions = new ArrayList<>();
        	for(GuiButton button : (List<GuiButton>)this.buttonList) {
        		originalButtonPositions.add(Pair.of(button.xPosition, button.yPosition));
        	}
        }
        
		for(int i = 0; i < this.buttonList.size(); i++) {
			GuiButton button = (GuiButton)this.buttonList.get(i);
			// TODO buttons below the extra row should be pushed downwards, not upwards
			button.yPosition = originalButtonPositions.get(i).getRight() + (hasSatchel ? -8 : 0);
		}
        	
	}

}
