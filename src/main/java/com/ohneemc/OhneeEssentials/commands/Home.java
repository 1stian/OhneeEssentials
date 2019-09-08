package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public class Home implements CommandExecutor {
    private Json userdata;

    private OhneeEssentials ohnee;
    public Home(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Home") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            String UUID;

            if (player != null){
                UUID = player.getUniqueId().toString();
            }else{
                sender.sendMessage("An error occurred, please report this to your server administrator. Code: H1");
                return false;
            }

            userdata = new Json(UUID, ohnee.getDataFolder().getAbsolutePath() +
                    "/userdata/homes/");

            Set<String> homes = userdata.getKeySet();

            if (args.length == 1){
                String name = args[0];
                if (userdata.contains(name)){
                    return teleport(player, name.toLowerCase());
                }else{
                    sender.sendMessage(ChatColor.GREEN + "Home does not exist.");
                    return true;
                }
            }else if (homes.size() > 1){
                String s1 = homes.toString();
                String s2 = s1.replaceAll("]", "");
                String name = s2.replaceAll("\\[", "");
                sender.sendMessage(ChatColor.GREEN + "Homes: " + name);
                return true;
            }else{
                if (userdata.contains("home")){
                    return teleport(player, "home");
                }else{
                    sender.sendMessage(ChatColor.GREEN + "You've not set any home yet. use /sethome");
                    return true;
                }
            }
        }
        return false;
    }

    private boolean teleport(Player player, String homeName){
        try{
            double x = userdata.getDouble(homeName + ".x");
            double y = userdata.getDouble(homeName + ".y");
            double z = userdata.getDouble(homeName + ".z");
            float pitch = userdata.getFloat(homeName + ".pitch");
            float yaw = userdata.getFloat(homeName + ".yaw");
            String world = userdata.getString(homeName + ".world");

            Location tp = new Location(ohnee.getServer().getWorld(world), x,y,z,yaw,pitch);
            player.teleport(tp);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
