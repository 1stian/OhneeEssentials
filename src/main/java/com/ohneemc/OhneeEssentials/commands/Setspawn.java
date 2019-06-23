package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Setspawn implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Setspawn(OhneeEssentials ohnee){
        this.ohnee = ohnee;
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

                ohnee.worldData().set(world.getName() + "Spawn.X", x);
                ohnee.worldData().set(world.getName() + "Spawn.Y", y);
                ohnee.worldData().set(world.getName() + "Spawn.Z", z);
                ohnee.worldData().set(world.getName() + "Spawn.Yaw", yaw);
                ohnee.worldData().set(world.getName() + "Spawn.Pitch", pitch);

                Location newSpawnLoc = new Location(world, x, y,z, yaw, pitch);
                //Sets it in the world data - plugin will not use that data, just for other ones knowing where it is.
                world.setSpawnLocation(newSpawnLoc);
                player.sendMessage("World spawn location has been set!");
                return true;
            }
            return false;
        }
        return false;
    }
}
