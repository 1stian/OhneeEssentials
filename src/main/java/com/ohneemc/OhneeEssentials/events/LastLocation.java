package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class LastLocation implements Listener {

    private OhneeEssentials ohnee;
    public LastLocation(OhneeEssentials ohnee){
        this.ohnee = ohnee;
    }

    @EventHandler
    public void lastLoc(PlayerTeleportEvent e){
        Player player = e.getPlayer();
        Location loc = player.getLocation();

        //Storing last location before teleport.
        ohnee.lLoc().put(player.getUniqueId(), loc);
    }
}
