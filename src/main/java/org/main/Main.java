package org.main;

import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        PacketAnalyzer pa = new PacketAnalyzer();
        int v = (int) (Math.random() * 100);
        String uid = String.valueOf(v);
        Sender s = new Sender();
        Receiver r = new Receiver();
        SenderThread st = new SenderThread(uid, s, pa);
        ReceiverThread rt = new ReceiverThread(r, pa);
        st.run();
        rt.run();
        Scanner sc = new Scanner(System.in);
        String str = sc.next();
        if (str.equals("q")){
            st.finish();
            rt.finish();
        }
    }
}