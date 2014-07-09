package kovu.teaminfo.handlers;

import kovu.teaminfo.gui.GuiTeamInfo;
import kovu.teaminfo.util.KeyBindings;
import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

public class KeyInputHandler
{
	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		if(KeyBindings.mainGui.isPressed())
		{
			Minecraft.getMinecraft().displayGuiScreen(new GuiTeamInfo(Minecraft.getMinecraft().currentScreen));
		}
	}
}
