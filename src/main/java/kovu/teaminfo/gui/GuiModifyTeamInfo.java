package kovu.teaminfo.gui;

import kovu.teaminfo.TeamInfo;
import kovu.teaminfo.util.Vars;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiModifyTeamInfo extends GuiScreen
{
	private boolean dragging;
	private int diffu;
	private int diffv;
	private int size;
	
	public GuiModifyTeamInfo()
	{
		dragging = false;
		size = 40;
	}
	
	protected void actionPerformed(GuiButton arg0) 
	{

	}
	
	public void drawScreen(int arg0, int arg1, float arg2) 
	{
		int x = Mouse.getX() / 2;
		int y = (mc.displayHeight - Mouse.getY()) / 2;
		
		for(String player : TeamInfo.players.keySet())
		{
			if((Mouse.isButtonDown(0)) && (!((Boolean)TeamInfo.dragable.get(player)).booleanValue()) && (x >= ((Integer)TeamInfo.toplefti.get(player)).intValue()) && (x <= ((Integer)TeamInfo.toplefti.get(player)).intValue() + 108.0F * Vars.scale) && (y >= ((Integer)TeamInfo.topleftj.get(player)).intValue()) && (y <= ((Integer)TeamInfo.topleftj.get(player)).intValue() + this.size * Vars.scale))
			{
				if(Keyboard.isKeyDown(Keyboard.KEY_O))
				{
					TeamInfo.showgui.put(player, Boolean.valueOf(!((Boolean)TeamInfo.showgui.get(player)).booleanValue()));
				}
				int p = 0;
				for(String players : TeamInfo.players.keySet())
				{
					if(((Boolean)TeamInfo.dragable.get(players)).booleanValue()) p++;
				}
				if(p < 1)
				{
					diffu = (x - ((Integer)TeamInfo.toplefti.get(player)).intValue());
					diffv = (y - ((Integer)TeamInfo.topleftj.get(player)).intValue());
					TeamInfo.dragable.put(player, Boolean.valueOf(true));
				}
			}
			else if((Mouse.isButtonDown(0)) && (((Boolean)TeamInfo.dragable.get(player)).booleanValue()))
			{
				TeamInfo.toplefti.put(player, Integer.valueOf(x - this.diffu));
				TeamInfo.topleftj.put(player, Integer.valueOf(y - this.diffv));
			}
			else
			{
				TeamInfo.dragable.put(player, Boolean.valueOf(false));
			}
		}
		for(String player : TeamInfo.players.keySet())
		{
			GL11.glTranslatef(((Integer)TeamInfo.toplefti.get(player)).intValue(), ((Integer)TeamInfo.topleftj.get(player)).intValue(), 0.0F);
			
			int color = 0x20ff0000;
			if(((Boolean)TeamInfo.showgui.get(player)).booleanValue())
			{
				color = 0x2000ff00;
			}
			else
			{
				drawCenteredString(fontRendererObj, player, (int)(108.0F * Vars.scale) / 2, (int)(20.0F * Vars.scale) / 2, 0xff00ff00);
			}
			drawRect(0, 0, (int)(108.0F * Vars.scale), (int)(20.0F * Vars.scale), color);
			GL11.glTranslatef(-((Integer)TeamInfo.toplefti.get(player)).intValue(), -((Integer)TeamInfo.topleftj.get(player)).intValue(), 0.0F);
		}
		super.drawScreen(arg0, arg1, arg2);
	}
	
	public void initGui() 
	{
		Keyboard.enableRepeatEvents(true);
		if(!Vars.hunger)
		{
			size -= 10;
		}
		if(!Vars.armour)
		{
			size -=10;
		}
		if(!Vars.coords)
		{
			size -= 10;
		}
		if(size < 20)
		{
			size = 20;
		}
	}
	
	public void updateScreen() 
	{
		
	}
}
