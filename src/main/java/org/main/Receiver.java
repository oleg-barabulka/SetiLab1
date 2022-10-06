package org.main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Vector;

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
    public void receive(PacketAnalyzer pa){
        var receivedMulticastDatagram = new DatagramPacket(new byte[400], 400);
        try {
            receiverSocket.receive(receivedMulticastDatagram);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String str = getStringFromData(receivedMulticastDatagram);
        pa.stringАnalisis(str);
        System.out.println(str);
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
        Vector<Integer> lengthArray = new Vector<>();
        int i = 0;
        while (packet.getData()[i]< 58 && packet.getData()[i] > 47){
            lengthArray.add((int) packet.getData()[i] );
            i++;
        }
        int strLength = 0;
        for (int j = 0; j < lengthArray.size(); j++){
            int decMul = 1;
            for (int k = 0; k < lengthArray.size() - j - 1; k++){
                decMul *= 10;
            }
            strLength += (lengthArray.get(j)-48) * decMul;
        }

        byte[] byteString = new byte[strLength];
        System.arraycopy(packet.getData(), i, byteString, 0, strLength);
        return new String(byteString);
    }

}
