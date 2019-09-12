package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.TpHelper;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tp implements CommandExecutor {
    private OhneeEssentials plugin;
    public Tp(OhneeEssentials ohneeEssentials) {
        this.plugin = ohneeEssentials;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Tp") && commandSender instanceof Player){
            if (strings.length == 2){
                if (new TpHelper().Tp(plugin, strings[0], strings[1])){
                    commandSender.sendMessage(ChatColor.GREEN + "Players have been teleported.");
                    return true;
                }else{
                    commandSender.sendMessage(ChatColor.GREEN + "Couldn't find players specified.");
                }
            }

            if (!(strings.length < 1)) {
                if (new TpHelper().Tp(plugin, commandSender.getName(), strings[0])){
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
