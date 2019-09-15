package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;

public class CreateCustomItem implements Listener {
    private Plugin plugin = OhneeEssentials.getPlugin(OhneeEssentials.class);

    public void giveFirstJoinItems(Player player){
        ItemStack item = new ItemStack(Material.COMPASS,1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GOLD +"Destiny");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.LIGHT_PURPLE + "Choose your destiny! Right or left click to open menu.");
        meta.setLore(lore);
    }

    public void createItem(Material material, String name, String... lore){

    }
}
