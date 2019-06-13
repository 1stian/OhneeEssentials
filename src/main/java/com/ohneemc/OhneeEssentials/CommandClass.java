package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.Wild;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Consumer;

import java.util.ArrayList;
import java.util.List;

public class CommandClass implements CommandExecutor {

    private OhneeEssentials plugin;

    public CommandClass(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    //Settings
    private int maxX;
    private int minX;
    private int maxZ;
    private int minZ;

    private List<Material> materials = new ArrayList<Material>();

    private int retries = 0;

    private final Consumer<Object> callback = new Consumer<Object>() {
        public void accept(Object o) {

        }
    };

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Wild command
        if (command.getName().equalsIgnoreCase("Wild")) {
            if (commandSender instanceof Player && commandSender.hasPermission("ohnee.Wild")) {
                if (((Player) commandSender).getPlayer() != null) {
                    Player player = ((Player) commandSender).getPlayer();
                    Wild(player, plugin);
                    return true;
                } else {
                    plugin.getLogger().warning("Player null... While using command /Wild");
                    return false;
                }
                //new Wild(((Player) commandSender).getPlayer());
            }
        }
        return false;
    }

    private void Wild(final Player player, final Plugin plugin) {
        try {
            maxX = plugin.getConfig().getInt("WildTP.Radius.maxX");
            minX = plugin.getConfig().getInt("WildTP.Radius.minX");
            maxZ = plugin.getConfig().getInt("WildTP.Radius.maxZ");
            minZ = plugin.getConfig().getInt("WildTP.Radius.minZ");
            List<String> safeBlocks = plugin.getConfig().getStringList("WildTP.SafeBlocks");

            final World world = player.getWorld();

            //Adding spawn coordinates to ints
            maxX = maxX + world.getSpawnLocation().getBlockX();
            minX = minX + world.getSpawnLocation().getBlockX();
            maxZ = maxZ + world.getSpawnLocation().getBlockZ();
            minZ = minZ + world.getSpawnLocation().getBlockZ();

            //Random location
            final int rX = getX();
            final int rZ = getZ();

            //Filling list
            for (String material : safeBlocks) {
                materials.add(Material.getMaterial(material));
            }

            final Location[] tp = new Location[1];

            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    tp[0] = getHighestBlock(world, rX, rZ);
                    Block blockY = player.getWorld().getHighestBlockAt(tp[0]);
                    final Location ready = new Location(world, rX, blockY.getY(), rZ).subtract(0,1,0);

                    if (SafeBlock(ready)) {
                        callback.accept(TeleportPlayer(player, ready));
                    } else {
                        if (retries < 10) {
                            retries++;
                            Wild(player, plugin);
                        } else {
                            callback.accept(NotFound(player));
                            retries = 0;
                        }
                    }
                }

                private Object TeleportPlayer(final Player player, final Location ready) {
                    plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                        public void run() {
                            player.sendMessage("Teleporting");
                            player.teleport(ready.add(0,2,0));
                        }
                    });
                    return true;
                }

                private Object NotFound(Player player) {
                    player.sendMessage("Couldn't find any location... Please try again.");
                    return true;
                }
            });
        } catch (NullPointerException ex) {
            plugin.getLogger().warning(ex.toString());
        }
    }

    private Integer getX() {
        return (int) (Math.random() * ((maxX - minX) + 1)) + minX;
    }

    private Integer getZ() {
        return (int) (Math.random() * ((maxZ - minZ) + 1)) + minZ;
    }

    private Location getHighestBlock(World world, int x, int z) {
        int i = 255;
        while (i > 0) {
            if (materials.contains(new Location(world, x, i, z).getBlock().getType())) {
                return new Location(world, x, i, z).add(0, 0, 0);
            } else {
                i--;
            }
        }
        return new Location(world, x, 1, z);
    }

    private boolean SafeBlock(Location tp) {
        Material block = tp.getBlock().getType();
        //plugin.getLogger().info(block.toString());
        return (materials.contains(block));
    }
}
