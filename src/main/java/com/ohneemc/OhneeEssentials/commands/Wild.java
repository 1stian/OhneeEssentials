package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.events.CustomInventory;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Wild implements CommandExecutor, Listener {

    private OhneeEssentials plugin;


    //Settings
    private int w1MAxX;
    private int w1MinX;
    private int w1MaxZ;
    private int w1MinZ;
    private int w2MAxX;
    private int w2MinX;
    private int w2MaxZ;
    private int w2MinZ;
    private int cooldown;

    private boolean running = false;
    private List<Material> materials;
    private boolean hitNogo = false;

    public Wild(OhneeEssentials plugin) {
        this.plugin = plugin;
        this.cooldown = plugin.cooldown;
        this.w1MAxX = plugin.w1MaxX;
        this.w1MinX = plugin.w1MinX;
        this.w1MaxZ = plugin.w1MaxZ;
        this.w1MinZ = plugin.w1Minz;
        this.w2MAxX = plugin.w2MaxX;
        this.w2MinX = plugin.w2MinX;
        this.w2MaxZ = plugin.w2MaxZ;
        this.w2MinZ = plugin.w2Minz;
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

                    CustomInventory i = new CustomInventory();
                    if (player != null) {
                        i.newWildInventory(player);
                    }
                    return true;
                } else {
                    sender.sendMessage(ChatColor.GREEN + "You're already being teleported!");
                    return true;
                }
            }
        }

        return false;
    }

    @SuppressWarnings("ConstantConditions")
    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        //ClickType click = e.getClick();
        Inventory open = e.getClickedInventory();
        InventoryView title = e.getView();
        String invTitle = title.getTitle();
        ItemStack item = e.getCurrentItem();

        if (open == null) {
            return;
        }
        if (invTitle.equalsIgnoreCase(ChatColor.DARK_GREEN + "WildTP - Chose world below.")) {
            if (item == null || !item.hasItemMeta()) {
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(ChatColor.YELLOW + "Teleport to a random location in the overworld.")) {
                player.closeInventory();
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Finding a good place for you, hold on.");
                runWild((Player) e.getWhoClicked(), plugin.wildWorld1);
                return;
            }

            if (item.getItemMeta().getDisplayName().equals(ChatColor.RED + "Teleport to a random location in the nether.")) {
                player.closeInventory();
                e.getWhoClicked().sendMessage(ChatColor.GREEN + "Finding a good place for you, hold on.");
                //e.getWhoClicked().sendMessage(ChatColor.GREEN + "Disabled.. Will be enabled shortly.");
                runWild((Player) e.getWhoClicked(), plugin.wildWorld2);
                return;
            }
            e.setCancelled(true);
        }
    }

    private void runWild(Player player, String args) {
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, () -> {
            World world;
            if (args != null) {
                world = plugin.getServer().getWorld(args);
                if (world == null) {
                    world = player.getWorld();
                }
            } else {
                world = player.getWorld();
            }
            String wName = world.getName();
            Location tp = createTpW(world);

            if (tp == null || tp.add(0, 2, 0).getBlock().getType() != Material.AIR) {
                runWild(player, wName);
                return;
            }
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                if (hitNogo) {
                    runWild(player, wName);
                } else {
                    player.sendMessage(ChatColor.GREEN + "Found a location, teleporting!");
                    player.teleport(tp);
                    plugin.cMap().put(player.getUniqueId(), System.currentTimeMillis());
                    running = false;
                }
            }, 20L);
        });
    }

    private Location createTpW(World world) {
        if (world.getName().equalsIgnoreCase(plugin.wildWorld1)) {
            int rX = w1GetX();
            int rZ = w1GetZ();
            return getHighestBlock(world, 255,rX, rZ);
        }
        if (world.getName().equalsIgnoreCase(plugin.wildWorld2)) {
            int rX = w2GetX();
            int rZ = w2GetZ();
            return getHighestBlock(world, 100,rX, rZ);
        }
        return null;
    }

    private Integer w1GetX() {
        return (int) (Math.random() * ((w1MAxX - w1MinX) + 1)) + w1MinX;
    }

    private Integer w1GetZ() {
        return (int) (Math.random() * ((w1MaxZ - w1MinZ) + 1)) + w1MinZ;
    }

    private Integer w2GetX() {
        return (int) (Math.random() * ((w2MAxX - w2MinX) + 1)) + w2MinX;
    }

    private Integer w2GetZ() {
        return (int) (Math.random() * ((w2MaxZ - w2MinZ) + 1)) + w2MinZ;
    }

    private Location getHighestBlock(World world, int height, int x, int z) {
        hitNogo = false;
        int i = height;
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
}
