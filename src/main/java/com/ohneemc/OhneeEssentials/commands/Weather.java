package com.ohneemc.OhneeEssentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Weather implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Weather") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            World world = player != null ? player.getWorld() : null;

            if (strings.length < 1){
                return false;
            }

            String type = strings[0].toLowerCase();
            String duration = null;

            if (strings.length > 1){
                duration = strings[1];
            }

            switch (type){
                case "clear":
                    if (world != null) {
                        world.setThundering(false);
                        world.setStorm(false);
                        world.setWeatherDuration(0);
                        player.sendMessage(ChatColor.GREEN + "Weather has been set to clear!");
                        return true;
                    }
                    return false;
                case "thunder":
                    if (world != null) {
                        world.setThundering(true);
                        world.setWeatherDuration(6000);
                        player.sendMessage(ChatColor.GREEN + "Is that... Thunder?!");
                        return true;
                    }
                    return false;
                case "rain":
                    if (world != null) {
                        if (duration != null){
                            int time = Integer.parseInt(duration.toLowerCase());
                            world.setWeatherDuration(time * 20);
                            player.sendMessage(ChatColor.GREEN + "Weather has been set to rain");
                            return true;
                        }else{
                            world.setStorm(true);
                            world.setWeatherDuration(6000);
                            player.sendMessage(ChatColor.GREEN + "Weather has been set to rain");
                            return true;
                        }
                    }
                    return false;
            }
            return false;
        }
        return false;
    }
}
