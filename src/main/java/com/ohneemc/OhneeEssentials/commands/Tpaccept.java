package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.Maps;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpaccept implements CommandExecutor {
    private OhneeEssentials ohnee;

    public Tpaccept(OhneeEssentials ohnee) {
        this.ohnee = ohnee;
    }

    private Maps maps;

    public Tpaccept(Maps maps) {
        this.maps = maps;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpaccept") && sender instanceof Player) {
            Player player = ((Player) sender).getPlayer();
            String extract = maps.tp().get(player.getName()).toString();
            String[] split = extract.split(",");
            if (split.length == 2) {

            } else {

            }

            Player target = ohnee.getServer().getPlayer(split[0]);
            Long timeReq = Long.valueOf(split[1]);
            Long timeCur = System.currentTimeMillis();

            player.teleport(target);

            if (split.length == 2) {
                target.teleport(player);
            } else {
                player.teleport(target);
            }

            maps.tp().remove(player);
            target.sendMessage(player.getName() + " accepted your request.");
            return true;
        }
        return false;
    }
}
