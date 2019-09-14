package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn implements CommandExecutor {
    private OhneeEssentials plugin;
    public Setspawn(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Setspawn") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            World world;
            if (player != null) {
                world =  player.getWorld();
                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                float yaw = player.getLocation().getYaw();
                float pitch = player.getLocation().getPitch();

                plugin.worldData().set(world.getName() + ".name", world.getName());
                plugin.worldData().set(world.getName() + ".X", x);
                plugin.worldData().set(world.getName() + ".Y", y);
                plugin.worldData().set(world.getName() + ".Z", z);
                plugin.worldData().set(world.getName() + ".Yaw", yaw);
                plugin.worldData().set(world.getName() + ".Pitch", pitch);

                Location newSpawnLoc = new Location(world, x, y,z, yaw, pitch);
                //Sets it in the world data - plugin will not use that data, just for other ones knowing where it is.
                world.setSpawnLocation(newSpawnLoc);
                player.sendMessage(ChatColor.GREEN + "World spawn location has been set!");
                return true;
            }
            return false;
        }
        return false;
    }
}
