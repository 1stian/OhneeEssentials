package com.ohneemc.OhneeEssentials.resources;

public class StringCreater {

    public String messageCreater(String[] message){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < message.length; i++) {
            if (i > 0) sb.append(" ");
            sb.append(message[i]);
        }
        return sb.toString();
    }
}
