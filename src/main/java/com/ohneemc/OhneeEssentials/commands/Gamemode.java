package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
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
                        return true;
                    }
                }
                if (args[0].equals("1") || args[0].toLowerCase().equals("creative")){
                    if (target != null) {
                        target.setGameMode(GameMode.CREATIVE);
                        return true;
                    }
                }
                if (args[0].equals("0") || args[0].toLowerCase().equals("survival")){
                    if (target != null) {
                        target.setGameMode(GameMode.SURVIVAL);
                        return true;
                    }
                }
                return false;
            }else if (args.length == 1){
                if (args[0].equals("2") || args[0].toLowerCase().equals("spectator")){
                    if (player != null) {
                        player.setGameMode(GameMode.SPECTATOR);
                        return true;
                    }
                }
                if (args[0].equals("1") || args[0].toLowerCase().equals("creative")){
                    if (player != null) {
                        player.setGameMode(GameMode.CREATIVE);
                        return true;
                    }
                }
                if (args[0].equals("0") || args[0].toLowerCase().equals("survival")){
                    if (player != null) {
                        player.setGameMode(GameMode.SURVIVAL);
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
