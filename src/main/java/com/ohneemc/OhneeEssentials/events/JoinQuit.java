package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.CreateCustomItem;
import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class JoinQuit implements Listener {
    private OhneeEssentials plugin;
    public JoinQuit(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    private Json userdata;

    public Json uData(){return userdata;}
    private com.ohneemc.OhneeEssentials.resources.UserData PlayerData = new com.ohneemc.OhneeEssentials.resources.UserData();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.getServer().broadcastMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());
        plugin.PlayerScoreboard(e.getPlayer());
        normalJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.getServer().broadcastMessage(ChatColor.RED + "[-] " + ChatColor.GRAY + e.getPlayer().getName());
        userLeave(e.getPlayer());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void normalJoin(Player e){
        plugin.pTime().put(Objects.requireNonNull(e.getPlayer()).getUniqueId(), System.currentTimeMillis());

        if (!plugin.cMap().containsKey(e.getPlayer().getUniqueId())){
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

        CreateCustomItem item = new CreateCustomItem();
        item.giveFirstJoinItems(player);
        //essentialsImport(player);
    }

    private void userLeave(Player player){
        userdata.update();
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        userdata.set("PlayerStats.LastSeen", formatter.format(date));
        plugin.pTime().remove(player.getUniqueId());
    }

    private void essentialsImport(Player player){
        /**
        String UUID = player.getUniqueId().toString();
        if (plugin.getServer().getPluginManager().getPlugin("Essentials") != null){
            Essentials ess = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");

            File p = new File(plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/"+UUID+".yml");
            if (p.exists()){
                UserData uData = null;
                if (ess != null) {
                    uData = ess.getUser(player);
                }
                Yaml essentialsdata = new Yaml(UUID, plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");

                if (uData != null) {
                    for (String h : uData.getHomes()){
                        Json userHome = new Json(UUID, plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/");
                        userHome.set(h + ".x", essentialsdata.getDouble("homes." + h + ".x"));
                        userHome.set(h + ".y", essentialsdata.getDouble("homes." + h + ".y"));
                        userHome.set(h + ".z", essentialsdata.getDouble("homes." + h + ".z"));
                        userHome.set(h + ".pitch", essentialsdata.getFloat("homes." + h + ".pitch"));
                        userHome.set(h + ".yaw", essentialsdata.getFloat("homes." + h + ".yaw"));
                        userHome.set(h + ".world", essentialsdata.getString("homes." + h + ".world"));
                    }
                }
                plugin.getServer().getLogger().info("Successfully imported homes from essentials, for player: " + player.getName()+"("+ UUID + ")");
            }
        }
         **/
    }
}
