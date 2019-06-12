package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class chunkHelper {

    private OhneeEssentials plugin;

    private chunkHelper (OhneeEssentials pl) {
        this.plugin = pl;
    }

    private intRandomizer random;

    public chunkHelper(intRandomizer rand) {
        this.random = rand;
    }

    //Getting min and max from config
    private Integer maxX;
    private Integer minX;
    private Integer maxZ;
    private Integer minZ;

    private int xx;
    private int zz;

    private int maxLX;
    private int minLX;
    private int maxLZ;
    private int minLZ;

    private World world;

    private int retries = 0;

    public chunkHelper() {

    }

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
        //System.out.println("block var debug: " + block);
        return !(block == Material.WATER || block == Material.LAVA);
    }

    public Location getLoc(Player player, Plugin plugin) {
        World worldLoc = player.getWorld();
        Location spawn = worldLoc.getSpawnLocation();

        maxX = plugin.getConfig().getInt("WildTP.maxX");
        minX = plugin.getConfig().getInt("WildTP.minX");
        maxZ = plugin.getConfig().getInt("WildTP.maxZ");
        minZ = plugin.getConfig().getInt("WildTP.minZ");

        int x = (int) spawn.getX();
        int z = (int) spawn.getY();

        maxLX = x;
        minLX = x;
        maxLZ = z;
        minLZ = z;

        xx = x;
        zz = z;

        world = worldLoc;

        //player.sendMessage("0");

        Location randomLoc = randomizer(world);

        //player.sendMessage("1");
        if (chunkLoader(randomLoc)) {
            //player.sendMessage("2");
            if (safeBlock(randomLoc.getBlock().getType())) {
                //player.sendMessage("3");
                //Getting highest block
                //int hBlock = randomLoc.getWorld().getHighestBlockYAt(randomLoc);
                int tX = randomLoc.getBlockX();
                int tY = randomLoc.getBlockY() + 1;
                int tZ = randomLoc.getBlockX();

                return new Location(worldLoc, tX, tY, tZ);
            } else {
                if (retries < 5) {
                    retries++;
                    player.sendMessage("Trying to find location! Try: " + retries + "/5");
                    getLoc(player, plugin);
                } else {
                    player.sendMessage("Couldn't find any safe locations.. Please try again.");
                }
            }
        }
        return null;
    }

    private  Location randomizer(World world) {
        maxLX = maxX + maxLX;
        minLX = minX + minLX;
        maxLZ = maxZ + maxLZ;
        minLZ = minZ + minLZ;

        //Randomizing ints!
        int rX = random.randomInt(maxLX, minLX);
        int rZ = random.randomInt(maxLZ, minLZ);

        int y = world.getHighestBlockYAt(rX, rZ);

        return new Location(world, rX, y, rZ);
    }
}
