package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.resources.intRandomizer;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class wild {

    private OhneeEssentials plugin;
    private intRandomizer random;

    public wild(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    public wild(Player player) {
        //FileConfiguration config = plugin.getConfig();

        //Getting world spawn
        Location worldSpawn = player.getWorld().getSpawnLocation();
        int x = worldSpawn.getBlockX();
        int z = worldSpawn.getBlockZ();

        //Getting min and max from config
        Integer maxX = plugin.config.getInt("WildTP.maxX");
        Integer minX = plugin.config.getInt("WildTP.minX");
        Integer maxZ = plugin.config.getInt("WildTP.maxZ");
        Integer minZ = plugin.config.getInt("WildTP.minZ");

        //Ints for randomize - Originates from world spawn
        int maxLX = x + maxX;
        int minLX = x - minX;
        int maxLZ = z + maxZ;
        int minLZ = z - minZ;

        //Randomizing ints!
        int rX = random.randomInt(maxLX, minLX);
        int rZ = random.randomInt(maxLZ, minLZ);

        player.sendMessage("Location: rX: " + rX + ", rZ: " + rZ);
    }
}
