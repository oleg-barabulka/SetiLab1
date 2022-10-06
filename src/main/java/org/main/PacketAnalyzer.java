package org.main;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.abs;

public class PacketAnalyzer {

    private HashMap<Integer, Integer> aliveProg;
    private Integer progCount;

    public PacketAnalyzer(){
        aliveProg = new HashMap<>();
        progCount = 1;
    }

    public void stringАnalisis(String str){
        Date date = new Date();
        String keyWord = str.substring(0, str.indexOf(" "));
        if (keyWord.equals("hello")) {
            int id = Integer.parseInt(str.substring(str.indexOf(" ")+ 1));
            aliveProg.put(id, date.getSeconds());
        }else if(keyWord.equals("alive")){
            int id =  Integer.parseInt(str.substring(str.indexOf(" ")+ 1, str.lastIndexOf(" ")));
            aliveProg.put(id, date.getSeconds());
        }else if(keyWord.equals("bye")){
            int id = Integer.parseInt(str.substring(str.indexOf(" ")+ 1));
            aliveProg.remove(id);
        }
        progCount = aliveProg.size();
    }

    public void checkAliveProgCount(){
        Date date = new Date();
        for (Map.Entry<Integer, Integer> currMap: aliveProg.entrySet()){
            if(5 <= abs(date.getSeconds() - currMap.getValue()) && abs(date.getSeconds() - currMap.getValue()) <= 55){
                aliveProg.remove(currMap.getKey());
            }
        }
        progCount = aliveProg.size();
    }
    public int getProgCount(){
        return progCount;
    }
}
