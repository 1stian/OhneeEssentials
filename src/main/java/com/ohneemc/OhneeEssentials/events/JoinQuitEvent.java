package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.commands.Time;
import de.leonhard.storage.Json;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JoinQuitEvent implements Listener {

    private OhneeEssentials Ohnee;
    public JoinQuitEvent(OhneeEssentials plugin) {
        this.Ohnee = plugin;
    }

    private Json userdata;

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        normalJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        userLeave(e.getPlayer());
    }

    private void normalJoin(Player e){
        Ohnee.pTime().put(e.getPlayer().getUniqueId(), System.currentTimeMillis());

        if (!Ohnee.coolmap.containsKey(e.getPlayer())){
            Ohnee.coolmap.put(e.getPlayer(), (System.currentTimeMillis() / 1000));
        }

        System.out.print("DataFolder: " + Ohnee.getDataFolder().getAbsoluteFile());
        File uData = new File(Ohnee.getDataFolder().getAbsoluteFile() + "/userdata");
        if(!uData.exists()){
            try{
                uData.mkdir();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        File first = new File(Ohnee.getDataFolder().getAbsoluteFile() + "/userdata/" + e.getPlayer().getUniqueId().toString());
        if (!first.exists()){
            userdata = new Json(e.getPlayer().getUniqueId().toString(), Ohnee.getDataFolder().getAbsoluteFile() + "/userdata");
            firstJoin(e.getPlayer());
        }else{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            userdata = new Json(e.getPlayer().getUniqueId().toString(), Ohnee.getDataFolder().getAbsoluteFile() + "/userdata");
            userdata.set("PlayerInfo.Name", e.getPlayer().getName());
            userdata.set("PlayerInfo.UUID", e.getPlayer().getUniqueId().toString());
            userdata.set("PlayerInfo.Banned", false);
            userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
        }
    }

    private void firstJoin(Player player){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        userdata.set("PlayerInfo.Name", player.getName());
        userdata.set("PlayerInfo.UUID", player.getUniqueId().toString());
        userdata.set("PlayerStats.FirstSeen", formatter.format(date));
        userdata.set("PlayerStats.Playtime", 0);
        userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
    }

    public void userLeave(Player player){
        Long joinedTime = (Long) Ohnee.pTime().get(player.getUniqueId());
        Long timeLeave = System.currentTimeMillis();
        Long summed = timeLeave - joinedTime;
        Long currentPlaytime = userdata.getLong("PlayerStats.Playtime");
        Long ready = currentPlaytime + summed;

        userdata.set("PlayerStats.Playtime", ready);

        Ohnee.pTime().remove(player.getUniqueId());

        //String playtime = String.format("%02d Days, %02d Min, %02d Sec",
        //        TimeUnit.MILLISECONDS.toDays(summed),
        //        TimeUnit.MILLISECONDS.toMinutes(summed),
        //        TimeUnit.MILLISECONDS.toSeconds(summed));


    }
}
