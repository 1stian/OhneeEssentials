package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Gamemode (OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Gamemode") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();

            if (args.length == 2){
                Player target = ohnee.getServer().getPlayer(args[1]);

                if (args[0].equals("2") || args[0].toLowerCase().equals("spectator")){
                    if (target != null) {
                        target.setGameMode(GameMode.SPECTATOR);
                        target.sendMessage(ChatColor.GREEN + "Your gamemode has been changed: " + ChatColor.GOLD + "spectator");
                        sender.sendMessage(ChatColor.GOLD + " " + target.getName() + ChatColor.GREEN + " has been set to spectator.");
                        return true;
                    }
                }
                if (args[0].equals("1") || args[0].toLowerCase().equals("creative")){
                    if (target != null) {
                        target.setGameMode(GameMode.CREATIVE);
                        target.sendMessage(ChatColor.GREEN + "Your gamemode has been changed: " + ChatColor.GOLD + "creative");
                        sender.sendMessage(ChatColor.GOLD + " " + target.getName() + ChatColor.GREEN + " has been set to creative.");
                        return true;
                    }
                }
                if (args[0].equals("0") || args[0].toLowerCase().equals("survival")){
                    if (target != null) {
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(ChatColor.GREEN + "Your gamemode has been changed: " + ChatColor.GOLD + "survival");
                        sender.sendMessage(ChatColor.GOLD + " " + target.getName() + ChatColor.GREEN + " has been set to survival.");
                        return true;
                    }
                }
                return false;
            }else if (args.length == 1){
                if (args[0].equals("2") || args[0].toLowerCase().equals("spectator")){
                    if (player != null) {
                        player.setGameMode(GameMode.SPECTATOR);
                        player.sendMessage(ChatColor.GREEN+"Gamemode has been changed to spectator.");
                        return true;
                    }
                }
                if (args[0].equals("1") || args[0].toLowerCase().equals("creative")){
                    if (player != null) {
                        player.setGameMode(GameMode.CREATIVE);
                        player.sendMessage(ChatColor.GREEN+"Gamemode has been changed to creative.");
                        return true;
                    }
                }
                if (args[0].equals("0") || args[0].toLowerCase().equals("survival")){
                    if (player != null) {
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage(ChatColor.GREEN+"Gamemode has been changed to survival.");
                        return true;
                    }
                }
                return false;
            }else{
                return false;
            }
        }
        return false;
    }
}
