package com.ohneemc.OhneeEssentials.commands;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Spawn") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null) {
                World world = player.getWorld();
                player.teleport(world.getSpawnLocation());
                player.sendMessage("You've been teleported to spawn!");
                return true;
            }
            return false;
        }
        return false;
    }
}
