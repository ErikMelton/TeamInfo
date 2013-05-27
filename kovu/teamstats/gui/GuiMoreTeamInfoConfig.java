package kovu.teamstats.gui;

import kovu.teamstats.Kovu;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiMoreTeamInfoConfig extends GuiScreen {

    private GuiScreen parentScreen;

    public GuiMoreTeamInfoConfig(GuiScreen guiscreen) {
        parentScreen = guiscreen;
    }

    public void updateScreen() {
    }

    public void drawScreen(int i, int j, float f) {
        drawRect(width / 2 - 165, height / 2 - 20, width / 2 + 165, height / 2 + 60, 0x60000000);
        drawCenteredString(fontRenderer, "\247cMore Config:", width / 2, height / 2 - 13, 0xffffff);
        super.drawScreen(i, j, f);
    }

    public void initGui() {
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 155, height / 2 + 5, 100, 20, (new StringBuilder()).append("Scale ").append((int) (Kovu.scale * 100F)).append("%").toString()));
        buttonList.add(new GuiButton(1, width / 2 - 50, height / 2 + 5, 100, 20, (new StringBuilder()).append("Version: ").append(Kovu.isVersion ? "On" : "Off").toString()));
        buttonList.add(new GuiButton(6, width / 2 + 55, height / 2 + 5 + 24, 100, 20, "Back"));
        //TODO: Add RGB sliders for the color of clickdrag
    }

    public void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0) {
            if (Kovu.scale != 1.5F) {
                Kovu.scale = (float) ((double) Kovu.scale + 0.25D);
            } else {
                Kovu.scale = 0.5F;
            }
            guibutton.displayString = (new StringBuilder()).append("Scale ").append((int) (Kovu.scale * 100F)).append("%").toString();
        }
        if (guibutton.id == 1) {
            Kovu.isVersion = !Kovu.isVersion;
            guibutton.displayString = (new StringBuilder()).append("Version: ").append(Kovu.isVersion ? "On" : "Off").toString();
        }
        if(guibutton.id == 6)
        {
        	mc.displayGuiScreen(parentScreen);
        }
    }
}