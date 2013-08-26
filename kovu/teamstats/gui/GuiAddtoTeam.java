package kovu.teamstats.gui;

import kovu.teamstats.Kovu;
import net.ae97.teamstats.api.TeamstatsAPI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class GuiAddtoTeam extends GuiScreen {

    private GuiScreen parentScreen;
    public GuiTextField serverTextField;

    public GuiAddtoTeam(GuiScreen guiscreen) {
        super();
        parentScreen = guiscreen;
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.clear();
        buttonList.add(new GuiButton(0, width / 2 - 66, height / 2 + 5, 60, 20, "Add"));
        buttonList.add(new GuiButton(1, width / 2 + 6, height / 2 + 5, 60, 20, "Back"));
        serverTextField = new GuiTextField(fontRenderer, width / 2 - 100, height / 2 - 25, 200, 20);
        serverTextField.setVisible(true);
        serverTextField.setFocused(true);
        serverTextField.setText("");
    }

    @Override
    public void actionPerformed(GuiButton guibutton) {
        if (guibutton.id == 0 && serverTextField.getText() != mc.func_110432_I().func_111285_a() && !serverTextField.getText().isEmpty()) {
            String s = serverTextField.getText();
            try {
                TeamstatsAPI.getAPI().addFriend(s);
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
            Kovu.friends.add(s);
            mc.thePlayer.addChatMessage(new StringBuilder().append("A request has been sent to \247c").append(serverTextField.getText()).toString());
            mc.displayGuiScreen(null);
        }

        if (guibutton.id == 1) {
            System.out.println(serverTextField.getText());
            mc.displayGuiScreen(parentScreen);
        }
    }

    @Override
    public void keyTyped(char c, int i) {
        serverTextField.textboxKeyTyped(c, i);
        if (c == '\r') {
            actionPerformed((GuiButton) buttonList.get(1));
        }
        super.keyTyped(c, i);
    }

    public boolean guiDoesPauseGame() {
        return false;
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void mouseClicked(int i, int j, int k) {
        super.mouseClicked(i, j, k);
        serverTextField.mouseClicked(i, j, k);
        serverTextField.updateCursorCounter();
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        drawRect(width / 2 - 120, height / 2 - 50, width / 2 + 120, height / 2 + 30, 0x60000000);
        drawCenteredString(fontRenderer, "Add Player", width / 2, height / 2 - 43, 0xffffff);
        serverTextField.drawTextBox();
        super.drawScreen(i, j, f);
    }

    @Override
    public void updateScreen() {
        if (Keyboard.isKeyDown(15)) {
            Kovu.showlist = true;
        } else {
            Kovu.showlist = false;
        }
        serverTextField.updateCursorCounter();
    }
}
