package kovu.teaminfo;

import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class GuiAddtoTeam extends GuiScreen 
{
	private GuiScreen parentScreen;
	private GuiTextField serverTextField;

	public GuiAddtoTeam(GuiScreen parentscreen) 
	{
		this.parentScreen = parentscreen;
	}

	public void updateScreen()
	{
		if (Keyboard.isKeyDown(15)) 
		{
			Util.showlist = true;
		}
		else
		{
			Util.showlist = false;
		}

		serverTextField.updateCursorCounter();
	}

	public void drawScreen(int i, int j, float f)
	{
		drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30, 1610612736);

		drawCenteredString(fontRendererObj, "Add Player", width / 2, height / 2 - 43, 16777215);

		serverTextField.drawTextBox();
		super.drawScreen(i, j, f);
	}

	public void initGui() 
	{
		Keyboard.enableRepeatEvents(true);
		buttonList.clear();
		buttonList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
		buttonList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "Back"));
		serverTextField = new GuiTextField(fontRendererObj, width / 2 - 100, height / 2 - 25, 200, 20);
		serverTextField.setFocused(true);
		serverTextField.setText("");
		serverTextField.setVisible(true);
	}

	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	public void actionPerformed(GuiButton guibutton) 
	{
		if (guibutton.id == 0)
		{
			if ((serverTextField.getText() != mc.getSession().getUsername()) && (serverTextField.getText() != "") && (!TeamInfo.players.containsKey(this.serverTextField.getText()))) 
			{
				TeamInfo.requestadd(this.serverTextField.getText());
				mc.thePlayer.sendChatMessage("A request has been sent to " + this.serverTextField.getText());

				serverTextField.setText("");
				mc.displayGuiScreen(null);
			}
		}

		if (guibutton.id == 1) 
		{
			mc.displayGuiScreen(this.parentScreen);
		}
	}

	public void keyTyped(char c, int i) 
	{
		serverTextField.textboxKeyTyped(c, i);

		super.keyTyped(c, i);
	}

	public void mouseCicked(int i, int j, int k)
	{
		super.mouseClicked(i, j, k);
		serverTextField.mouseClicked(i, j, k);
		serverTextField.updateCursorCounter();
	}
}