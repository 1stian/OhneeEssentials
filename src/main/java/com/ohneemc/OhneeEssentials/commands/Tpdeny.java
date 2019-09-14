package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Tpdeny implements CommandExecutor {
    private OhneeEssentials plugin;
    public Tpdeny (OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpdeny") && sender instanceof Player){
            if (plugin.tp().containsKey(((Player) sender).getUniqueId())){
                Player player = ((Player) sender).getPlayer();
                String extract = plugin.tp().get(((Player) sender).getUniqueId());
                String[] split = extract.split(",");

                Player target = plugin.getServer().getPlayer(split[0]);

                if (split.length == 2) {
                    Tpa.cancelTask();
                } else {
                    Tpahere.cancelTask();
                }
                if (target != null && player != null) {
                    plugin.tp().remove(player.getUniqueId());
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
