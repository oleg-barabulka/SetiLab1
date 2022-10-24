package org.main;

import org.main.receive.KeyWords;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import static java.lang.Math.abs;

public class PacketAnalyzer {
    private Vector<String> ipArray;
    private HashMap<Integer, Long> aliveProg;
    private Integer progCount;

    public PacketAnalyzer(){
        aliveProg = new HashMap<>();
        progCount = 1;
        ipArray = new Vector<>();
    }

    public void stringАnalysis(String str){
        final int START_OF_LINE = 0;
        final int PROG_ID_SPACE = 4;
        final int SPACE_LENGTH = 1;
        String ipPart = str.substring(str.indexOf(" ") + PROG_ID_SPACE);
        String massagePart = str.substring(START_OF_LINE,str.indexOf(" ") + PROG_ID_SPACE - SPACE_LENGTH);

        KeyWords keyWord = KeyWords.valueOf(KeyWords.class, massagePart.split(" ")[0]);
        int id = Integer.parseInt(massagePart.split(" ")[1]);

        switch (keyWord) {
            case hello -> {
                aliveProg.put(id, System.currentTimeMillis());
                ipArray.add(ipPart);
                for (String s : ipArray) {
                    System.out.print(s + " ");
                }
            }
            case alive -> {
                aliveProg.put(id, System.currentTimeMillis());
            }
            case bye -> {
                aliveProg.remove(id);
                for(int i = 0; i < ipArray.size(); i++){
                    if (ipArray.get(i).equals(ipPart)){
                        ipArray.remove(i);
                        break;
                    }
                }
                for (String s : ipArray) {
                    System.out.print(s + " ");
                }
            }
        }
        progCount = aliveProg.size();
    }

    public void checkAliveProgCount(){
        final int waitingTime = 5000;
        for (Map.Entry<Integer, Long> currMap: aliveProg.entrySet()){
            if(abs(System.currentTimeMillis() - currMap.getValue()) >= waitingTime){
                aliveProg.remove(currMap.getKey());
                for (String s : ipArray) {
                    System.out.print(s + " ");
                }
            }
        }
        progCount = aliveProg.size();
    }
    public int getProgCount(){
        return progCount;
    }
}
