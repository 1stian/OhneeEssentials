package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChunkHelper {

    private OhneeEssentials plugin;

    private ChunkHelper(OhneeEssentials pl) {
        this.plugin = pl;
    }

    public ChunkHelper(IntRandomizer rand) {
    }

    //Getting min and max from config
    private Integer maxX;
    private Integer minX;
    private Integer maxZ;
    private Integer minZ;

    private int maxLX;
    private int minLX;
    private int maxLZ;
    private int minLZ;

    private int retries = 0;

    private boolean rerun = false;

    public ChunkHelper() {

    }

    private boolean chunkLoader(Location chunkLoc) {
        try {
            //chunkLoc.getWorld().loadChunk(chunkLoc.getChunk().getX(), chunkLoc.getChunk().getZ(), true);
            //chunkLoc.getWorld().getChunkAt(chunkLoc.getChunk().getX(), chunkLoc.getChunk().getZ());
            //chunkLoc.getChunk().load(true);
            System.out.println("Loading!");
            return chunkLoc.getChunk().load(true);
        } catch (Exception ex) {
            plugin.getLogger().warning(ex.toString());
            return false;
        }
    }

    private boolean isChunkLoded(Location loc) {
        return loc.getWorld().isChunkLoaded(loc.getChunk().getX(), loc.getChunk().getZ());
    }

    private boolean safeBlock(Material block, Location check) {
        int x = check.getBlockX();
        int y = check.getBlockY();
        int z = check.getBlockZ();

        Location oneAbove = new Location(check.getWorld(), x, y + 1, z);
        Location oneUnder = new Location(check.getWorld(), x, y - 1, z);

        Material mY = check.getBlock().getType();
        Material above = oneAbove.getBlock().getType();
        Material under = oneAbove.getBlock().getType();

        System.out.println("above var debug: " + above);
        System.out.println("middle var debug: " + mY);
        System.out.println("under var debug: " + under);

        return (mY == Material.AIR || above == Material.AIR || under != Material.WATER && under != Material.LAVA);
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

        Location randomLoc = randomizer(worldLoc);

        if (chunkLoader(randomLoc)) {
            for (int i = 0; i < 30; i++) {
                System.out.println("Number: " + i);
                if (isChunkLoded(randomLoc)) {
                    System.out.println("Oh it's loaded!");
                    if (safeBlock(randomLoc.getBlock().getType(), randomLoc)) {
                        System.out.println("And safe!! Woaah!");
                        int tX = randomLoc.getBlockX();
                        int tY = randomLoc.getBlockY();
                        int tZ = randomLoc.getBlockX();
                        //rerun = false;

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

                    //i = 0;
                }else{
                    System.out.println("Not loaded!");
                    i = 0;
                }
            }
        }
        return null;
    }

    private Location randomizer(World world) {
        maxLX = maxX + maxLX;
        minLX = minX + minLX;
        maxLZ = maxZ + maxLZ;
        minLZ = minZ + minLZ;

        //Randomizing ints!
        int rX = IntRandomizer.randomInt(maxLX, minLX);
        int rZ = IntRandomizer.randomInt(maxLZ, minLZ);

        return getHighestBlock(world, rX, rZ);
    }

    private Location getHighestBlock(World world, int x, int z) {
        int i = 255;
        while (i > 0) {
            if (new Location(world, x, i, z).getBlock().getType() != Material.AIR)
                return new Location(world, x, i, z).add(0, 1, 0);
            i--;
        }
        return new Location(world, x, 1, z);
    }
}
