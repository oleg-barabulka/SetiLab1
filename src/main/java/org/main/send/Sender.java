package org.main.send;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Sender {
    public MulticastSocket senderSocket;
    public Sender(){
        try {
            senderSocket = new MulticastSocket(8000);
            senderSocket.joinGroup(InetAddress.getByName("224.1.1.1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void sendDatagram(String content)  {
        final int MAX_LENGTH = 3;
        StringBuilder contentLength = new StringBuilder(String.valueOf(content.length()));
        while (contentLength.length() < MAX_LENGTH){
            contentLength.append(" ");
        }
        content = contentLength + content;
        DatagramPacket datagram;
        try {
            datagram = new DatagramPacket(content.getBytes(), content.getBytes().length, InetAddress.getByName("224.1.1.1"), 8000);
            senderSocket.send(datagram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
