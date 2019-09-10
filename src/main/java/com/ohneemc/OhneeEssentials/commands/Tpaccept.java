package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpaccept implements CommandExecutor {
    private OhneeEssentials plugin;
    public Tpaccept(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    private Tpa tpa;
    public Tpaccept(Tpa tpa){this.tpa = tpa;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpaccept") && sender instanceof Player) {
            if (plugin.tp().containsKey(((Player) sender).getUniqueId())){
                String extract = plugin.tp().get(((Player) sender).getUniqueId());
                Player player = ((Player) sender).getPlayer();
                String[] split = extract.split(",");

                Player target = plugin.getServer().getPlayer(split[0]);
                player.teleport(target);

                if (split.length == 2) {
                    target.teleport(player);
                } else {
                    player.teleport(target);
                }

                plugin.tp().remove(player.getUniqueId());
                plugin.getServer().getScheduler().cancelTask(tpa.resp);
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
