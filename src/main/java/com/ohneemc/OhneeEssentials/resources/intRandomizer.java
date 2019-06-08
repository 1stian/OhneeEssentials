package com.ohneemc.OhneeEssentials.resources;

import java.util.Random;

public class intRandomizer {

    public static int randomInt(int max, int min){
        //return random.nextInt(max - min + 1) + min;
        Random random = new Random();

        return random.ints(min, (max+1)).findFirst().getAsInt();
    }
}
