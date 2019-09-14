package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    private OhneeEssentials plugin;
    public Vanish(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    private boolean vanished = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Vanish") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null && !vanished){
                for (Player online :Bukkit.getOnlinePlayers()){
                    online.hidePlayer(plugin, player);
                }
                vanished = true;
                sender.sendMessage(ChatColor.GREEN + "You've now " + ChatColor.GOLD + "vanished " + ChatColor.GREEN + "from all players.");
            }else if (player != null){
                for (Player online :Bukkit.getOnlinePlayers()){
                    online.showPlayer(plugin, player);
                }
                vanished = false;
                sender.sendMessage(ChatColor.GREEN + "You are no longer " + ChatColor.GOLD + "vanished " + ChatColor.GREEN + "from all players.");
            }
        }
        return false;
    }
}
