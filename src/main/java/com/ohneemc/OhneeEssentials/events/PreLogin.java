package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.scoreboard.Score;

public class PreLogin implements Listener {
    private OhneeEssentials plugin;
    public PreLogin(OhneeEssentials plugin){this.plugin = plugin;}

    @EventHandler
    public void LoginListen(AsyncPlayerPreLoginEvent e){
        Score score = plugin.o.getScore("" + Bukkit.getOnlinePlayers().size());
        int previousScore = score.getScore();
        plugin.setSb().put(e.getUniqueId(), true);

        plugin.s.resetScores("" + Bukkit.getOnlinePlayers().size());

        score = plugin.o.getScore("" + Bukkit.getOnlinePlayers().size() + 1);
        score.setScore(previousScore);
    }
}
