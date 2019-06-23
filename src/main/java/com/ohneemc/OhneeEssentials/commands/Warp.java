package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class Warp implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Warp") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            String warpName;

            if (strings.length < 1) {
                Set<String> warps = OhneeEssentials.warpMap.keySet();
                String n1 = warps.toString();
                String n2 = n1.replaceAll("]", "");
                String name = n2.replaceAll("\\[", "");
                if (player != null) {
                    player.sendMessage("Warps: " + name);
                    return true;
                }
                return false;
            } else {
                warpName = strings[0].toLowerCase();
            }

            if (!OhneeEssentials.warpMap.containsKey(warpName)) {
                if (player != null) {
                    player.sendMessage("A warp with the name: " + warpName + " does not exist!");
                    return true;
                }
                return false;
            } else {
                Location loc = OhneeEssentials.warpMap.get(warpName);
                if (player != null) {
                    player.teleport(loc);
                    player.sendMessage("You've been teleported to: " + warpName);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
