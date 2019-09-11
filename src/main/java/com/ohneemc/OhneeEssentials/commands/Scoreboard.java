package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Scoreboard implements CommandExecutor {
    private OhneeEssentials plugin;
    public Scoreboard(OhneeEssentials plugin){this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Scoreboard") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            if (player != null)if (plugin.getSb().get(player.getUniqueId())){
                plugin.setSb().put(player.getUniqueId(), false);
                sender.sendMessage(ChatColor.GREEN + "You've now " + ChatColor.RED + "disabled" + ChatColor.GREEN + " the scoreboard");
                player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
                return true;
            }else{
                plugin.setSb().put(player.getUniqueId(), true);
                sender.sendMessage(ChatColor.GREEN + "You've now " + ChatColor.GOLD + "enabled" + ChatColor.GREEN + " the scoreboard");
                return true;
            }
        }
        return false;
    }
}
