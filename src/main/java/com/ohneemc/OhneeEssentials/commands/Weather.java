package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Weather implements CommandExecutor {
    private OhneeEssentials plugin;
    public Weather(OhneeEssentials ohneeEssentials) {
        this.plugin = ohneeEssentials;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Weather") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            World world = player.getWorld();

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
                    world.setThundering(false);
                    world.setStorm(false);
                    world.setWeatherDuration(0);
                    player.sendMessage("Weather has been set to clear!");
                    return true;
                case "thunder":
                    world.setThundering(true);
                    world.setWeatherDuration(6000);
                    player.sendMessage("Is that... Thunder?!");
                    return true;
                case "rain":
                    if (duration != null){
                        int time = Integer.parseInt(duration.toLowerCase());
                        world.setWeatherDuration(time * 20);
                        player.sendMessage("Weather has been set to rain");
                    }else{
                        world.setStorm(true);
                        world.setWeatherDuration(6000);
                        player.sendMessage("Weather has been set to rain");
                    }
                    return true;
            }
            return false;
        }
        return false;
    }
}
