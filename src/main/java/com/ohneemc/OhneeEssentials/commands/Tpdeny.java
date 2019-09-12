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

    private Tpa tpa;
    public Tpdeny(Tpa tpa){this.tpa = tpa;}

    private Tpahere Tpahere;
    public Tpdeny(Tpahere Tpahere){this.Tpahere = Tpahere;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpdeny") && sender instanceof Player){
            if (plugin.tp().containsKey(((Player) sender).getUniqueId())){
                Player player = ((Player) sender).getPlayer();
                String extract = plugin.tp().get(player != null ? player.getName() : null).toString();
                String[] split = extract.split(",");

                Player target = plugin.getServer().getPlayer(split[0]);

                if (split.length == 2) {
                    plugin.getServer().getScheduler().cancelTask(Tpahere.resp);
                } else {
                    plugin.getServer().getScheduler().cancelTask(tpa.resp);
                }

                plugin.tp().remove(player.getUniqueId());

                if (target != null) {
                    plugin.getServer().getScheduler().cancelTask(tpa.resp);
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
