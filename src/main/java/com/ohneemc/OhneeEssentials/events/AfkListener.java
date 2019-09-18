package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class AfkListener implements Listener {
    private static OhneeEssentials plugin;
    public AfkListener(OhneeEssentials plugin){
        AfkListener.plugin = plugin;
        checkAfk();
    }

    private HashMap<UUID, Long> notMovedSince = new HashMap<>();

    private static HashMap<UUID, Boolean> playerAFk = new HashMap<>();
    public static HashMap<UUID, Boolean> isAfk(){return playerAFk;}

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        notMovedSince.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        cancelAfk(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (plugin.afkCancelOnChat){
            notMovedSince.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            cancelAfk(event.getPlayer());
        }
    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        notMovedSince.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        notMovedSince.remove(event.getPlayer().getUniqueId());
    }

    private void checkAfk(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()){
                    if (notMovedSince.containsKey(player.getUniqueId())){
                        int minutes = plugin.afkTimer * 60 * 1000;
                        long passed = System.currentTimeMillis() - minutes;
                        long oldTime = notMovedSince.get(player.getUniqueId());
                        if (oldTime < passed){
                            if (!isAfk().containsKey(player.getUniqueId())) {
                                setAfk(player);
                            }
                        }
                    }
                }
            }
        }, 40L, 100L);
    }

    public static void cancelAfk(Player player){
        if (isAfk().containsKey(player.getUniqueId())){
            isAfk().remove(player.getUniqueId());
            player.setSleepingIgnored(false);
            plugin.chat().setPlayerSuffix(player, "");
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN+" is no longer AFK.");
        }
    }

    public static void setAfk(Player player){
        isAfk().put(player.getUniqueId(), true);
        player.setSleepingIgnored(true);
        plugin.chat().setPlayerSuffix(player, ChatColor.ITALIC + "" + ChatColor.GRAY + " away");
        player.setDisplayName(player.getName() + ChatColor.ITALIC + "" + ChatColor.GRAY + " away");
        player.setPlayerListName(player.getName() + ChatColor.ITALIC + "" + ChatColor.GRAY + " away");
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN+" is now AFK.");
    }

    public int Difference(int a, int b){
        return Math.abs(a-b);
    }
}
