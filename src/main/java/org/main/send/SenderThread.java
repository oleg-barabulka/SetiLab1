package org.main.send;

import org.main.PacketAnalyzer;
import org.main.send.Sender;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class SenderThread{
    private String uid;
    private Timer timer;
    private Sender sender;
    private PacketAnalyzer packetAnalyzer;
    private Integer progCount;
    public SenderThread(String str, Sender sender, PacketAnalyzer packetAnalyzer){
        timer = new Timer();
        uid = str;
        this.sender = sender;
        this.packetAnalyzer = packetAnalyzer;
        progCount = 1;
    }

    public void run(){
        try {
            sender.sendDatagram( "hello " + uid + " " + Arrays.toString(Inet4Address.getLocalHost().getAddress()));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
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
        try {
            sender.sendDatagram("bye " + uid + " " + Arrays.toString(Inet4Address.getLocalHost().getAddress()));
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}
