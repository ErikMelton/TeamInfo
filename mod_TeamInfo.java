package net.minecraft.src;

import org.lwjgl.input.Keyboard;

public class mod_TeamInfo extends BaseMod{

	GuiScreen guiscreen;
	
	public void load()
	{
		
	}
	public mod_TeamInfo()
	{
		ModLoader.registerKey(this, new KeyBinding("Team Info", Keyboard.KEY_EQUALS), false);
		ModLoader.setInGameHook(this, true, true);
	}
	
	public void keyboardEvent(KeyBinding keybinding) {
		
		if(keybinding.keyCode == Keyboard.KEY_EQUALS){
			System.out.println("PRESSED");
	        ModLoader.openGUI(ModLoader.getMinecraftInstance().thePlayer, new GuiTeamInfo(guiscreen));
		}
		
	}

	public String getVersion() {
		return "1.2.5, made by Rainfur";
	}
}
