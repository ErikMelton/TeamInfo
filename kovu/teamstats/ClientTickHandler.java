package kovu.teamstats;

import java.util.EnumSet;

import kovu.teamstats.gui.GuiTeamInfoIngame;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import net.ae97.teamstats.api.TeamstatsAPI;

public class ClientTickHandler implements ITickHandler {

    private int ticksPassed = 0;
    public static String selectedFriend;
    private GuiTeamInfoIngame mainGui;

    public ClientTickHandler() {
        mainGui = new GuiTeamInfoIngame();
    }

    @Override
    public void tickStart(EnumSet<TickType> type, Object... tickData) {
    }

    @Override
    public void tickEnd(EnumSet<TickType> type, Object... tickData) {

        if (type.equals(EnumSet.of(TickType.RENDER))) {
            onRenderTick();
        } else if (type.equals(EnumSet.of(TickType.CLIENT))) {
            GuiScreen guiscreen = Minecraft.getMinecraft().currentScreen;
            if (guiscreen != null) {
                onTickInGUI(guiscreen);
            } else {
                onTickInGame();
            }

        }
    }

    @Override
    public EnumSet<TickType> ticks() {

        return EnumSet.of(TickType.RENDER, TickType.CLIENT);
    }

    @Override
    public String getLabel() {

        return null;
    }

    public void onTickInGUI(GuiScreen guiscreen) {

        Kovu.isInGUI = false;
    }

    public void onTickInGame() {

        Kovu.isInGUI = true;
    }

    public void onRenderTick() {
        ticksPassed++;

        if (Kovu.isInGUI == true) {
            mainGui.drawMain();
        }
    }
}