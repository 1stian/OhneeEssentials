package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.events.JoinQuitEvent;
import de.leonhard.storage.Json;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Playtime implements CommandExecutor {
    private OhneeEssentials ohnee;
    public Playtime(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    private Json userdata;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Playtime")){
            if (sender instanceof Player){
                Player player = ((Player) sender).getPlayer();
                if (args.length < 1){
                    player.sendMessage("Your playtime: " + getPlaytime(player));
                }else if (args.length == 1){
                    player.sendMessage(args[0] + "'s playtime: " + getPlaytime(ohnee.getServer().getPlayer(args[0])));
                }
            }else{

            }
        }
        return false;
    }

    private String getPlaytime(Player player){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date currentTime = new Date(System.currentTimeMillis());
        if (ohnee.getServer().getPlayer(player.getUniqueId()).isOnline()){
            userdata = new Json(player.getUniqueId().toString(), ohnee.getDataFolder().getAbsoluteFile() +
                    "/userdata/" + player.getPlayer().getUniqueId().toString() + ".json");

            String first = userdata.get("PlayerStats.FirstSeen").toString();
            String now = format.format(currentTime);

            Date d1 = null;
            Date d2 = null;

            try{
                d2 = format.parse(now);
                d1 = format.parse(first);

                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);

                String pTime = diffDays + " days, " + diffHours + " hours, " + diffMinutes + " minutes, " + diffSeconds + "seconds.";

                return pTime;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            userdata = new Json(player.getServer().getOfflinePlayer(player.getUniqueId()).toString(), ohnee.getDataFolder().getAbsoluteFile() +
                    "/userdata/" + player.getServer().getOfflinePlayer(player.getUniqueId()).toString() + ".json");
        }
        return "0";
    }
}
