package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Set;

public class Warp implements CommandExecutor {
    private OhneeEssentials plugin;
    public Warp(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Warp") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            String warpName;

            if (strings.length < 1) {
                Set<String> warps = plugin.wMap().keySet();
                String n1 = warps.toString();
                String n2 = n1.replaceAll("]", "");
                String name = n2.replaceAll("\\[", "");
                if (player != null) {
                    player.sendMessage(ChatColor.GREEN + "Warps: " + name);
                    return true;
                }
                return false;
            } else {
                warpName = strings[0].toLowerCase();
            }

            if (!plugin.wMap().containsKey(warpName)) {
                if (player != null) {
                    player.sendMessage(ChatColor.GREEN + "A warp with the name: " + warpName + " does not exist!");
                    return true;
                }
                return false;
            } else {
                Location loc = plugin.wMap().get(warpName);
                if (player != null) {
                    player.teleport(loc.add(0,0.2, 0));
                    player.sendMessage(ChatColor.GREEN + "You've been teleported to: " + warpName);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}
