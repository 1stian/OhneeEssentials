package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class JoinQuit implements Listener {
    private OhneeEssentials plugin;
    public JoinQuit(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    private Json userdata;

    public Json uData(){return userdata;}
    private com.ohneemc.OhneeEssentials.resources.UserData PlayerData = new com.ohneemc.OhneeEssentials.resources.UserData();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        e.setJoinMessage("");
        plugin.getServer().broadcastMessage(ChatColor.GREEN + "[+] " + ChatColor.GRAY + e.getPlayer().getName());
        plugin.PlayerScoreboard(e.getPlayer());
        normalJoin(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage("");
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

            if (userdata.get("PlayerInfo.Gamemode") != null){
                GameMode gm = (GameMode.valueOf(userdata.getString("PlayerInfo.Gamemode")));
                if (gm == GameMode.CREATIVE){
                    e.setGameMode(gm);
                    e.setFlying(true);
                }
                if (gm == GameMode.SURVIVAL){
                    e.setGameMode(gm);
                    e.setFlying(false);
                }
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
        userdata.set("PlayerInfo.Gamemode", "SURVIVAL");
        userdata.set("PlayerStats.FirstSeen", formatter.format(date));
        userdata.set("PlayerStats.lastSessionStarted", formatter.format(date));

        Bukkit.broadcastMessage("Welcome, " + ChatColor.GOLD + player.getName() + " to OhneeMC!");
        giveStarterKit(player);

        //This is not ready yet.
        //CreateCustomItem item = new CreateCustomItem();
        //item.giveFirstJoinItems(player);

        //essentialsImport(player);
    }

    private void userLeave(Player player){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        userdata.set("PlayerStats.LastSeen", formatter.format(date));
        GameMode gm = player.getGameMode();
        userdata.set("PlayerInfo.Gamemode", gm);
        plugin.pTime().remove(player.getUniqueId());
    }

    private void giveStarterKit(Player player){
        List<String> items = plugin.kits().getStringList("Starter.items");

        for (String i : items){
            String[] split = i.split(":");
            String iName = split[0];
            int iAmount = Integer.valueOf(split[1]);
            Material item = Material.matchMaterial(iName);
            ItemStack stack;
            if (item != null){
                stack = new ItemStack(item, iAmount);
                player.getInventory().addItem(stack);
            }else{
                Bukkit.getLogger().warning("[Ohnee] There was a problem with the starter kit... Unrecognized item..");
            }
        }
        Bukkit.getLogger().info(player.getName() + " received the starter kit.");
    }

    private void essentialsImport(Player player){
        /*
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
         */
    }
}
