package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
                try {
                    Location warpLoc = player.getLocation();
                    OhneeEssentials.warpMap.put(warpName, warpLoc);

                    String world = warpLoc.getWorld().getName();
                    double x = warpLoc.getX();
                    double y = warpLoc.getY();
                    double z = warpLoc.getZ();
                    float pitch = warpLoc.getPitch();
                    float yaw = warpLoc.getYaw();

                    switch (plugin.fileUse()){
                        case 1:
                            //JSON
                            plugin.jsonWarps().set(warpName + ".world", world);
                            plugin.jsonWarps().set(warpName + ".x", x);
                            plugin.jsonWarps().set(warpName + ".y", y);
                            plugin.jsonWarps().set(warpName + ".z", z);
                            plugin.jsonWarps().set(warpName + ".pitch", pitch);
                            plugin.jsonWarps().set(warpName + ".yaw", yaw);
                            break;
                        case 2:
                            //Toml
                            plugin.tomlWarps().set(warpName + ".world", world);
                            plugin.tomlWarps().set(warpName + ".x", x);
                            plugin.tomlWarps().set(warpName + ".y", y);
                            plugin.tomlWarps().set(warpName + ".z", z);
                            plugin.tomlWarps().set(warpName + ".pitch", pitch);
                            plugin.tomlWarps().set(warpName + ".yaw", yaw);
                            break;
                        case 3:
                            //Yaml
                            plugin.yamlWarps().set(warpName + ".world", world);
                            plugin.yamlWarps().set(warpName + ".x", x);
                            plugin.yamlWarps().set(warpName + ".y", y);
                            plugin.yamlWarps().set(warpName + ".z", z);
                            plugin.yamlWarps().set(warpName + ".pitch", pitch);
                            plugin.yamlWarps().set(warpName + ".yaw", yaw);
                            break;
                    }
                    //MySql/MariaDB

                    player.sendMessage("Warp: " + warpName + " has been set!");
                    return true;
                }catch (Exception ex){
                    ex.printStackTrace();
                    player.sendMessage("Couldn't set warp, contact your server admins.");
                }
            }
        }
        return false;
    }
}
