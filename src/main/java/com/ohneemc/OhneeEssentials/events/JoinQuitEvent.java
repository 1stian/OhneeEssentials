package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class JoinQuitEvent implements Listener {

    private OhneeEssentials Ohnee;
    public JoinQuitEvent(OhneeEssentials plugin) {
        this.Ohnee = plugin;
    }

    private Json userdata;
    public Json uData(){return userdata;}

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Ohnee.getServer().broadcastMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());

        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();

        Objective o = b.registerNewObjective("Board", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.DARK_AQUA + "OhneeMC");

        Score oPlayer = o.getScore(ChatColor.WHITE + "Online: " + Ohnee.getServer().getOnlinePlayers().size());
        oPlayer.setScore(1);
        //player.setScoreboard(b);

        normalJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Ohnee.getServer().broadcastMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
        userLeave(e.getPlayer());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void normalJoin(Player e){
        Ohnee.pTime().put(Objects.requireNonNull(e.getPlayer()).getUniqueId(), System.currentTimeMillis());

        if (!Ohnee.cMap().containsKey(e.getPlayer())){
            Ohnee.cMap().put(e.getPlayer(), System.currentTimeMillis() / 1000);
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

        File first = new File(Ohnee.getDataFolder().getAbsoluteFile() + "/userdata/" + e.getPlayer().getUniqueId().toString() + ".json");
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
        userdata.set("PlayerInfo.Banned", false);
        userdata.set("PlayerStats.FirstSeen", formatter.format(date));
        userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
    }

    private void userLeave(Player player){
        userdata.update();

        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        Long joinedTime = Ohnee.pTime().get(player.getUniqueId());
        Long timeLeave = System.currentTimeMillis();
        Long summed = timeLeave - joinedTime;
        Long currentPlaytime = userdata.getLong("PlayerStats.Playtime");
        Long ready = currentPlaytime + summed;

        userdata.set("PlayerStats.LastSeen", formatter.format(date));

        Ohnee.pTime().remove(player.getUniqueId());
    }
}
