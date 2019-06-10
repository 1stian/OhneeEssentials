package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class chunkHelper {

    private OhneeEssentials plugin;
    private intRandomizer random;

    public chunkHelper(OhneeEssentials plugin) {
        this.plugin = plugin;
    }
    public chunkHelper(intRandomizer rand) {this.random = rand;}

    public boolean chunkLoader(Location chunkLoc){
        try {
            Chunk chunk = chunkLoc.getChunk();
            chunk.load();
            return true;
        }catch(Exception ex){
            plugin.getLogger().warning(ex.toString());
            return false;
        }
    }

    public Location chunkFinder(Player player, Plugin plugin) {
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
        int rX = random.randomInt(maxLX, minLX);
        int rZ = random.randomInt(maxLZ, minLZ);

        //Getting highest block
        Block hBlock = chunkLoc.getWorld().getHighestBlockAt(chunkLoc);

        //Coordinates
        World world = chunkLoc.getWorld();
        int y = hBlock.getY();
        int x = chunkLoc.getBlockX();
        int z = chunkLoc.getBlockZ();

        Location tp = new Location(world, x, y, z);

        if (safeBlock(hBlock.getLocation().getBlock().getType())){
            return tp;
        }else{
            rX = random.randomInt(maxLX, minLX);
            rZ = random.randomInt(maxLZ, minLZ);
        }
    }

    public boolean safeBlock(Material block){
        //Material block = loc.getBlock().getType();

        if (!(block == Material.WATER || block == Material.LAVA)){
            return true;
        }else{
            return false;
        }
    }

    public Location getLoc(Location tp){
        if (chunkLoader(tp)){
            chunkFinder(tp);
        }
    }
}
