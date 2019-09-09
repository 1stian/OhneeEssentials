package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.UserData;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Fly implements CommandExecutor {
    private OhneeEssentials plugin;
    public Fly(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    private UserData uData = new UserData();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Fly") && sender instanceof Player){
            if (args.length == 1){
                Player target = plugin.getServer().getPlayer(args[0]);
                if (target != null) {
                    boolean fly = uData.setFly((target), plugin.getDataFolder().getAbsolutePath() + "/userdata");
                    if (!fly){
                        sender.sendMessage(ChatColor.GREEN + " Fly enabled for " + ChatColor.GOLD + target.getName());
                        target.setAllowFlight(true);
                        target.setFlying(true);
                        return true;
                    }else{
                        sender.sendMessage(ChatColor.GREEN + " Fly disabled for " + ChatColor.GOLD + target.getName());
                        target.setAllowFlight(false);
                        target.setFlying(false);
                        return true;
                    }
                }else{
                    sender.sendMessage(ChatColor.GREEN + " Player not found!");
                }
            }else{
                boolean fly = uData.setFly(((Player) sender).getPlayer(), plugin.getDataFolder().getAbsolutePath() + "/userdata");
                if (!fly){
                    sender.sendMessage(ChatColor.GREEN + " Fly enabled");
                    ((Player) sender).setAllowFlight(true);
                    ((Player) sender).setFlying(true);
                    return true;
                }else{
                    sender.sendMessage(ChatColor.GREEN + " Fly disabled");
                    ((Player) sender).setAllowFlight(false);
                    ((Player) sender).setFlying(false);
                    return true;
                }
            }
        }
        return false;
    }
}
