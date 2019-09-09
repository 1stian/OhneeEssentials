package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.StringCreater;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Me implements CommandExecutor {
    private OhneeEssentials plugin;
    public Me(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    StringCreater sc = new StringCreater();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Me") && sender instanceof Player){
            if (args.length > 0){
                plugin.getServer().broadcastMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + sender.getName() + " " + sc.messageCreater(args));
                return true;
            }else{
                sender.sendMessage(ChatColor.GREEN + "You must type a message! /me <message>");
                return true;
            }
        }
        return false;
    }
}
