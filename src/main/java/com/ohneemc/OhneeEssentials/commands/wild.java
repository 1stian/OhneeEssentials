package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.resources.chunkHelper;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class wild {

    public wild(final Player player, final Plugin plugin, final chunkHelper chunk) {
        try {
            //Debug lines
            //System.out.println("player var debug: " + player);
            //System.out.println("chunk var debug: " + chunk);
            //System.out.println("plugin var debug: " + plugin);

            plugin.getServer().getScheduler().runTask(plugin, new Runnable() {
                public void run() {
                    final Location tp = chunk.getLoc(player, plugin);
                    player.sendMessage("You will be teleported in 3 seconds! Hang on!");

                    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                        public void run() {
                            player.teleport(tp);
                        }

                    }, 60l);
                }
            });

        }catch (Exception ex){
            player.sendMessage("Something went wrong.... Please tell your server admin!");
            plugin.getLogger().warning(ex.toString());
        }
    }
}
