package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Delhome implements CommandExecutor {
    private Json userdata;

    private OhneeEssentials ohnee;
    public Delhome(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Delhome") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            String UUID;

            if (player != null){
                UUID = player.getUniqueId().toString();
            }else{
                sender.sendMessage("An error occurred, please report this to your server administrator. Code: H2");
                return false;
            }

            userdata = new Json(UUID, ohnee.getDataFolder().getAbsolutePath() +
                    "/userdata/homes/");

            if (args.length < 1){
                return delHome(player, "home");
            }else if (args.length == 1){
                String name = args[0];
                if (userdata.contains(name)){
                    return delHome(player, name.toLowerCase());
                }else{
                    sender.sendMessage(ChatColor.GREEN + "Home does not exist.");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean delHome(Player player, String homeName){
        try {
            userdata.removeKey(homeName);
            player.sendMessage(ChatColor.GOLD + homeName + ChatColor.GREEN + " has been deleted.");
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
