package kovu.teaminfo.teamspeak;

import java.net.URI;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kovu.teaminfo.TeamInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ChatComponentText;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class MSgc extends GuiScreen
{
    private String field_73898_b = "";
    private int sentHistoryCursor = -1;
    private boolean field_73897_d = false;
    private boolean field_73905_m = false;
    private int field_73903_n = 0;
    private List field_73904_o = new ArrayList();
    private long openTime = 0L;
    private boolean isServerGuiHeld = false;
    private boolean isChatGuiHeld = false;
    private boolean isChatFocused = false;
    private int xgrab;
    private int ygrab;
    private int flasherCount = 0;
    private long lastClick = 0L;
    private String lastChannelClick = "-1";
    private URI clickedURI = null;
    protected GuiTextField inputField;
    private String defaultInputFieldText = "";
    private TeamInfo mod;
    private MSguiServer serverGui;
    private MSguiChat chatGui;

    public MSgc(TeamInfo var1, MSguiServer var2, MSguiChat var3)
    {
        this.mod = var1;
        this.serverGui = var2;
        this.chatGui = var3;
        this.openTime = System.currentTimeMillis();
    }

    public MSgc(String var1)
    {
        this.defaultInputFieldText = var1;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
        this.inputField = new GuiTextField(fontRendererObj, 4, this.height - 12, this.width - 4, 12);
        this.inputField.setMaxStringLength(100);
        this.inputField.setEnableBackgroundDrawing(false);
        this.inputField.setFocused(true);
        this.inputField.setText(this.defaultInputFieldText);
        this.inputField.setCanLoseFocus(false);
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.ingameGUI.getChatGUI().resetScroll();
        this.isServerGuiHeld = false;
        this.xgrab = 0;
        this.ygrab = 0;

        if (this.serverGui.channelPop != null && this.serverGui.channelPop.isVisible())
        {
            this.serverGui.channelPop.destroy();
        }
        else if (this.serverGui.userPop != null && this.serverGui.userPop.isVisible())
        {
            this.serverGui.userPop.destroy();
        }

        this.chatGui.userChat = "";
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.inputField.updateCursorCounter();

        if (this.isChatFocused)
        {
            if (this.flasherCount < 10)
            {
                ++this.flasherCount;
                this.chatGui.userChat = this.inputField.getText() + "|";
            }
            else if (this.flasherCount < 20)
            {
                ++this.flasherCount;
                this.chatGui.userChat = this.inputField.getText();
            }
            else
            {
                this.flasherCount = 0;
                this.chatGui.userChat = this.inputField.getText();
            }
        }
        else
        {
            this.chatGui.userChat = this.inputField.getText();
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char var1, int var2)
    {
        this.field_73905_m = false;

        if (this.isChatFocused)
        {
            if (var2 == 15)
            {
                this.completePlayerName();
            }
            else
            {
                this.field_73897_d = false;
            }
        }

        if (var2 == 1)
        {
            this.mc.displayGuiScreen((GuiScreen)null);
        }
        else if (this.isChatFocused)
        {
            if (var2 == 28)
            {
                String var3 = this.inputField.getText().trim();

                if (var3.length() > 0)
                {
                    int var4 = this.chatGui.selectedChat;

                    if (var4 == 0)
                    {
                        this.mod.sendTSMessage(var3);
                    }
                    else if (var4 == 1)
                    {
                        this.mod.sendTSMessage("cmsg " + var3);
                    }
                    else if (var4 > 1)
                    {
                        this.mod.sendTSMessage("pmsg " + ((MSguiConversation)this.chatGui.conversations.get(var4 - 2)).getClid() + " " + var3);
                    }

                    this.mc.ingameGUI.getChatGUI().addToSentMessages(var3);
                }

                this.chatGui.userChat = " ";
                this.inputField.setText(this.defaultInputFieldText);
            }
            else if (var2 == 200)
            {
                this.getSentHistory(-1);
            }
            else if (var2 == 208)
            {
                this.getSentHistory(1);
            }
            else if (var2 == 201)
            {
                this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().func_146232_i() /*.func_96127_i()*/ - 1);
            }
            else if (var2 == 209)
            {
                this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().func_146232_i() /*.func_96127_i()*/ + 1);
            }
            else if (this.openTime + 500L <= System.currentTimeMillis() || var2 != 22)
            {
                this.inputField.textboxKeyTyped(var1, var2);
            }
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput()
    {
        super.handleMouseInput();
        int var1 = Mouse.getEventDWheel();
        ScaledResolution var2;

        if (this.isServerGuiHeld)
        {
        	var2 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            this.mod.ingame.serverGui.setxpos(Mouse.getX() * var2.getScaledWidth() / this.mc.displayWidth - this.xgrab);
            this.mod.ingame.serverGui.setypos(var2.getScaledHeight() - Mouse.getY() * var2.getScaledHeight() / this.mc.displayHeight - this.ygrab);
        }
        else if (this.isChatGuiHeld)
        {
        	var2 = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            this.mod.ingame.chatGui.setxpos(Mouse.getX() * var2.getScaledWidth() / this.mc.displayWidth - this.xgrab);
            this.mod.ingame.chatGui.setypos(var2.getScaledHeight() - Mouse.getY() * var2.getScaledHeight() / this.mc.displayHeight - this.ygrab);
        }
        else if (this.serverGui.isFocused())
        {
            if (var1 != 0)
            {
                if (this.serverGui.channelPop != null && this.serverGui.channelPop.isVisible())
                {
                    this.serverGui.channelPop.destroy();
                }
                else if (this.serverGui.userPop != null && this.serverGui.userPop.isVisible())
                {
                    this.serverGui.userPop.destroy();
                }

                if (var1 > 0)
                {
                    this.serverGui.scrollUp();
                }
                else if (var1 < 0)
                {
                    this.serverGui.scrollDown();
                }
            }
        }
        else if (this.chatGui.isFocused() && var1 != 0)
        {
            if (var1 > 0)
            {
                this.chatGui.scrollUp();
            }
            else if (var1 < 0)
            {
                this.chatGui.scrollDown();
            }
        }
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int var1, int var2, int var3)
    {
        int var4;

        if (!this.isChatGuiHeld)
        {
            if (this.serverGui.channelPop != null && this.serverGui.channelPop.isVisible())
            {
                if (var3 == 0 && var1 > this.serverGui.channelPop.getxPos() && var1 < this.serverGui.channelPop.getxPos() + this.serverGui.channelPop.getWidth() && var2 > this.serverGui.channelPop.getyPos() && var2 < this.serverGui.channelPop.getyPos() + this.serverGui.channelPop.getHeight())
                {
                    var4 = this.serverGui.channelPop.getOptionPressed(var2);

                    if (var4 == 0)
                    {
                        this.mod.ingame.bridge.handleInput("clientmove cid=" + this.serverGui.channelPop.getChannel().getCid() + " clid=0");
                    }

                    this.serverGui.channelPop.destroy();
                    return;
                }

                this.serverGui.channelPop.destroy();
            }

            if (this.serverGui.userPop != null && this.serverGui.userPop.isVisible())
            {
                if (var3 == 0 && var1 > this.serverGui.userPop.getxPos() && var1 < this.serverGui.userPop.getxPos() + this.serverGui.userPop.getWidth() && var2 > this.serverGui.userPop.getyPos() && var2 < this.serverGui.userPop.getyPos() + this.serverGui.userPop.getHeight())
                {
                    var4 = this.serverGui.userPop.getOptionPressed(var2);

                    if (var4 == 0)
                    {
                        this.chatGui.openNewPrivateChat(this.serverGui.userPop.getUser().getClid());
                    }
                    else if (var4 != 1)
                    {
                        if (var4 == 2)
                        {
                            this.mod.ingame.bridge.handleInput("clientkick reasonid=4 reasonmsg=Minespeak-Kick clid=" + this.serverGui.userPop.getUser().getClid());
                        }
                        else if (var4 == 3)
                        {
                            this.mod.ingame.bridge.handleInput("clientkick reasonid=5 reasonmsg=Minespeak-Kick clid=" + this.serverGui.userPop.getUser().getClid());
                        }
                        else if (var4 == 4)
                        {
                            ;
                        }
                    }

                    this.serverGui.userPop.destroy();
                    return;
                }

                this.serverGui.userPop.destroy();
            }

            if (var1 > this.serverGui.getxpos() - 2 && var1 < this.serverGui.getxpos() + 202 && var2 > this.serverGui.getypos() - 2 && var2 < this.serverGui.getypos() + 365 && this.serverGui.isVisible() || !this.serverGui.isVisible() && var1 >= this.serverGui.getxpos() && var1 <= this.serverGui.getxpos() + 200 && var2 >= this.serverGui.getypos() && var2 <= this.serverGui.getypos() + 10)
            {
                this.serverGui.setFocused();

                if (!this.isServerGuiHeld && var1 >= this.serverGui.getxpos() && var1 <= this.serverGui.getxpos() + 185 && var2 >= this.serverGui.getypos() && var2 <= this.serverGui.getypos() + 10)
                {
                    this.isServerGuiHeld = true;
                    this.xgrab = var1 - this.mod.ingame.serverGui.getxpos();
                    this.ygrab = var2 - this.mod.ingame.serverGui.getypos();
                }
                else if (var1 >= this.serverGui.getxpos() + 186 && var1 <= this.serverGui.getxpos() + 197 && var2 >= this.serverGui.getypos() && var2 <= this.serverGui.getypos() + 10)
                {
                    this.serverGui.toggleVisibility();
                }
                else if (var1 >= this.serverGui.getxpos() + 10 && var1 <= this.serverGui.getxpos() + 190 && var2 >= this.serverGui.getypos() + 30 && var2 <= this.serverGui.getypos() + 325 && this.serverGui.isVisible())
                {
                    for (var4 = 0; var4 < 30; ++var4)
                    {
                        if (var1 >= this.serverGui.getxpos() + 10 && var1 <= this.serverGui.getxpos() + 190 && var2 >= this.serverGui.getypos() + 30 + 10 * var4 && var2 <= this.serverGui.getypos() + 40 + 10 * var4)
                        {
                            if (this.serverGui.serverList.size() > var4 + this.serverGui.getScroll())
                            {
                                Object var5 = this.serverGui.serverList.get(var4 + this.serverGui.getScroll());

                                if (var5 instanceof MSguiListChannel)
                                {
                                    MSguiListChannel var6 = (MSguiListChannel)var5;

                                    if (var3 == 0)
                                    {
                                        if (this.lastClick + 5250L > System.currentTimeMillis() && this.lastClick > System.currentTimeMillis() - 250L && this.lastChannelClick.equals(var6.getChannel().getCid()))
                                        {
                                            this.mod.ingame.bridge.handleInput("clientmove cid=" + var6.getChannel().getCid() + " clid=0");
                                        }
                                        else
                                        {
                                            this.lastClick = System.currentTimeMillis();
                                            this.lastChannelClick = var6.getChannel().getCid();
                                        }
                                    }
                                    else if (var3 == 1)
                                    {
                                        this.serverGui.channelPop = new MSguiPopChannel(var1, var2, var6.getChannel(), fontRendererObj);
                                    }
                                }
                                else if (var5 instanceof MSguiListUser)
                                {
                                    MSguiListUser var7 = (MSguiListUser)var5;

                                    if (var3 != 0 && var3 == 1)
                                    {
                                        this.serverGui.userPop = new MSguiPopUser(var1, var2, var7.getUser(), fontRendererObj);
                                    }
                                }
                            }

                            break;
                        }
                    }
                }
                else
                {
                    this.isServerGuiHeld = false;
                }

                return;
            }

            this.serverGui.setUnfocused();
        }

        if (!this.isServerGuiHeld)
        {
            this.isChatFocused = false;

            if (var1 > this.chatGui.getxpos() - 2 && var1 < this.chatGui.getxpos() + 350 && var2 > this.chatGui.getypos() - 2 && var2 < this.chatGui.getypos() + 150)
            {
                this.chatGui.setFocused();

                if (!this.isChatGuiHeld && var1 >= this.chatGui.getxpos() && var1 <= this.chatGui.getxpos() + 337 && var2 >= this.chatGui.getypos() && var2 <= this.chatGui.getypos() + 10)
                {
                    this.isChatGuiHeld = true;
                    this.xgrab = var1 - this.mod.ingame.chatGui.getxpos();
                    this.ygrab = var2 - this.mod.ingame.chatGui.getypos();
                    return;
                }

                if (var1 >= this.chatGui.getxpos() + 338 && var1 <= this.chatGui.getxpos() + 349 && var2 >= this.chatGui.getypos() + 3 && var2 <= this.chatGui.getypos() + 9)
                {
                    this.chatGui.toggleVisibility();
                }
                else if (this.chatGui.isVisible())
                {
                    if (!this.isChatFocused && var1 >= this.chatGui.getxpos() && var1 <= this.chatGui.getxpos() + 315 && var2 >= this.chatGui.getypos() + 135 && var2 <= this.chatGui.getypos() + 160)
                    {
                        this.isChatFocused = true;
                    }
                    else if (var2 >= this.chatGui.getypos() + 15 && var2 <= this.chatGui.getypos() + 25)
                    {
                        if (var1 >= this.chatGui.getxpos() + 4 && var1 <= this.chatGui.getxpos() + 15)
                        {
                            this.chatGui.selectedChat = 0;
                        }
                        else if (var1 >= this.chatGui.getxpos() + 17 && var1 <= this.chatGui.getxpos() + 60)
                        {
                            this.chatGui.selectedChat = 1;
                        }
                        else if (var1 >= this.chatGui.getxpos() + 64 && var1 <= this.chatGui.getxpos() + 74)
                        {
                            this.chatGui.scrollLeft();
                        }
                        else if (var1 >= this.chatGui.getxpos() + 335 && var1 <= this.chatGui.getxpos() + 345)
                        {
                            this.chatGui.scrollRight();
                        }
                        else
                        {
                            for (var4 = 0; var4 < this.chatGui.conversations.size() && var4 < 4; ++var4)
                            {
                                if (var1 >= this.chatGui.getxpos() + 77 + var4 * 64 && var1 <= this.chatGui.getxpos() + 140 + var4 * 64)
                                {
                                    this.chatGui.selectedChat = var4 + this.chatGui.sideScroll + 2;
                                    break;
                                }
                            }
                        }
                    }
                    else if (var1 >= this.chatGui.getxpos() + 316 && var1 <= this.chatGui.getxpos() + 345 && var2 >= this.chatGui.getypos() + 135 && var2 <= this.chatGui.getypos() + 160 && this.chatGui.conversations.size() > 0)
                    {
                        var4 = this.chatGui.selectedChat - 2;

                        if (this.chatGui.conversations.size() == 1)
                        {
                            this.chatGui.selectedChat = 1;
                        }

                        this.chatGui.conversations.remove(var4);
                    }
                }
            }
            else
            {
                this.chatGui.setUnfocused();
                this.isChatFocused = false;
            }
        }

        this.isChatGuiHeld = false;
    }

    public void confirmClicked(boolean var1, int var2) {}

    public void completePlayerName()
    {
        String var1;

        if (this.field_73897_d)
        {
            this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());

            if (this.field_73903_n >= this.field_73904_o.size())
            {
                this.field_73903_n = 0;
            }
        }
        else
        {
            int var2 = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
            this.field_73904_o.clear();
            this.field_73903_n = 0;
            String var3 = this.inputField.getText().substring(var2).toLowerCase();
            var1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
            //this.func_73893_a(var1, var3);

            if (this.field_73904_o.isEmpty())
            {
                return;
            }

            this.field_73897_d = true;
            this.inputField.deleteFromCursor(var2 - this.inputField.getCursorPosition());
        }

        if (this.field_73904_o.size() > 1)
        {
            StringBuilder var4 = new StringBuilder();

            for (Iterator var5 = this.field_73904_o.iterator(); var5.hasNext(); var4.append(var1))
            {
                var1 = (String)var5.next();

                if (var4.length() > 0)
                {
                    var4.append(", ");
                }
            }

            this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new ChatComponentText(var4.toString()), 1);            
        }

        this.inputField.writeText((String)this.field_73904_o.get(this.field_73903_n++));
    }

    public void getSentHistory(int var1)
    {
        int var2 = this.sentHistoryCursor + var1;
        int var3 = this.mc.ingameGUI.getChatGUI().getSentMessages().size();

        if (var2 < 0)
        {
            var2 = 0;
        }

        if (var2 > var3)
        {
            var2 = var3;
        }

        if (var2 != this.sentHistoryCursor)
        {
            if (var2 == var3)
            {
                this.sentHistoryCursor = var3;
                this.inputField.setText(this.field_73898_b);
            }
            else
            {
                if (this.sentHistoryCursor == var3)
                {
                    this.field_73898_b = this.inputField.getText();
                }

                this.inputField.setText((String)this.mc.ingameGUI.getChatGUI().getSentMessages().get(var2));
                this.sentHistoryCursor = var2;
            }
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int var1, int var2, float var3)
    {
        super.drawScreen(var1, var2, var3);
    }

    public void func_73894_a(String[] var1)
    {
        if (this.field_73905_m)
        {
            this.field_73904_o.clear();
            String[] var2 = var1;
            int var3 = var1.length;

            for (int var4 = 0; var4 < var3; ++var4)
            {
                String var5 = var2[var4];

                if (var5.length() > 0)
                {
                    this.field_73904_o.add(var5);
                }
            }

            if (this.field_73904_o.size() > 0)
            {
                this.field_73897_d = true;
                this.completePlayerName();
            }
        }
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame()
    {
        return false;
    }
}
