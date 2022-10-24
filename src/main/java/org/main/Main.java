package org.main;

import org.main.receive.Receiver;
import org.main.receive.ReceiverThread;
import org.main.send.Sender;
import org.main.send.SenderThread;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        PacketAnalyzer packetAnalyzer = new PacketAnalyzer();
        int value = (int) (Math.random() * 100);
        String uid = String.valueOf(value);
        Sender sender = new Sender();
        Receiver receiver = new Receiver();
        SenderThread senderThread = new SenderThread(uid, sender, packetAnalyzer);
        ReceiverThread receiverThread = new ReceiverThread(receiver, packetAnalyzer);
        senderThread.run();
        receiverThread.run();
        Scanner scanner = new Scanner(System.in);
        String str = scanner.next();
        if (str.equals("q")){
            senderThread.finish();
            receiverThread.finish();
        }
    }
}