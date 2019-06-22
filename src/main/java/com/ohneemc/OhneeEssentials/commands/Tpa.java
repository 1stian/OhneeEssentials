package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpa  implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Tpa (OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    private Maps maps;
    public Tpa(Maps maps){
        this.maps = maps;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpa") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Player target;

            if (args.length < 1){
                return false;
            }else if (ohnee.getServer().getPlayer(args[0].toLowerCase()) != null){
                target = ohnee.getServer().getPlayer(args[0]);
                if (!maps.tp().containsKey(target)){
                    String toMap = player.getName() + "," + System.currentTimeMillis();
                    maps.tp().put(target, toMap);
                    player.sendMessage("Teleport request sent!");
                    target.sendMessage(player.getName() + " has sent you a teleport request - /tpaaccept or /tpadeny");
                    return true;
                }else{
                    player.sendMessage(target.getName() + " already has a pending request.");
                    return true;
                }
            }else{
                player.sendMessage("Couldn't find player");
                return true;
            }
        }

        return false;
    }
}
