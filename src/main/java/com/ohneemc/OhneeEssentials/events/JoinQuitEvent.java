package com.ohneemc.OhneeEssentials.events;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.UserData;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
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

    private OhneeEssentials plugin;
    private UserData uData;
    public JoinQuitEvent(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    private Json userdata;

    public Json uData(){return userdata;}
    private com.ohneemc.OhneeEssentials.resources.UserData PlayerData = new com.ohneemc.OhneeEssentials.resources.UserData();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        //e.setJoinMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());
        plugin.getServer().broadcastMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());

        ScoreboardManager m = Bukkit.getScoreboardManager();
        Scoreboard b = m.getNewScoreboard();

        Objective o = b.registerNewObjective("Board", "");
        o.setDisplaySlot(DisplaySlot.SIDEBAR);
        o.setDisplayName(ChatColor.DARK_AQUA + "OhneeMC");

        Score oPlayer = o.getScore(ChatColor.WHITE + "Online: " + plugin.getServer().getOnlinePlayers().size());
        oPlayer.setScore(1);
        //player.setScoreboard(b);

        normalJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        //e.setQuitMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
        plugin.getServer().broadcastMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
        userLeave(e.getPlayer());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void normalJoin(Player e){
        plugin.pTime().put(Objects.requireNonNull(e.getPlayer()).getUniqueId(), System.currentTimeMillis());

        if (!plugin.cMap().containsKey(e.getPlayer())){
            plugin.cMap().put(e.getPlayer().getUniqueId(), System.currentTimeMillis() / 1000);
        }

        System.out.print("DataFolder: " + plugin.getDataFolder().getAbsoluteFile());
        File uData = new File(plugin.getDataFolder().getAbsoluteFile() + "/userdata");
        if(!uData.exists()){
            try{
                uData.mkdir();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        File first = new File(plugin.getDataFolder().getAbsoluteFile() + "/userdata/" + e.getPlayer().getUniqueId().toString() + ".json");
        if (!first.exists()){
            userdata = new Json(e.getPlayer().getUniqueId().toString(), plugin.getDataFolder().getAbsoluteFile() + "/userdata");
            firstJoin(e.getPlayer());
        }else{
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
            Date date = new Date(System.currentTimeMillis());

            userdata = new Json(e.getPlayer().getUniqueId().toString(), plugin.getDataFolder().getAbsoluteFile() + "/userdata");
            userdata.set("PlayerInfo.Name", e.getPlayer().getName());
            userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));
            if (e.getPlayer().hasPermission("plugin.fly.safe")){
                e.getPlayer().setFlying(PlayerData.getFly(e.getPlayer(), plugin.getDataFolder().getAbsolutePath() + "/userdata"));
            }
        }
    }

    private void firstJoin(Player player){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());

        userdata.set("PlayerInfo.Name", player.getName());
        userdata.set("PlayerInfo.UUID", player.getUniqueId().toString());
        userdata.set("PlayerInfo.Banned", false);
        userdata.set("PlayerInfo.Muted", false);
        userdata.set("PlayerInfo.Fly", false);
        userdata.set("PlayerStats.FirstSeen", formatter.format(date));
        userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));

        essentialsImport(player);
    }

    private void userLeave(Player player){
        userdata.update();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        userdata.set("PlayerStats.LastSeen", formatter.format(date));
        plugin.pTime().remove(player.getUniqueId());
    }

    private void essentialsImport(Player player){
        String UUID = player.getUniqueId().toString();
        Essentials ess = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");

        File p = new File(plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/"+UUID+".yml");
        if (p.exists()){
            UserData uData = ess.getUser(player);
            Yaml essentialsdata = new Yaml(UUID, plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");

            for (String h : uData.getHomes()){
                Json userHome = new Json(UUID, plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/");
                String homeName = h;
                userHome.set(homeName + ".x", essentialsdata.getDouble("homes." + homeName + ".x"));
                userHome.set(homeName + ".y", essentialsdata.getDouble("homes." + homeName + ".y"));
                userHome.set(homeName + ".z", essentialsdata.getDouble("homes." + homeName + ".z"));
                userHome.set(homeName + ".pitch", essentialsdata.getFloat("homes." + homeName + ".pitch"));
                userHome.set(homeName + ".yaw", essentialsdata.getFloat("homes." + homeName + ".yaw"));
                userHome.set(homeName + ".world", essentialsdata.getString("homes." + homeName + ".world"));
            }
            plugin.getServer().getLogger().info("Successfully imported homes from essentials, for player: " + player.getName()+"("+ UUID + ")");
        }
    }
}
