package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Toml;
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
            boolean tomlSave = plugin.getConfig().getBoolean("PluginSettings.Warp.toml");
            boolean yamlSave = plugin.getConfig().getBoolean("PluginSettings.Warp.yaml");
            boolean jsonSave = plugin.getConfig().getBoolean("PluginSettings.Warp.json");
            boolean mysqlSave = plugin.getConfig().getBoolean("PluginSettings.Warp.mysql");


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

                //Yaml
                if (yamlSave){
                    plugin.warpConfig().set(warpName + ".world", world);
                    plugin.warpConfig().set(warpName + ".x", x);
                    plugin.warpConfig().set(warpName + ".y", y);
                    plugin.warpConfig().set(warpName + ".z", z);
                    plugin.warpConfig().set(warpName + ".pitch", pitch);
                    plugin.warpConfig().set(warpName + ".yaw", yaw);
                }

                //Toml
                if (tomlSave){
                    plugin.tomlWarps().set(warpName + ".world", world);
                    plugin.tomlWarps().set(warpName + ".x", x);
                    plugin.tomlWarps().set(warpName + ".y", y);
                    plugin.tomlWarps().set(warpName + ".z", z);
                    plugin.tomlWarps().set(warpName + ".pitch", pitch);
                    plugin.tomlWarps().set(warpName + ".yaw", yaw);
                }

                //JSON
                if (jsonSave){
                    plugin.jsonWarps().set(warpName + ".world", world);
                    plugin.jsonWarps().set(warpName + ".x", x);
                    plugin.jsonWarps().set(warpName + ".y", y);
                    plugin.jsonWarps().set(warpName + ".z", z);
                    plugin.jsonWarps().set(warpName + ".pitch", pitch);
                    plugin.jsonWarps().set(warpName + ".yaw", yaw);
                }

                //MySql/MariaDB

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
