package kovu.teaminfo.ts3api;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class InputThread extends Thread
{
    TSBridge tsBridge;
    private Socket bridge;
    private BufferedReader in;
    private boolean isConnected = true;

    public InputThread(TSBridge var1, Socket var2, BufferedReader var3)
    {
        this.tsBridge = var1;
        this.bridge = var2;
        this.in = var3;
    }

    public void run()
    {
        String var1 = "";

        while (!this.tsBridge.shouldClose())
        {
            try
            {
                if (this.in.ready())
                {
                    var1 = this.in.readLine();

                    if (var1.equals("TS3 Client"))
                    {
                        this.tsBridge.onConnect();
                    }
                    else
                    {
                        String[] var2;

                        if (var1.startsWith("notifytextmessage"))
                        {
                            var2 = var1.split(" ");

                            if (var2[2].endsWith("1"))
                            {
                                this.tsBridge.onPrivateTextMessage(var2[1].substring(12), var2[3].substring(4), var2[6].substring(12), var2[4].substring(7), var2[5].substring(10));
                            }
                            else if (var2[2].endsWith("2"))
                            {
                                this.tsBridge.onChannelTextMessage(var2[1].substring(12), var2[3].substring(4), var2[5].substring(12));
                            }
                            else if (var2[2].endsWith("3"))
                            {
                                this.tsBridge.onServerTextMessage(var2[1].substring(12), var2[3].substring(4), var2[5].substring(12));
                            }
                        }
                        else if (var1.startsWith("notifytalkstatuschange"))
                        {
                            var2 = var1.split(" ");
                            this.tsBridge.onTalkStatusChange(var2[1].substring(12), var2[2].substring(7), var2[4].substring(5));
                        }
                        else if (var1.startsWith("notifyclientpoke"))
                        {
                            var2 = var1.split(" ");
                            this.tsBridge.onPoke(var2[1].substring(12), var2[2].substring(10), var2[3].substring(12), var2[5].substring(4));
                        }
                        else
                        {
                            int var4;
                            String[] var5;

                            if (var1.startsWith("clid="))
                            {
                                var2 = var1.split("\\|");
                                TS3User[] var9 = new TS3User[var2.length];

                                for (var4 = 0; var4 < var2.length; ++var4)
                                {
                                    var5 = var2[var4].split(" ");
                                    var9[var4] = new TS3User(var5[0].substring(5), var5[1].substring(4), var5[2].substring(19), var5[3].substring(16));
                                }

                                this.tsBridge.onAllUsers(var9);
                            }
                            else if (!var1.startsWith("cid="))
                            {
                                this.tsBridge.onConsoleMessage(var1);
                            }
                            else
                            {
                                var2 = var1.split("\\|");
                                TS3Channel[] var3 = new TS3Channel[var2.length];

                                for (var4 = 0; var4 < var2.length; ++var4)
                                {
                                    var5 = var2[var4].split(" ");
                                    var3[var4] = new TS3Channel(var5[0].substring(4), var5[1].substring(4), var5[2].substring(14), var5[3].substring(13), var5[4].substring(28));
                                }

                                this.tsBridge.onChannelList(var3);
                            }
                        }
                    }

                    this.tsBridge.resetLastCheckTime();
                }
            }
            catch (IOException var8)
            {
                System.out.println("2Error:" + var8);
            }

            try
            {
                Thread.sleep(10L);
            }
            catch (InterruptedException var7)
            {
                ;
            }
        }

        try
        {
            this.in.close();
            this.bridge.close();
        }
        catch (IOException var6)
        {
            ;
        }
    }
}
