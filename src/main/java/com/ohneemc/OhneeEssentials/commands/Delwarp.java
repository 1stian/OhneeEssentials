package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delwarp implements CommandExecutor {
    private OhneeEssentials plugin;
    public Delwarp(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Delwarp") && commandSender instanceof Player) {
            Player player = ((Player) commandSender).getPlayer();
            String warpName;

            if (strings.length < 1) {
                return false;
            } else {
                warpName = strings[0].toLowerCase();
            }

            if (!plugin.wMap().containsKey(warpName)) {
                if (player != null) {
                    player.sendMessage(ChatColor.GREEN + "A warp with the name: " + warpName + " does not exist!");
                    return true;
                }
                return false;
            } else {
                try {
                    plugin.wMap().remove(warpName);

                    switch (plugin.fileUse()){
                        case 1:
                            //JSON
                            plugin.jsonWarps().remove(warpName);
                            break;
                        case 2:
                            //Toml
                            plugin.tomlWarps().removeKey(warpName);
                            break;
                        case 3:
                            //Yaml
                            plugin.yamlWarps().remove(warpName);
                            break;
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + "Couldn't delete warp, contact your server admins.");
                    }
                }
                if (player != null) {
                    player.sendMessage(ChatColor.GREEN + "Warp: " + warpName + " has been removed!");
                }
                return true;
            }
        }
        return false;
    }
}
