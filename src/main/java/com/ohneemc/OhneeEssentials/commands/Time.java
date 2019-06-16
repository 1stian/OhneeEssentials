package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Time implements CommandExecutor {
    private  OhneeEssentials plugin;
    public Time(OhneeEssentials ohneeEssentials) {
        this.plugin = ohneeEssentials;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Time") && commandSender instanceof Player){
            Player player = ((Player) commandSender).getPlayer();
            World world = player.getWorld();

            String type = strings[0].toLowerCase();

            switch (type){
                case "day":
                    world.setTime(8000);
                    player.sendMessage("Time set to day!");
                    return true;
                case "night":
                    world.setTime(20000);
                    player.sendMessage("Time set to night!");
                    return true;
            }
            return false;
        }
        return false;
    }
}
