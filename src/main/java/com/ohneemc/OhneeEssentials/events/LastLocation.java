package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class LastLocation implements Listener {

    private OhneeEssentials plugin;
    public LastLocation(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void lastLoc(PlayerTeleportEvent e){
        Player player = e.getPlayer();
        Location loc = e.getFrom();

        //Storing last location before teleport.
        plugin.lLoc().put(player.getUniqueId(), loc);
    }

    @EventHandler
    public void lastDeathLoc(PlayerDeathEvent e){
        Player player = e.getEntity().getPlayer();
        Location loc;
        if (player != null) {
            loc = player.getLocation();
            //Storing last death location.
            plugin.lLoc().put(player.getUniqueId(), loc);
        }
    }
}
