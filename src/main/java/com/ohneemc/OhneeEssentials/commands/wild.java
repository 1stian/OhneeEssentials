package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.resources.chunkHelper;
import com.ohneemc.OhneeEssentials.resources.intRandomizer;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class wild {

    private OhneeEssentials plugin;
    private intRandomizer random;
    private chunkHelper chunk;

    public wild(OhneeEssentials plugin) {
        this.plugin = plugin;
    }
    public wild(intRandomizer rand) {this.random = rand;}
    public wild(chunkHelper chunk) {this.chunk = chunk;}

    public wild(Player player, Plugin plugin) {
        try {
            System.out.println("chunk var debug: " + chunk);
            Location tp = chunk.getLoc(player);
            player.teleport(tp);
        }catch (Exception ex){
            player.sendMessage("Something went wrong.... Please tell your server admin!");
            plugin.getLogger().warning(ex.toString());
        }
    }
}
