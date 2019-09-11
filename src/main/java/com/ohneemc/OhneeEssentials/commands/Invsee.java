package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class Invsee implements CommandExecutor {
    private OhneeEssentials plugin;
    public Invsee(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Invsee") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Player target;

            if (args.length == 1){
                target = plugin.getServer().getPlayer(args[0]);
                PlayerInventory pi;
                if (player != null && target != null) {
                    pi = target.getInventory();
                    player.openInventory(pi);
                    sender.sendMessage(ChatColor.GREEN + "Opened " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " inventory.");
                    return true;
                }else{
                    sender.sendMessage(ChatColor.GREEN + "Couldn't open " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " inventory.");
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.GREEN + "You must specify a player to open their inventory.");
                return true;
            }
        }
        return false;
    }
}