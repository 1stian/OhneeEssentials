package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.LightningEditor;
import de.leonhard.storage.Toml;
import de.leonhard.storage.base.LightningBase;
import de.leonhard.storage.base.TomlBase;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Setwarp implements CommandExecutor {
    private OhneeEssentials plugin;
    public Setwarp(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Setwarp") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            String warpName = null;

            if (strings.length < 1) {
                return false;
            } else {
                warpName = strings[0].toLowerCase();
            }

            if (OhneeEssentials.warpMap.containsKey(warpName)) {
                player.sendMessage("A warp with the name: " + warpName + " already exists!");
                return true;
            } else {
                Location warpLoc = player.getLocation();
                OhneeEssentials.warpMap.put(warpName, warpLoc);

                String world = warpLoc.getWorld().getName();
                double x = warpLoc.getX();
                double y = warpLoc.getY();
                double z = warpLoc.getZ();
                float pitch = warpLoc.getPitch();
                float yaw = warpLoc.getYaw();

                plugin.warpConfig().set(warpName + ".world", world);
                plugin.warpConfig().set(warpName + ".x", x);
                plugin.warpConfig().set(warpName + ".y", y);
                plugin.warpConfig().set(warpName + ".z", z);
                plugin.warpConfig().set(warpName + ".pitch", pitch);
                plugin.warpConfig().set(warpName + ".yaw", yaw);

                //Toml format test:


                try {
                    plugin.warpConfig().save(plugin.getWarpFile());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                player.sendMessage("Warp: " + warpName + " has been set!");
                return true;
            }
        }
        return false;
    }
}
