package kovu.teaminfo;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class TestGui extends Gui 
{
	int x, y, xx, yy, xxx, yyy, height, width, mouseOffsetX = 0, mouseOffsetY = 0;
	// xx and yy are points which are clicked on initially, xxx and yyy are the orig X and Y perclick
	
	boolean isPressed;
	 
	public TestGui(int x, int y, int height, int width) 
	{
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
	}
	
	public void drawElement() 
	{
		Minecraft.getMinecraft().fontRenderer.drawString("Drag Me!!!", x, y, 0xFFFFFF);
	}
	
	//Call as: mouseDragged(mc, Mouse.getX(), Mouse.getY())
    public void mouseDragged(Minecraft par1Minecraft, int mX, int mY) 
    {
    	mY = -(mY - par1Minecraft.displayHeight)/2;
    	mX /= 2;
    	if(Mouse.isButtonDown(0) && mousePressed(par1Minecraft, mX, mY))
    	{
    		System.out.println("===== Before =====");
    		System.out.println("x = " + x);
    		System.out.println("mX = " + mX);
    		System.out.println("y = " + y);
    		System.out.println("mY = " + mY);
    		System.out.println("mouseOffsetX = " + mouseOffsetX);
    		System.out.println("mouseOffsetY = " + mouseOffsetY);
    		System.out.println("===== After =====");
    		x = xxx + mouseOffsetX;
    		y = yyy + mouseOffsetY;
    		System.out.println("x = " + x);
    		System.out.println("mX = " + mX);
    		System.out.println("y = " + y);
    		System.out.println("mY = " + mY);
    		System.out.println("mouseOffsetX = " + mouseOffsetX);
    		System.out.println("mouseOffsetY = " + mouseOffsetY);
    		System.out.println("===== Done =====");
    		System.out.println("");
    		mouseOffsetX = 0;
    		mouseOffsetY = 0;
    	}
    	else
    	{
    		isPressed = false;
    	}
    }
    
    public boolean mousePressed(Minecraft par1Minecraft, int mX, int mY) 
    {
    	System.out.println("PRESSED");
    	if(mX >= (x - 20) && mY >= (y - 20) && mX < (x + width) && mY < (y + height))
    	{ 
    		if(!isPressed)
    		{
    			isPressed = true;
    			xx = mX;
    			yy = mY;
    			xxx = x;
    			yyy = y;
    		}
        	mouseOffsetX = mX - xx;
        	mouseOffsetY = mY - yy;
        	return true;
    	}
        return false;
    }
}