package kovu.teamstats.gui;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import kovu.teamstats.Kovu;
import net.ae97.teamstats.api.TeamstatsAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

import org.lwjgl.input.Keyboard;

public class GuiRemoveFromTeam extends GuiScreen {

    private GuiScreen parentScreen;
    int players;
    int extra;
    int i;
    String[] friends;
    List list;
    public GuiAddtoTeam ga;

    public GuiRemoveFromTeam(GuiScreen guiscreen) {
        players = 0;
        extra = 0;
        parentScreen = guiscreen;
        ga = new GuiAddtoTeam(parentScreen);
    }

    @Override
    public void updateScreen() {
        if (Keyboard.isKeyDown(15)) {
            Kovu.showlist = true;
        } else {
            Kovu.showlist = false;
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        i = TeamstatsAPI.getAPI().getFriends().size();

        byte byte0 = 0;
        if ((i % 2 != 0) && (i != 0)) {
            byte0 = -1;
        }

        drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30 + 23 * (players / 2 + byte0), 0x6000000);
        drawCenteredString(fontRenderer, "Remove Player", width / 2, height / 2 - 43, 0xffffff);
        if (i == 0) {
            drawCenteredString(fontRenderer, "No players in list", width / 2, height / 2 - 18, 0xffffff);
        }

        super.drawScreen(i, j, f);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        players = 0;
        friends = new String[0];
        try {
            //friends = FriendModApi.getFriends().clone();
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        System.out.println(friends);
        for (String s : Kovu.friends) {
            if (players % 2 == 0) {
                buttonList.add(new GuiButton(players, width / 2 - 90, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            } else {
                buttonList.add(new GuiButton(players, width / 2 + 10, (height / 2 - 20) + 23 * (players / 2), 80, 20, s));
            }
            players++;
        }

        if (players % 2 != 0 || players == 0) {
            extra = 1;
        }
        buttonList.add(new GuiButton(players, width / 2 - 30, (height / 2 - 20) + 23 * (players / 2 + extra), 60, 20, "Back"));
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == players) {
            mc.displayGuiScreen(parentScreen);
        }

        List l = Arrays.asList(friends);

        for (Iterator iterator = l.iterator(); iterator.hasNext();) {
            String s1 = (String) iterator.next();
            if (guibutton.id == i) {
                System.out.println("Inside of" + i);
                try
                {
                    TeamstatsAPI.getAPI().removeFriend(s1);
                }
                catch(IOException e)
                {
                	e.printStackTrace();
                }
                buttonList.remove(i);
                mc.displayGuiScreen(new GuiRemoveFromTeam(new GuiTeamInfo(null)));
                return;
            }
            i++;
        }
    }

    @Override
    public void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
    }
}
