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

            userdata = new Json(UUID, ohnee.getDataFolder().getAbsolutePath() +
                    "/userdata/homes/");

            Set<String> existHomes = userdata.getKeySet();

            if (args.length < 1){
                return setHome(player, "home");
            }else if ((args.length == 1) && sender.hasPermission("ohnee.homes.multi")){
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
            world = player.getWorld().getName();
        }else{
            return false;
        }

        try{
            userdata.set(homeName + ".x", x);
            userdata.set(homeName + ".y", y);
            userdata.set(homeName + ".z", z);
            userdata.set(homeName + ".pitch", pitch);
            userdata.set(homeName + ".yaw", yaw);
            userdata.set(homeName + ".world", world);

            player.sendMessage("Home " + homeName + " has been set!");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
