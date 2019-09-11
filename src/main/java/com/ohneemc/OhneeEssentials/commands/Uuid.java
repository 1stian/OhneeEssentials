package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Uuid implements CommandExecutor {
    private OhneeEssentials plugin;
    public Uuid(OhneeEssentials plugin){this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Uuid")){
            if (args.length == 1){
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target != null){
                    sender.sendMessage(ChatColor.GREEN + target.getName() + " UUID: " + ChatColor.GOLD + " " + target.getUniqueId());
                    return true;
                }else{
                    try {
                        OfflinePlayer[] oPlayers = plugin.getServer().getOfflinePlayers();
                        plugin.getServer().getLogger().info("Test grab offline: " + oPlayers.toString());
                        return true;
                    }catch (Exception e){
                        sender.sendMessage(ChatColor.GREEN + "Something went wrong trying to grab player... " + ChatColor.RED + e.getCause().toString());
                    }
                }
            }else{
                sender.sendMessage(ChatColor.GREEN + "You must specifit a player.. /uuid <name>");
            }
        }
        return true;
    }
}
