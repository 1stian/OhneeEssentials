package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class Ohnee implements CommandExecutor {
    private OhneeEssentials plugin;
    public Ohnee(OhneeEssentials ohneeEssentials) {
        this.plugin = ohneeEssentials;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Ohnee")) {
            if (((Player) commandSender).getPlayer() != null) {
                Player player = ((Player) commandSender).getPlayer();
                if (!(strings.length < 1)){
                    System.out.print(strings[0]);
                    switch (strings[0]){
                        case "reload":
                            plugin.getConfig();
                            if (player != null) player.sendMessage("Config has been reloaded");
                            break;

                        case "version":
                            String ver = plugin.getDescription().getVersion();
                            if (player != null) player.sendMessage("OhneeEssentials version: " + ver);
                            break;
                    }

                    return true;

                }


            } else {
                plugin.getLogger().warning("Player null... While using command /Wild");
                return false;
            }
            //new Wild(((Player) commandSender).getPlayer());
        }
        return false;
    }
}
