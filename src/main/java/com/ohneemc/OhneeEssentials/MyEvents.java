package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.events.quit;
import com.ohneemc.OhneeEssentials.events.join;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MyEvents implements Listener {

    private OhneeEssentials plugin;

    public MyEvents(OhneeEssentials plugin){
        plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        new join(e.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        new quit(e.getPlayer());
    }
}
