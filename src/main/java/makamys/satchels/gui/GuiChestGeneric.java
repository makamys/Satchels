package makamys.satchels.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import makamys.satchels.inventory.ContainerChestGeneric;
import makamys.satchels.inventory.SlotCustom;
import makamys.satchels.item.ItemPouch;
import makamys.satchels.item.ItemPouchUpgrade;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.function.Predicate;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GuiChestGeneric extends GuiContainer
{
    protected static final ResourceLocation chestGenericTexture = new ResourceLocation("satchels", "textures/gui/container/chest_generic.png");
    
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;
    /** window height is calculated with these values; the more rows, the heigher */
    private int inventoryRows;
    int rowSize;
    
    public GuiChestGeneric(IInventory p_i1083_1_, IInventory p_i1083_2_) {
        this(p_i1083_1_, p_i1083_2_, 9);
    }
    
    public GuiChestGeneric(IInventory p_i1083_1_, IInventory p_i1083_2_, int rowSize)
    {
        super(ItemPouch.constructUpgradesContainer(p_i1083_1_, p_i1083_2_));
        this.rowSize = rowSize = ((ContainerChestGeneric)this.inventorySlots).getRowSize();
        
        this.upperChestInventory = p_i1083_1_;
        this.lowerChestInventory = p_i1083_2_;
        this.allowUserInput = false;
        short short1 = 222;
        int i = short1 - 108;
        this.inventoryRows = (int)Math.ceil(p_i1083_2_.getSizeInventory() / (float)rowSize);
        this.ySize = i + this.inventoryRows * 18;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        this.fontRendererObj.drawString(this.lowerChestInventory.hasCustomInventoryName() ? this.lowerChestInventory.getInventoryName() : I18n.format(this.lowerChestInventory.getInventoryName(), new Object[0]), 8, 6, 4210752);
        this.fontRendererObj.drawString(this.upperChestInventory.hasCustomInventoryName() ? this.upperChestInventory.getInventoryName() : I18n.format(this.upperChestInventory.getInventoryName(), new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(chestGenericTexture);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, 17);
        
        int slotStartX = this.inventorySlots.getSlot(0).xDisplayPosition;
        
        for(int row = 0; row < Math.ceil(lowerChestInventory.getSizeInventory() / (float)rowSize); row++) {
            this.drawTexturedModalRect(k, l + 17 + row * 18, 0, 133, this.xSize, 18);
            
            this.drawTexturedModalRect(k + slotStartX - 1, l + 17 + row * 18, 7, 19, rowSize * 18, 18);
        }
        this.drawTexturedModalRect(k, l + this.inventoryRows * 18 + 17, 0, 37, this.xSize, 96);
    }
}