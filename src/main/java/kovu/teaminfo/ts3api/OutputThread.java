package kovu.teaminfo.ts3api;

import java.io.PrintWriter;
import java.net.Socket;

public class OutputThread extends Thread
{
    private TSBridge tsBridge;
    private Socket bridge;
    private PrintWriter out;
    private userInputHandler userIn;
    private int rotation = 0;
    private boolean flip = false;

    public OutputThread(TSBridge var1, Socket var2, PrintWriter var3, userInputHandler var4)
    {
        this.tsBridge = var1;
        this.bridge = var2;
        this.out = var3;
        this.userIn = var4;
    }

    public void run()
    {
        String var1 = "";

        try
        {
            Thread.sleep(5L);
            this.tsBridge.sendEnableTextMessageNotication();

            for (; !this.tsBridge.shouldClose(); Thread.sleep(10L))
            {
                if (this.userIn.isReady())
                {
                    var1 = this.userIn.getMessage();

                    if (var1.equals("quit"))
                    {
                        this.tsBridge.doClose();
                        break;
                    }

                    this.out.println(var1);
                }
                else if (this.tsBridge.getLastCheckTime() == 500)
                {
                    this.out.println("isAlive");
                    this.tsBridge.increaseLastCheckTime();
                }
                else if (this.tsBridge.getLastCheckTime() == 750)
                {
                    this.tsBridge.doClose();
                }
                else
                {
                    this.tsBridge.increaseLastCheckTime();
                }

                if (this.rotation == 1)
                {
                    this.tsBridge.getAllUsers();
                    this.tsBridge.getChannelList();
                    ++this.rotation;
                }
                else if (this.rotation < 250)
                {
                    ++this.rotation;
                }
                else
                {
                    this.rotation = 0;
                }
            }

            this.bridge.close();
            this.out.close();
        }
        catch (Exception var3)
        {
            System.out.println("1Error:" + var3);
        }
    }
}
