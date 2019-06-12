package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.entity.Player;

public class Quit {

    private OhneeEssentials plugin;

    public Quit(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    public Quit(Player player) {

    }
}
