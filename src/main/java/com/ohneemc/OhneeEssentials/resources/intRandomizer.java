package com.ohneemc.OhneeEssentials.resources;

import java.util.Random;

public class intRandomizer {

    public static int randomInt(int max, int min){
        //return random.nextInt(max - min + 1) + min;
        //Random random = new Random();

        //return random.nextInt(max + 1 - min) + max;

        int result = (int)(Math.random() * ((max -min) + 1 )) + min;

        return result;
    }
}
