package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class chunkHelper {

    private OhneeEssentials plugin;
    private intRandomizer random;

    public chunkHelper(OhneeEssentials plugin) {
        this.plugin = plugin;
    }
    public chunkHelper(intRandomizer rand) {
        this.random = rand;
    }

    //Getting min and max from config
    private Integer maxX = plugin.getConfig().getInt("WildTP.maxX");
    private Integer minX = plugin.getConfig().getInt("WildTP.minX");
    private Integer maxZ = plugin.getConfig().getInt("WildTP.maxZ");
    private Integer minZ = plugin.getConfig().getInt("WildTP.minZ");

    private int xx;
    private int zz;

    private int maxLX;
    private int minLX;
    private int maxLZ;
    private int minLZ;

    private World world;

    private int retries = 0;

    private  boolean chunkLoader(Location chunkLoc) {
        try {
            Chunk chunk = chunkLoc.getChunk();
            chunk.load();
            return true;
        } catch (Exception ex) {
            plugin.getLogger().warning(ex.toString());
            return false;
        }
    }

    private  boolean safeBlock(Material block) {
        //Material block = loc.getBlock().getType();

        return !(block == Material.WATER || block == Material.LAVA);
    }

    public Location getLoc(Player player) {
        World worldLoc = player.getWorld();
        Location spawn = world.getSpawnLocation();

        int x = (int) spawn.getX();
        int z = (int) spawn.getY();

        maxLX = x;
        minLX = x;
        maxLZ = z;
        minLZ = z;

        xx = x;
        zz = z;

        world = worldLoc;

        player.sendMessage("0");

        Location randomLoc = randomizer();

        player.sendMessage("1");
        if (chunkLoader(randomLoc)) {
            player.sendMessage("2");
            if (safeBlock(randomLoc.getBlock().getType())) {
                player.sendMessage("3");
                //Getting highest block
                Block hBlock = randomLoc.getWorld().getHighestBlockAt(randomLoc);
                int tX = (int) randomLoc.getX();
                int tY = hBlock.getY();
                int tZ = (int) randomLoc.getZ();

                return new Location(worldLoc, tX, tY, tZ);
            } else {
                if (retries < 5) {
                    retries++;
                    player.sendMessage("Trying to find location! Try: " + retries + "/5");
                    getLoc(player);
                } else {
                    player.sendMessage("Couldn't find any safe locations.. Please try again.");
                }
            }
        }
        return null;
    }

    private  Location randomizer() {
        maxLX = maxX + maxLX;
        minLX = minX + minLX;
        maxLZ = maxZ + maxLZ;
        minLZ = minZ + minLZ;

        //Randomizing ints!
        int rX = random.randomInt(maxLX, minLX);
        int rZ = random.randomInt(maxLZ, minLZ);

        return new Location(world, xx, 0, zz);
    }
}
