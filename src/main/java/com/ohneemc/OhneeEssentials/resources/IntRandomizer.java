package com.ohneemc.OhneeEssentials.resources;

class IntRandomizer {

    static int randomInt(int max, int min){
        //return random.nextInt(max - min + 1) + min;
        //Random random = new Random();

        //return random.nextInt(max + 1 - min) + max;

        return (int)(Math.random() * ((max -min) + 1 )) + min;
    }
}
