package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpaccept implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Tpaccept(OhneeEssentials ohnee) {
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpaccept") && sender instanceof Player) {
            if (ohnee.tp().containsKey(((Player) sender).getUniqueId())){
                String extract = ohnee.tp().get(((Player) sender).getUniqueId());
                Player player = ((Player) sender).getPlayer();
                String[] split = extract.split(",");
                if (split.length == 2) {

                } else {

                }

                Player target = ohnee.getServer().getPlayer(split[0]);

                player.teleport(target);

                if (split.length == 2) {
                    target.teleport(player);
                } else {
                    player.teleport(target);
                }

                ohnee.tp().remove(player.getUniqueId());
                target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " accepted your request.");
                return true;
            }else{
                sender.sendMessage(ChatColor.GREEN + "You have no pending requests.");
                return true;
            }
        }
        return false;
    }
}
