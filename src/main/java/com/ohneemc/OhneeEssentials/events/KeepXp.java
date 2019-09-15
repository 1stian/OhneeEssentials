package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.UUID;

public class KeepXp implements Listener {
    private OhneeEssentials plugin;
    public KeepXp(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    private HashMap<UUID, Integer> level = new HashMap<>();

    @EventHandler
    public void playerDied(PlayerDeathEvent e){
        if (e != null){
            Player player = e.getEntity().getPlayer();
            if (player != null && player.hasPermission("Ohnee.keepxp")){
                int lev = player.getLevel();
                if (e.getEntity().getKiller() == null){
                    e.setDroppedExp(0);
                }
                level.put(player.getUniqueId(), lev);
            }
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();

        if (level.containsKey(player.getUniqueId())){
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.setLevel(level.get(player.getUniqueId()));
                level.remove(player.getUniqueId());
            }, 20L);
        }
    }
}
