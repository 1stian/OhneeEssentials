package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.Wild;
import com.ohneemc.OhneeEssentials.events.Quit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyEvents implements Listener {

    private OhneeEssentials plugin;

    MyEvents(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    private Wild wild;

    public MyEvents(Wild wild) {
        this.wild = wild;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!plugin.coolmap.containsKey(e.getPlayer())){
            plugin.coolmap.put(e.getPlayer(), (System.currentTimeMillis() / 1000));
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        new Quit(e.getPlayer());
    }
}
