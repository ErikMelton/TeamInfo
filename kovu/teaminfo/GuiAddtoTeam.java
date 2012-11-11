package kovu.teaminfo;


import org.lwjgl.input.Keyboard;

import friendmodapi.FriendModApi;

import net.minecraft.src.GuiButton;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.GuiTextField;

public class GuiAddtoTeam extends GuiScreen {

	private GuiScreen parentScreen;
	public GuiTextField serverTextField;
	
	public GuiAddtoTeam(GuiScreen guiscreen)
	{
		super();
		parentScreen = guiscreen;
	}
	
	public void initGui()
	{
		Keyboard.enableRepeatEvents(true);
		controlList.clear();
		controlList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
		controlList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "Back"));
		serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, height / 2 - 25, 200, 20);
		serverTextField.setVisible(true);
		serverTextField.setFocused(true);
		serverTextField.setText("");
		
	}
	
	public void actionPerformed(GuiButton guibutton)
	{
		 if(guibutton.id == 0 && serverTextField.getText() != mc.session.username && serverTextField.getText() != "")
	        {
	            String s = serverTextField.getText();
	            try {            
	            FriendModApi.addFriend(s);
	            }
	            catch (Exception e)
	            {
	            	e.printStackTrace();
	            }
	            mc.thePlayer.addChatMessage((new StringBuilder()).append("A request has been sent to \247c").append(serverTextField.getText()).toString());
	            mc.displayGuiScreen(null);
	        }
	        
	        if(guibutton.id == 1)
	        {
	        	System.out.println(serverTextField.getText());
	            mc.displayGuiScreen(parentScreen);
	        }
	}
	
	public void keyTyped(char c, int i)
	{
		serverTextField.textboxKeyTyped(c, i);
		if (c == '\r')
		{
			actionPerformed((GuiButton)controlList.get(1));
		}
		super.keyTyped(c, i);
	}
	
	public boolean guiDoesPauseGame()
	{
		return false;
	}
	
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}
	
	public void mouseClicked(int i, int j, int k)
	{
		super.mouseClicked(i,  j,  k);
		serverTextField.mouseClicked(i,  j,  k);
		serverTextField.updateCursorCounter();
	}
	
	public void drawScreen(int i, int j, float f)
	{
		drawRect(width/ 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30, 0x60000000);
		drawCenteredString(fontRenderer, "Add Player", width / 2, height / 2 - 43, 0xffffff);
		serverTextField.drawTextBox();
		super.drawScreen(i, j, f);
	}
	
	public void updateScreen()
	{
		if(Keyboard.isKeyDown(15))
		{
			Kovu.showlist = true;
		}
		else
		{
			Kovu.showlist = false;
		}
		serverTextField.updateCursorCounter();
	}
}
