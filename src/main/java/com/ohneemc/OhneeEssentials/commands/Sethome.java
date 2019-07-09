package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class Sethome implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Sethome(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    private Json userdata;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Sethome") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            String UUID;
            if (player != null) {
                UUID = player.getUniqueId().toString();
            }else{
                return false;
            }

            userdata = new Json(UUID, ohnee.getDataFolder().getAbsoluteFile() +
                    "/userdata/" + UUID + ".json");

            List<String> existHomes = userdata.getStringList("PlayerInfo.homes");

            if (args.length < 1){
                return setHome(player, "home");
            }else if (args.length == 1){
                if (!existHomes.contains(args[0].toLowerCase())){
                    return setHome(player, args[0].toLowerCase());
                }else{
                    sender.sendMessage("Home with that name already exist. Please delete it first.");
                    return true;
                }
            }else{
                return false;
            }

        }
        return false;
    }

    private boolean setHome(Player player, String homeName){
        double x;
        double y;
        double z;
        float pitch;
        float yaw;
        String world;

        if (player != null){
            x = player.getLocation().getX();
            y = player.getLocation().getY();
            z = player.getLocation().getZ();
            pitch = player.getLocation().getPitch();
            yaw = player.getLocation().getYaw();
            world = player.getWorld().toString();
        }else{
            return false;
        }

        try{
            userdata.set("PlayerInfo.homes.", homeName);
            userdata.set("PlayerInfo.homes." + homeName + ".x", x);
            userdata.set("PlayerInfo.homes." + homeName + ".y", y);
            userdata.set("PlayerInfo.homes." + homeName + ".z", z);
            userdata.set("PlayerInfo.homes." + homeName + ".pitch", pitch);
            userdata.set("PlayerInfo.homes." + homeName + ".yaw", yaw);
            userdata.set("PlayerInfo.homes." + homeName + ".world", world);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
