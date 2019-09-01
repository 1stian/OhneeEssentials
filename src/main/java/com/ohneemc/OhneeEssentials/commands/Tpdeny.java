package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpdeny implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Tpdeny (OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    private Maps maps;
    public Tpdeny(Maps maps){
        this.maps = maps;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpdeny") && sender instanceof Player){
            if (ohnee.tp().containsKey(((Player) sender).getUniqueId())){
                Player player = ((Player) sender).getPlayer();
                String extract = ohnee.tp().get(player != null ? player.getName() : null).toString();
                String[] split = extract.split(",");

                Player target = ohnee.getServer().getPlayer(split[0]);

                ohnee.tp().remove(player.getUniqueId());
                if (target != null) {
                    target.sendMessage(player.getName() + " denied your request.");
                }
                return true;
            }else{
                sender.sendMessage("You have no pending requests.");
                return true;
            }
        }
        return false;
    }
}
