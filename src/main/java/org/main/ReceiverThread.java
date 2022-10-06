package org.main;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

public class ReceiverThread implements Runnable {
    private Timer timer;
    private Receiver receiver;
    private PacketAnalyzer packetAnalyzer;
    public ReceiverThread(Receiver r, PacketAnalyzer pa){
        timer = new Timer();
        receiver = r;
        packetAnalyzer = pa;
    }

    public void run(){
        TimerTask timerTask = new TimerTask() {
            int i = 0;
            public void run() {
                receiver.receive(packetAnalyzer);
                i++;
                if (i == 5){
                    packetAnalyzer.checkAliveProgCount();
                    i = 0;
                }
            }
        };
        timer.schedule(timerTask, 3000, 200);

    }
    public void finish(){
        timer.cancel();
        try {
            sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        receiver.finishReceiving();
    }
}
