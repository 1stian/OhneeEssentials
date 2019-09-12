package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.resources.InvCreator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InvListener implements Listener {
    private OhneeEssentials plugin;
    public InvListener(OhneeEssentials plugin){
        this.plugin = plugin;

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private InvCreator InvCreator;
    public InvListener(InvCreator invCreator){
        this.InvCreator = invCreator;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        String title = e.getInventory().getType().getDefaultTitle();
        if (title.equals(InvCreator.inventory_name)){
            e.setCancelled(true);
            if(e.getCurrentItem() == null){
                return;
            }
            if (title.equals(InvCreator.inventory_name)){
                InvCreator.invItemClicked((Player) e.getWhoClicked(), e.getSlot(), e.getCurrentItem(), e.getInventory());
            }
        }
    }
}
