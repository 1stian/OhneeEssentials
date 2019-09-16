package com.ohneemc.OhneeEssentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Guide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Guide") && sender instanceof Player){
            if (args.length > 1){
                if (sender.hasPermission("Ohnee.guide.other")){
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null) {
                        sender.sendMessage(ChatColor.GREEN + "Guide has been sent to: " + ChatColor.GOLD + args[0]);
                        sendGuides(target);
                    }else{
                        sender.sendMessage(ChatColor.GREEN + "Couldn't find specified player.");
                    }
                }else{
                    sendGuides(((Player) sender).getPlayer());
                }
            }else{
                sendGuides(((Player) sender).getPlayer());
            }
        }
        return true;
    }

    private boolean sendGuides(Player player){
        if (player != null){
            player.sendMessage(ChatColor.GOLD +"-----------"+ChatColor.BOLD+"Server guides" + ChatColor.GOLD + "----------");
            player.sendMessage(ChatColor.GOLD + "----- " + ChatColor.GREEN + "https://ohneemc.com/guides/" + ChatColor.GOLD + " -----");
            player.sendMessage(ChatColor.GOLD + "-----------------------------------");
            return true;
        }else{
            return false;
        }
    }
}
