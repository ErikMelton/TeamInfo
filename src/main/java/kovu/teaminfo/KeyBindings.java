package kovu.teaminfo;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.minecraft.client.settings.KeyBinding;

public class KeyBindings 
{
	public static KeyBinding mainGui;
	
	public static void init()
	{
		mainGui = new KeyBinding("key.teaminfo", Keyboard.KEY_SEMICOLON, "key.categories.teaminfo");

		ClientRegistry.registerKeyBinding(mainGui);
	}
}
