package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.Wild;
import com.ohneemc.OhneeEssentials.resources.ChunkHelper;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandClass implements CommandExecutor {

    private OhneeEssentials plugin;
    public CommandClass(OhneeEssentials plugin) {this.plugin = plugin;}

    private ChunkHelper chunk;
    private CommandClass(ChunkHelper chunk) {this.chunk = chunk;}

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        //Wild command
        if (command.getName().equalsIgnoreCase("Wild")) {
            if (commandSender instanceof Player && commandSender.hasPermission("ohnee.Wild")) {
                if (((Player) commandSender).getPlayer() != null) {
                    new Wild(((Player) commandSender).getPlayer(), plugin, new ChunkHelper());
                } else {
                    plugin.getLogger().warning("Player null... While using command /Wild");
                }
                //new Wild(((Player) commandSender).getPlayer());
                return true;
            }
        }
        return false;
    }
}
