package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.UUID;

public class AfkListener implements Listener {
    private OhneeEssentials plugin;
    public AfkListener(OhneeEssentials plugin){
        this.plugin = plugin;
        checkAfk();
    }

    private HashMap<UUID, Long> notMovedSince = new HashMap<>();
    public HashMap<UUID, Long> afkPlayer(){return notMovedSince;}

    private HashMap<UUID, Boolean> playerAFk = new HashMap<>();
    public HashMap<UUID, Boolean> isAfk(){return playerAFk;}

    @EventHandler
    public void onMove(PlayerMoveEvent event){
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        notMovedSince.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
        cancelAfk(event.getPlayer());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event){
        if (plugin.afkCancelOnChat){
            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            notMovedSince.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
            cancelAfk(event.getPlayer());
        }
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

    private void cancelAfk(Player player){
        if (isAfk().containsKey(player.getUniqueId())){
            isAfk().remove(player.getUniqueId());
            player.setDisplayName(player.getName());
            player.setPlayerListName(player.getName());
            Bukkit.getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN+" is no longer AFK.");
        }
    }

    private void setAfk(Player player){
        isAfk().put(player.getUniqueId(), true);
        player.setDisplayName(player.getName() + ChatColor.ITALIC + "" + ChatColor.GRAY + " away");
        player.setPlayerListName(player.getName() + ChatColor.ITALIC + "" + ChatColor.GRAY + " away");
        Bukkit.getServer().broadcastMessage(ChatColor.GOLD + player.getName() + ChatColor.GREEN+" is now AFK.");
    }
}
