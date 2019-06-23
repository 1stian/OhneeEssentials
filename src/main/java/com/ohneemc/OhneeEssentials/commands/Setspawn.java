package com.ohneemc.OhneeEssentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Setspawn") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            World world;
            if (player != null) {
                world =  player.getWorld();
                world.setSpawnLocation(player.getLocation());
                player.sendMessage("World spawn location has been set!");
                return true;
            }
            return false;
        }
        return false;
    }
}
