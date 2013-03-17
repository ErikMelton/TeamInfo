package kovu.teamstats;

import org.lwjgl.input.Mouse;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class GuiDraggableElement extends Gui {

    int x, y, height, width, mouseOffsetX, mouseOffsetY;

    public GuiDraggableElement(int x, int y, int height, int width) {
        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;
    }

    public void drawElement() {
        Minecraft.getMinecraft().fontRenderer.drawString("Drag Me!!!", x, y, 0xFFFFFF);
    }

    //Call as: mouseDragged(mc, Mouse.getX(), Mouse.getY())
    public void mouseDragged(Minecraft par1Minecraft, int par2, int par3) {
        par3 = -(par3 - par1Minecraft.displayHeight) / 2;
        par2 /= 2;
        if (Mouse.isButtonDown(0) && mousePressed(par1Minecraft, par2, par3)) {
            System.out.println("IS DOWN");
            x = par2 - mouseOffsetX;
            y = par3 - mouseOffsetY;
            mouseOffsetX = 0;
            mouseOffsetY = 0;
        }
    }

    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3) {
        boolean is = par2 >= x && par3 >= y && par2 < x + width && par3 < y + height;
        if (is) {
            int currentXOffset = mouseOffsetX;
            int currentYOffset = mouseOffsetY;

            mouseOffsetX = par2 - x;
            mouseOffsetY = par3 - y;
            return true;
        }

        return false;
    }
}
