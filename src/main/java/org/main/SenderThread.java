package org.main;

import java.util.Timer;
import java.util.TimerTask;

public class SenderThread implements Runnable{
    private String uid;
    private Timer timer;
    private Sender sender;
    private PacketAnalyzer packetAnalyzer;
    private Integer progCount;
    public SenderThread(String str, Sender s, PacketAnalyzer pa){
        timer = new Timer();
        uid = str;
        sender = s;
        packetAnalyzer = pa;
        progCount = 1;
    }

    public void run(){
        sender.sendDatagram( "hello " + uid);
        TimerTask timerTask = new TimerTask() {
            public void run() {
                sender.sendDatagram( "alive " + uid + " " + progCount);
                progCount = packetAnalyzer.getProgCount();
            }
        };
        timer.schedule(timerTask, 3000, 2000);

    }

    public void finish(){
        timer.cancel();
        sender.sendDatagram("bye " + uid);
    }


}
