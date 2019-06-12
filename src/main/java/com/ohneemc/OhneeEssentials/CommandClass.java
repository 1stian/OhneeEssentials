package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.wild;
import com.ohneemc.OhneeEssentials.resources.chunkHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

    private OhneeEssentials plugin;
    public CommandClass(OhneeEssentials plugin) {this.plugin = plugin;}

    private chunkHelper chunk;
    public CommandClass(chunkHelper chunk) {this.chunk = chunk;}

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Wild command
        if (command.getName().equalsIgnoreCase("wild")) {
            if (commandSender instanceof Player && commandSender.hasPermission("ohnee.wild")) {
                if (((Player) commandSender).getPlayer() != null) {
                    new wild(((Player) commandSender).getPlayer(), plugin, new chunkHelper());
                } else {
                    plugin.getLogger().warning("Player null... While using command /wild");
                }
                //new wild(((Player) commandSender).getPlayer());
                return true;
            }
        }
        return false;
    }
}
