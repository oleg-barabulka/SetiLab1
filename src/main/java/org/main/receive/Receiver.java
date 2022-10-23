package org.main.receive;

import org.main.PacketAnalyzer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


public class Receiver {
    public MulticastSocket receiverSocket;
    public Receiver(){
        try {
            receiverSocket = new MulticastSocket(8000);
            receiverSocket.joinGroup(InetAddress.getByName("224.1.1.1"));
            receiverSocket.setSoTimeout(5000);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void receive(PacketAnalyzer packetAnalyzer){
        var receivedMulticastDatagram = new DatagramPacket(new byte[400], 400);
        try {
            receiverSocket.receive(receivedMulticastDatagram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String str = getStringFromData(receivedMulticastDatagram);
        packetAnalyzer.stringАnalysis(str);
    }
    public void finishReceiving(){
        try {
            receiverSocket.leaveGroup(InetAddress.getByName("224.1.1.1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        receiverSocket.close();
    }

    String getStringFromData(DatagramPacket packet){
        final int maxLength = 3;
        byte[] byteStringLength = new byte[maxLength];
        System.arraycopy(packet.getData(), 0, byteStringLength, 0, maxLength);
        String lengthString = new String(byteStringLength);
        int length = Integer.parseInt(lengthString.split(" ")[0]);
        byte[] byteString = new byte[length];
        System.arraycopy(packet.getData(), maxLength, byteString, 0, length);
        return new String(byteString);
    }

}
