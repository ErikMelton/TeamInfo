package kovu.teamstats.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.EventPriority;
import net.minecraftforge.event.ForgeSubscribe;

public class GuiDraggable extends Gui
{
	private Minecraft mc;
    protected int width;
    protected int height;
    public int xPosition;
    public int yPosition;
    public String displayString;
    public int id;

	public GuiDraggable(Minecraft mc, int width, int height, int xPos, int yPos, int id, String string)
	{
		this.mc = mc;
        this.width = width;
        this.height = height;
        this.id = id;
        this.xPosition = xPos;
        this.yPosition = yPos;
        this.displayString = string;

	}
	
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
    {
            FontRenderer fontrenderer = par1Minecraft.fontRenderer;
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            int l = 14737632;

            this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
    }

    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {}

    public void mouseReleased(int par1, int par2) {}

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
    	return false;
    }

	@ForgeSubscribe(priority = EventPriority.NORMAL)
	public void eventHandler(RenderGameOverlayEvent event)
	{
		new GuiTeamInfoIngame();
	}	
}
