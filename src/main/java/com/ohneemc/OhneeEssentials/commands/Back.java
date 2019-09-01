package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Back implements CommandExecutor {

    private OhneeEssentials ohnee;
    public Back(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Back") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Location lasLoc = (Location) ohnee.lLoc().get(player != null ? player.getUniqueId() : null);

            if (player != null && lasLoc != null) {
                player.teleport(lasLoc);
                player.sendMessage("You've been teleported to your previous location!");
                return true;
            }
            return false;
        }

        return false;
    }
}
