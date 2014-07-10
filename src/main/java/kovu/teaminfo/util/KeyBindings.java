package kovu.teaminfo.util;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings 
{
	public static KeyBinding mainGui;
	public static KeyBinding ddGui;
	public static KeyBinding configGui;
	
	public void init()
	{
		mainGui = new KeyBinding("Main GUI", Keyboard.KEY_SEMICOLON, "TeamInfo");
		ddGui = new KeyBinding("Drag and Drop GUI", Keyboard.KEY_P, "TeamInfo");
		configGui = new KeyBinding("Config GUI", Keyboard.KEY_LBRACKET, "TeamInfo");

		ClientRegistry.registerKeyBinding(mainGui);
		ClientRegistry.registerKeyBinding(ddGui);
		ClientRegistry.registerKeyBinding(configGui);
	}
}
