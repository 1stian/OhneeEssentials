package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.Consumer;

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

    private int countdown;
    private int cooldown;

    private List<String> safeBlocks;

    private boolean running = false;

    private List<Material> materials = new ArrayList<>();

    private int retries = 0;

    public Wild(OhneeEssentials plugin) {
        this.plugin = plugin;
        this.countdown = plugin.settings().getInt("PluginSettings.WildTP.countdown");
        this.cooldown = plugin.settings().getInt("PluginSettings.WildTP.cooldown");
        this.maxX = plugin.settings().getInt("PluginSettings.WildTP.Radius.maxX");
        this.minX = plugin.settings().getInt("PluginSettings.WildTP.Radius.minX");
        this.maxZ = plugin.settings().getInt("PluginSettings.WildTP.Radius.maxZ");
        this.minZ = plugin.settings().getInt("PluginSettings.WildTP.Radius.minZ");
        this.safeBlocks = plugin.safeBlocks;
    }

    private final Consumer<Object> callback = o -> {

    };

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Wild command
        if (command.getName().equalsIgnoreCase("Wild")) {
            if (((Player) commandSender).getPlayer() != null) {
                Player player = ((Player) commandSender).getPlayer();

                if (plugin.cMap().containsKey(player) && (plugin.cMap().get(player) / 1000 + cooldown) >= (System.currentTimeMillis() / 1000)) {
                    assert player != null;
                    long timeLeft = System.currentTimeMillis() - plugin.cMap().get(player);
                    player.sendMessage(ChatColor.GREEN + MessageHelper.timeLeft + (TimeUnit.MILLISECONDS.toSeconds(timeLeft) - cooldown) + " seconds");
                } else {
                    if (!running){
                        running = true;
                        commandSender.sendMessage(ChatColor.GREEN + "Looking for a safe location. Hold on.");
                        RunWild(player);
                    }else{
                        assert player != null;
                        player.sendMessage(ChatColor.GREEN+MessageHelper.alreadyBeingTeleported);
                    }
                }
                return true;
            } else {
                plugin.getLogger().warning("Player null... While using command /Wild");
                return false;
            }
        }
        return false;
    }

    private void RunWild(final Player player) {
        try {
            final World world = player.getWorld();

            //Random location
            final int rX = getX();
            final int rZ = getZ();

            //Filling list
            for (String material : safeBlocks) {
                materials.add(Material.getMaterial(material.toUpperCase()));
            }

            final Location[] tp = new Location[1];

            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable() {
                public void run() {
                    tp[0] = getHighestBlock(world, rX, rZ);
                    Block blockY = player.getWorld().getHighestBlockAt(tp[0]);
                    final Location ready = new Location(world, rX, blockY.getY(), rZ).subtract(0, 1, 0);

                    if (SafeBlock(ready)) {
                        callback.accept(delayMessage(player));
                        callback.accept(TeleportPlayer(player, ready));
                    } else {
                        if (retries < 30) {
                            retries++;
                            RunWild(player);
                        } else {
                            callback.accept(NotFound(player));
                            retries = 0;
                        }
                    }
                }

                private Object TeleportPlayer(final Player player, final Location ready) {
                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                        player.sendMessage(ChatColor.GREEN + "Teleporting");
                        plugin.cMap().put(player, (System.currentTimeMillis()));
                        player.teleport(ready.add(0, 2, 0));
                        running = false;
                    }, countdown * 20L);
                    return true;
                }

                private Object NotFound(Player player) {
                    running = false;
                    player.sendMessage(ChatColor.GREEN+MessageHelper.couldnTfind);
                    return true;
                }

                private Object delayMessage(Player player) {
                    player.sendMessage(ChatColor.GREEN + MessageHelper.youWillbeTped + countdown + " seconds");
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
