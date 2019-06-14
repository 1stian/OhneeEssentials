package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitEvent implements Listener {

    private OhneeEssentials plugin;
    public JoinQuitEvent(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    public JoinQuitEvent() {

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!plugin.coolmap.containsKey(e.getPlayer())){
            plugin.coolmap.put(e.getPlayer(), (System.currentTimeMillis() / 1000));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {

    }
}
