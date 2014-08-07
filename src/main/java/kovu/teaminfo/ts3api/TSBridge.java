package kovu.teaminfo.ts3api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class TSBridge
{
    private Socket bridge = null;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private InputThread inputThread;
    private OutputThread outputThread;
    private userInputHandler userIn = null;
    private final String SPACE = "\\\\s";
    private int lastAliveCheck = 0;
    private boolean close = false;

    protected void connect() throws IOException
    {
        try
        {
            this.bridge = new Socket("127.0.0.1", 25639);
            this.out = new PrintWriter(this.bridge.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(this.bridge.getInputStream()));
            this.userIn = new userInputHandler();
            this.inputThread = new InputThread(this, this.bridge, this.in);
            this.outputThread = new OutputThread(this, this.bridge, this.out, this.userIn);
            this.inputThread.start();
            this.outputThread.start();
            this.onConnect();
        }
        catch (UnknownHostException var2)
        {
            System.out.println("Error:" + var2);
        }
        catch (IOException var3)
        {
            this.onTeamspeakClientIsNotActive();
        }
    }

    protected void getWhoAmI()
    {
        this.userIn.setMessage("whoami");
    }

    protected void getChannelList()
    {
        this.userIn.setMessage("channellist  ");
    }

    protected void getAllUsers()
    {
        this.userIn.setMessage("clientlist");
    }

    protected void getCurrentChannelName()
    {
        this.userIn.setMessage("channelconnectinfo");
    }

    protected void sendEnablePokeNotication()
    {
        this.userIn.setMessage("clientnotifyregister schandlerid=0 event=notifyclientpoke");
    }

    protected void sendDisablePokeNotication()
    {
        this.userIn.setMessage("clientnotifyunregister schandlerid=0 event=notifyclientpoke");
    }

    protected void sendEnableTextMessageNotication()
    {
        this.userIn.setMessage("clientnotifyregister schandlerid=0 event=notifytextmessage");
    }

    protected void sendDisableTextMessageNotication()
    {
        this.userIn.setMessage("clientnotifyunregister schandlerid=0 event=notifytextmessage");
    }

    protected void sendEnableTalkStatusChangeNotifcation()
    {
        this.userIn.setMessage("clientnotifyregister schandlerid=0 event=notifytalkstatuschange");
    }

    protected void sendDisableTalkStatusChangeNotifcation()
    {
        this.userIn.setMessage("clientnotifyunregister schandlerid=0 event=notifytalkstatuschange");
    }

    protected void sendConsoleMessage(String var1)
    {
        this.userIn.setMessage(var1);
    }

    protected void sendServerMessage(String var1)
    {
        this.userIn.setMessage("sendtextmessage targetmode=3 msg=" + var1.replaceAll(" ", "\\\\s"));
    }

    protected void sendChannelMessage(String var1)
    {
        this.userIn.setMessage("sendtextmessage targetmode=2 msg=" + var1.replaceAll(" ", "\\\\s"));
    }

    protected void sendPrivateMessage(String var1, String var2)
    {
        this.userIn.setMessage("sendtextmessage targetmode=1 target=" + var1 + " msg=" + var2.replaceAll(" ", "\\\\s"));
    }

    protected abstract void onConnect();

    protected abstract void onClose();

    protected abstract void onServerTextMessage(String var1, String var2, String var3);

    protected abstract void onChannelTextMessage(String var1, String var2, String var3);

    protected abstract void onPrivateTextMessage(String var1, String var2, String var3, String var4, String var5);

    protected abstract void onTalkStatusChange(String var1, String var2, String var3);

    protected void onUsersInOwnChannel() {}

    protected void onUsersInOtherChannel() {}

    protected abstract void onChannelList(TS3Channel[] var1);

    protected abstract void onTeamspeakClientIsNotActive();

    protected abstract void onPoke(String var1, String var2, String var3, String var4);

    protected abstract void onAllUsers(TS3User[] var1);

    protected void onUserJoinOwnChannel() {}

    protected abstract void onConsoleMessage(String var1);

    protected String getLastMessage()
    {
        return this.userIn.getLastMessage();
    }

    protected void resetLastCheckTime()
    {
        this.lastAliveCheck = 0;
    }

    protected int getLastCheckTime()
    {
        return this.lastAliveCheck;
    }

    protected void increaseLastCheckTime()
    {
        ++this.lastAliveCheck;
    }

    protected boolean shouldClose()
    {
        return this.close;
    }

    protected void doClose()
    {
        this.close = true;
        this.onClose();
    }
}
