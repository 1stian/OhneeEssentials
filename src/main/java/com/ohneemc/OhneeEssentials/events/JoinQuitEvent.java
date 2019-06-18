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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinQuitEvent implements Listener {

    private OhneeEssentials plugin;
    public JoinQuitEvent(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    Json userdata;

    public JoinQuitEvent() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!plugin.coolmap.containsKey(e.getPlayer())){
            plugin.coolmap.put(e.getPlayer(), (System.currentTimeMillis() / 1000));
        }

        File uData = new File(plugin.getDataFolder() + "userdata");
        if(!uData.exists()){
            try{
                uData.mkdir();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        File first = new File(plugin.getDataFolder() + "userdata/" + e.getPlayer().getUniqueId().toString());
        if (!first.exists()){
            firstJoin(e.getPlayer());
            userdata = new Json(e.getPlayer().getUniqueId().toString(), plugin.getDataFolder().toString() + "userdata/");
        }else{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            userdata = new Json(e.getPlayer().getUniqueId().toString(), plugin.getDataFolder().toString() + "userdata/");
            userdata.set("PlayerInfo.Name", e.getPlayer().getName());
            userdata.set("PlayerInfo.UUID", e.getPlayer().getUniqueId().toString());
            userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }

    public void firstJoin(Player player){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        userdata.set("PlayerInfo.Name", player.getPlayer().getName());
        userdata.set("PlayerInfo.UUID", player.getPlayer().getUniqueId().toString());
        userdata.set("PlayerStats.FirstSeen", formatter.format(date));
        userdata.set("PlayerStats.Playtime", 0);
        userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
    }

    public void userLeave(Player player){

    }
}
