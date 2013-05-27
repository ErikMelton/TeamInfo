package kovu.teamstats;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import kovu.teamstats.api.*;

public class GuiAddReject extends GuiScreen {

    private GuiScreen parentScreen;
    private GuiTextField serverTextField;
    private String playername;
    private String fullMessage;

    public GuiAddReject(GuiScreen guiscreen, String s, String s1) {
        parentScreen = guiscreen;
        playername = s;
        fullMessage = s1;
    }

    public void updateScreen() {
    }

    public void drawScreen(int i, int j, float f) {
        drawRect(width / 2 - 120, height / 2 - 20, width / 2 + 120, height / 2 + 30, 0x60000000);
        drawCenteredString(fontRenderer, (new StringBuilder((new StringBuilder()).append("\247c").append(playername).append("\247f").toString())).append(" wants to share team info with you").toString(), width / 2, height / 2 - 13, 0xffffff);
        super.drawScreen(i, j, f);
    }

    public void initGui() {
    	buttonList.clear();
    	buttonList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
    	buttonList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "reject"));
    }

    public void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            try {
                TeamStatsAPI.addFriend(playername);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TeamInfo.interpretUpdate(fullMessage);
            mc.displayGuiScreen(parentScreen);
        }
        if (guibutton.id == 1) {
            //FriendModAPI.rejectadd(playername);
            mc.displayGuiScreen(parentScreen);
        }
    }
}
