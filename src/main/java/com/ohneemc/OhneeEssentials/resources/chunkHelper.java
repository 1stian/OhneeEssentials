package com.ohneemc.OhneeEssentials.resources;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

public class chunkHelper {

    public Location chunkLoader(Location chunkLoc){
        Chunk chunk = chunkLoc.getChunk();
        chunk.load();

        //Getting highest block
        Block hBlock = chunkLoc.getWorld().getHighestBlockAt(chunkLoc);

        //Coordinates
        World world = chunkLoc.getWorld();
        int y = hBlock.getY();
        int x = chunkLoc.getBlockX();
        int z = chunkLoc.getBlockZ();


        Location tp = new Location(world, x, y, z);
        return tp;
    }
}
