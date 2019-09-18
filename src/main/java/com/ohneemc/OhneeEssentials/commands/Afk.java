package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.events.AfkListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Afk implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("afk") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null && AfkListener.isAfk().containsKey(player.getUniqueId())) {
                AfkListener.cancelAfk(player);
                return true;
            }else if (player != null){
                AfkListener.setAfk(player);
                return true;
            }
        }
        return false;
    }
}
