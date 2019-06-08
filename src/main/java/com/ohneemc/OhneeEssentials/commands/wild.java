package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.resources.intRandomizer;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class wild {

    private OhneeEssentials plugin;
    private intRandomizer random;

    public wild(OhneeEssentials plugin) {
        this.plugin = plugin;
    }
    public wild (intRandomizer rand) {this.random = rand;}

    public wild(Player player, Plugin plugin) {
        //FileConfiguration config = plugin.getConfig();

        //Getting world spawn
        Location worldSpawn = player.getWorld().getSpawnLocation();
        int x = worldSpawn.getBlockX();
        int z = worldSpawn.getBlockZ();

        //Getting min and max from config
        Integer maxX = plugin.getConfig().getInt("WildTP.maxX");
        Integer minX = plugin.getConfig().getInt("WildTP.minX");
        Integer maxZ = plugin.getConfig().getInt("WildTP.maxZ");
        Integer minZ = plugin.getConfig().getInt("WildTP.minZ");

        //Ints for randomize - Originates from world spawn
        int maxLX = x;
        int minLX = x;
        int maxLZ = z;
        int minLZ = z;

        maxLX = maxX + maxLX;
        minLX = minX + minLX;
        maxLZ = maxZ + maxLZ;
        minLZ = minZ + minLZ;

        //Randomizing ints!
        final int rX = random.randomInt(maxLX, minLX);
        final int rZ = random.randomInt(maxLZ, minLZ);

        player.sendMessage("Location: rX: " + rX + ", rZ: " + rZ);

        Location toLoad = new Location(player.getWorld(), rX, 0 ,rZ);
        Chunk destChunk = toLoad.getChunk();
        //plugin.getLogger().info("Loading chunk at: x" + rX + ", z" + rZ);
        destChunk.load();
        //plugin.getLogger().info("Chunk loaded!");

        Block hBlock = toLoad.getWorld().getHighestBlockAt(toLoad);

        Location tp = new Location(toLoad.getWorld(), rX, hBlock.getY(), rZ);

        player.teleport(tp);
    }
}
