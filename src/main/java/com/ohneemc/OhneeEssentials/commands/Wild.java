package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Wild implements CommandExecutor {

    private OhneeEssentials plugin;

    //Settings
    private int maxX;
    private int minX;
    private int maxZ;
    private int minZ;
    private int cooldown;

    private boolean running = false;
    private List<Material> materials = new ArrayList<>();
    private boolean hitNogo = false;

    public Wild(OhneeEssentials plugin) {
        this.plugin = plugin;
        this.cooldown = plugin.cooldown;
        this.maxX = plugin.maxX;
        this.minX = plugin.minX;
        this.maxZ = plugin.maxZ;
        this.minZ = plugin.minZ;
        this.materials = plugin.materials;
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Wild") && sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            if (player != null && plugin.cMap().containsKey(player.getUniqueId()) && (plugin.cMap().get(player.getUniqueId()) / 1000 + cooldown) >= (System.currentTimeMillis() / 1000)) {
                long timeLeft = System.currentTimeMillis() - plugin.cMap().get(player.getUniqueId());
                sender.sendMessage(ChatColor.GREEN + MessageHelper.timeLeft + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldown) + " seconds");
                return true;
            } else {
                if (!running) {
                    running = true;
                    sender.sendMessage(ChatColor.GREEN + "Looking for a safe location. Hold on.");
                    String world = null;
                    if (strings.length == 1) {
                        world = strings[0];
                    }

                    return runWild(player, world);
                } else {
                    sender.sendMessage(ChatColor.GREEN + "You're already being teleported!");
                    return true;
                }
            }
        }

        return false;
    }

    private boolean runWild(Player player, String args) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                World world;
                if (args != null) {
                    world = plugin.getServer().getWorld(args);
                    if (world == null){
                        world = player.getWorld();
                    }
                } else {
                    world = player.getWorld();
                }
                String wName = world.getName();
                Location tp = createTp(player, world);
                if (tp == null || tp.add(0,1,0).getBlock().getType() != Material.AIR) {
                    runWild(player, wName);
                    return;
                }

                plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (hitNogo) {
                            runWild(player, wName);
                        } else {
                            plugin.cMap().put(player.getUniqueId(), System.currentTimeMillis());
                            player.sendMessage(ChatColor.GREEN + "Found a location, teleporting!");
                            player.teleport(tp);
                            running = false;
                        }
                    }
                }, 20L);
            }
        });
        return true;
    }

    private Location createTp(Player player, World world) {
        int rX = getX();
        int rZ = getZ();
        return getHighestBlock(world, rX, rZ);
    }

    private Integer getX() {
        return (int) (Math.random() * ((maxX - minX) + 1)) + minX;
    }

    private Integer getZ() {
        return (int) (Math.random() * ((maxZ - minZ) + 1)) + minZ;
    }

    private Location getHighestBlock(World world, int x, int z) {
        plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
            @Override
            public void run() {
                plugin.getServer().getWorld(world.getUID()).loadChunk(x, z, true);
            }
        });
        hitNogo = false;

        int i = 255;
        Location check = new Location(world, x, i, z);
        while (i > 0) {
            if (!materials.contains(new Location(world, x, i, z).getBlock().getType())) {
                return new Location(world, x, i, z).add(0, 0, 0);
            } else {
                if (materials.contains(check.getBlock().getType()) && check.getBlock().getType() != Material.AIR) {
                    hitNogo = true;
                }
                i--;
            }
        }
        return null;
    }

    /**
    private boolean SafeBlock(Location tp) {
        Material block = tp.getBlock().getType();
        //plugin.getLogger().info(block.toString());
        return (materials.contains(block));
    }
     **/
}
