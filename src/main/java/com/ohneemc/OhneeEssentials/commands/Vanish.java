package com.ohneemc.OhneeEssentials.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Vanish implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Vanish") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            
        }
        return false;
    }
}
