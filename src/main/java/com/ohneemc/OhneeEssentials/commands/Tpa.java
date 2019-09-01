package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Tpa  implements CommandExecutor {
    private int TimeToRespond;

    private OhneeEssentials ohnee;
    public Tpa (OhneeEssentials ohnee)
    {
        this.ohnee = ohnee;
        this.TimeToRespond = ohnee.settings().getInt("PluginSettings.Teleportation.Tp.TimeToRespond");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Tpa") && sender instanceof Player){
            UUID targetID;
            Player target;

            if (args.length < 1){
                return false;
            }else if (ohnee.getServer().getPlayer(args[0].toLowerCase()) != null){
                targetID = ohnee.getServer().getPlayer(args[0]).getUniqueId();
                target= ohnee.getServer().getPlayer(targetID);
                if (!ohnee.tp().containsKey(targetID)){
                    String toMap = sender.getName() + "," + System.currentTimeMillis();
                    ohnee.tp().put(targetID, toMap);
                    sender.sendMessage(ChatColor.GREEN + "Teleport request sent!");
                    target.sendMessage(ChatColor.GOLD + sender.getName() + ChatColor.GREEN + " has sent you a teleport request - /tpaccept or /tpadeny");

                    Bukkit.getScheduler().scheduleSyncDelayedTask(ohnee, new Runnable() {
                        @Override
                        public void run() {
                            sender.sendMessage(ChatColor.GOLD + target.getName() + ChatColor.GREEN + " didn't respond in time. Request removed.");
                            target.sendMessage(ChatColor.GREEN + "Time ran out, request removed.");
                            ohnee.tp().remove(targetID);
                        }
                    }, TimeToRespond * 20L);
                    return true;
                }else{
                    sender.sendMessage(ChatColor.GOLD + args[0] + ChatColor.GREEN + " already has a pending request.");
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.GREEN + "Couldn't find player");
                return true;
            }
        }

        return false;
    }
}
