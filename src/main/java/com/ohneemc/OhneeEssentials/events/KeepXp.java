package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class KeepXp implements Listener {
    private OhneeEssentials plugin;
    public KeepXp(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void playerDied(PlayerDeathEvent e){
        if (e != null){
            Player player = e.getEntity().getPlayer();
            if (player.hasPermission("Ohnee.keepxp")){
                float pXp = player.getTotalExperience();
                if (e.getEntity().getKiller() == null){
                    e.setDroppedExp(0);
                }
                e.setNewTotalExp((int) pXp);
            }
        }
    }
}
