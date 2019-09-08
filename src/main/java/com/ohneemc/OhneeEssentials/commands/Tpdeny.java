package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpdeny implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Tpdeny (OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    private Tpa tpa;
    public Tpdeny(Tpa tpa){this.tpa = tpa;}

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
                    ohnee.getServer().getScheduler().cancelTask(tpa.resp);
                    target.sendMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN + " denied your request.");
                }
                return true;
            }else{
                sender.sendMessage(ChatColor.GREEN + "You have no pending requests.");
                return true;
            }
        }
        return false;
    }
}
