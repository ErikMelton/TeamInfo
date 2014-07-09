package kovu.teaminfo.gui;

import kovu.teaminfo.proxies.ClientProxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.opengl.GL11;

public class GuiTeamInfo extends GuiScreen 
{
	private GuiScreen parentScreen;
	//private TestGui test;
	
	public GuiTeamInfo(GuiScreen parentscreen) 
	{
		this.parentScreen = parentscreen;
	}

	public void updateScreen()
	{

	}

	public void drawScreen(int i, int j, float f)
	{
		mc.renderEngine.bindTexture(ClientProxy.kovuHead);
		GL11.glTranslated(width / 2 - 155, height / 2 - 10, 0.0D);
		GL11.glScalef(1.0F, 0.5F, 1.0F);
		GL11.glScalef(0.75F, 0.75F, 1.0F);
		drawTexturedModalRect(0, 0, 32, 64, 32, 64);
		drawTexturedModalRect(0, 0, 160, 64, 32, 64);

		GL11.glScalef(1.333333F, 1.333333F, 1.0F);
		GL11.glScalef(1.0F, 2.0F, 1.0F);
		GL11.glTranslated(-(width / 2 - 155), -height / 2 + 10, 0.0D);

		mc.renderEngine.bindTexture(ClientProxy.kovuHead);
		GL11.glTranslated(width / 2 + 130, height / 2 - 10, 0.0D);
		GL11.glScalef(1.0F, 0.5F, 1.0F);
		GL11.glScalef(0.75F, 0.75F, 1.0F);
		drawTexturedModalRect(0, 0, 32, 64, 32, 64);
		drawTexturedModalRect(0, 0, 160, 64, 32, 64);
		GL11.glScalef(1.333333F, 1.333333F, 1.0F);
		GL11.glScalef(1.0F, 2.0F, 1.0F);
		GL11.glTranslated(-(width / 2 + 130), -height / 2 + 10, 0.0D);

		drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 1610612736);
		drawCenteredString(fontRendererObj, "§cTeam Info by §bKovu", width / 2, height / 2 - 13, 16777215);
		//test.drawElement();
		super.drawScreen(i, j, f);
	}

	public void initGui()
	{
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 102, height / 2 + 5, 60, 20, "Add"));
		buttonList.add(new GuiButton(1, width / 2 - 30, height / 2 + 5, 60, 20, "Remove"));
		buttonList.add(new GuiButton(2, width / 2 + 42, height / 2 + 5, 60, 20, "Config"));
		//test = new TestGui(100, 100, 200, 200);
		System.out.println("[TeamInfo]: GuiTeamInfo Initialized.");
	}

	public void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 0) 
		{
			mc.displayGuiScreen(new GuiAddtoTeam(this));
		}
		if (guibutton.id == 1)
		{
			mc.displayGuiScreen(new GuiRemoveFromTeam(this));
		}
		if (guibutton.id == 2)
		{
			mc.displayGuiScreen(new GuiTeamInfoConfig(this));
		}
	}

	/*
	public void handleMouseInput()
	{
		//test.mouseDragged(Minecraft.getMinecraft(), Mouse.getX(), Mouse.getY());
	}
	*/
}