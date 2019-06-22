package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
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
            String warpName = null;

            if (strings.length < 1) {
                return false;
            } else {
                warpName = strings[0].toLowerCase();
            }

            if (!OhneeEssentials.warpMap.containsKey(warpName)) {
                player.sendMessage("A warp with the name: " + warpName + " does not exist!");
                return true;
            } else {
                try {
                    OhneeEssentials.warpMap.remove(warpName);

                    if (plugin.settings().getBoolean("PluginSettings.Warp.json")){
                        //JSON
                        plugin.jsonWarps().set(warpName, null);
                    }else if (plugin.settings().getBoolean("PluginSettings.Warp.toml")){
                        //Toml
                        plugin.tomlWarps().set(warpName, null);
                    }else if (plugin.settings().getBoolean("PluginSettings.Warp.yaml")) {
                        //Yaml
                        plugin.yamlWarps().set(warpName, null);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                    player.sendMessage("Couldn't delete warp, contact your server admins.");
                }
                player.sendMessage("Warp: " + warpName + " has been removed!");
                return true;
            }
        }
        return false;
    }
}
