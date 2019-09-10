package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {

    private OhneeEssentials plugin;
    public Back(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Back") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Location lasLoc = (Location) plugin.lLoc().get(player != null ? player.getUniqueId() : null);

            if (player != null && lasLoc != null) {
                player.teleport(lasLoc);
                player.sendMessage(ChatColor.GREEN + "You've been teleported to your previous location!");
                return true;
            }
            return false;
        }

        return false;
    }
}
