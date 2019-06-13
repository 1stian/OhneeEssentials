package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MessageHelper {
    public static String timeLeft;
    public static String alreadyBeingTeleported;
    public static String couldnTfind;
    public static String youWillbeTped;



    public MessageHelper(OhneeEssentials plugin){
        File customMessages = new File(plugin.getDataFolder(), "messages.txt");

        //List of messages to write to file
        List<String> lines = Arrays.asList(
                "Cooldown-Text;;;You must wait: ",
                "Already-being-teleported;;;You're already being teleported, be patient!",
                "You-will-be-teleported;;;You will be teleported in approx: ",
                "Couldn't-find-location;;;Couldn't find any location... Please try again."
        );

        if (!customMessages.exists()){
            try {
                customMessages.createNewFile();
                Path file = Paths.get(String.valueOf(customMessages));
                Files.write(file, lines, StandardCharsets.UTF_8);
                loadMessages(String.valueOf(customMessages));
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }else{
            loadMessages(String.valueOf(customMessages));
        }
    }

    public boolean loadMessages(String fileName){
        List<String> listMsg = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            for(String line; (line = br.readLine()) != null; ) {
                listMsg.add(line);
                System.out.print(line);
            }

            String l1 = listMsg.get(0).split(";;;")[1];
            timeLeft = l1;

            String l2 = listMsg.get(1).split(";;;")[1];
            alreadyBeingTeleported = l2;

            String l3 = listMsg.get(2).split(";;;")[1];
            youWillbeTped = l3;

            String l4 = listMsg.get(3).split(";;;")[1];
            couldnTfind = l4;

            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
