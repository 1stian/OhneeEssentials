package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Spawn(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Spawn") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null) {
                World world = player.getWorld();

                double x = ohnee.worldData().getDouble(world.getName() + "Spawn.X");
                double y = ohnee.worldData().getDouble(world.getName() + "Spawn.Y");
                double z = ohnee.worldData().getDouble(world.getName() + "Spawn.Z");
                float yaw = ohnee.worldData().getFloat(world.getName() + "Spawn.Yaw");
                float pitch = ohnee.worldData().getFloat(world.getName() + "Spawn.Pitch");

                Location spawnLoc = new Location(world, x, y,z, yaw, pitch);

                player.teleport(spawnLoc);
                player.sendMessage(ChatColor.GREEN + "You've been teleported to spawn!");
                return true;
            }
            return false;
        }
        return false;
    }
}
