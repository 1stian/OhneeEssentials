package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.events.Quit;
import com.ohneemc.OhneeEssentials.events.Join;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyEvents implements Listener {

    private OhneeEssentials plugin;

    MyEvents(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        new Join(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        new Quit(e.getPlayer());
    }
}
