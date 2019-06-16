package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Ohnee implements CommandExecutor {
    private OhneeEssentials plugin;
    public Ohnee(OhneeEssentials ohneeEssentials) {
        this.plugin = ohneeEssentials;
    }

    private MessageHelper msg;
    public Ohnee(MessageHelper msg){
        this.msg = msg;
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
                            File customMessages = new File(plugin.getDataFolder(), "messages.txt");
                            plugin.getConfig();
                            msg.loadMessages(String.valueOf(customMessages));
                            if (player != null) player.sendMessage("Config has been reloaded!");
                            break;

                        case "version":
                            String ver = plugin.getDescription().getVersion();
                            if (player != null) player.sendMessage("OhneeEssentials version: " + ver);
                            break;
                    }

                    return true;
                }

                return false;

            } else {
                plugin.getLogger().warning("Player null... While using command /Wild");
                return false;
            }
        }
        return false;
    }
}
