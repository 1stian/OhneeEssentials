package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;

public class KeepXp implements Listener {
    private OhneeEssentials plugin;
    public KeepXp(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    private HashMap<Player, Integer> level = new HashMap<>();
    private HashMap<Player, Float> Xp = new HashMap<>();

    @EventHandler
    public void playerDied(PlayerDeathEvent e){
        if (e != null){
            Player player = e.getEntity().getPlayer();
            if (player != null && player.hasPermission("Ohnee.keepxp")){
                float exp = player.getExp();
                int lev = player.getLevel();
                if (e.getEntity().getKiller() == null){
                    e.setDroppedExp(0);
                }
                plugin.getLogger().info("Getting level: " + lev);
                level.put(player, lev);
                Xp.put(player, exp);
            }
        }
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e){
        Player player = e.getPlayer();

        if (level.containsKey(player)){
            plugin.getLogger().info("Setting level: " + level.get(player));
            plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                player.setLevel(level.get(player));
                level.remove(player);
                Xp.remove(player);
            }, 20L);
        }
    }
}
