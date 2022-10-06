package org.main;

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
        String contentLength = String.valueOf(content.length());
        content = contentLength + content;
        DatagramPacket datagram;
        try {
            datagram = new DatagramPacket(content.getBytes(), content.getBytes().length, InetAddress.getByName("224.1.1.1"), 8000);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        try {
            senderSocket.send(datagram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
