package com.ohneemc.OhneeEssentials.events;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CustomInventory implements Listener {
    private Plugin plugin = OhneeEssentials.getPlugin(OhneeEssentials.class);

    public void inventorySee(Player player, Player target){
        PlayerInventory pi = target.getInventory();
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.DARK_GREEN + "Invsee");
        ItemStack[] iStack = pi.getContents();
        inv.setContents(iStack);
        player.openInventory(inv);
    }

    public void newFirstJoinInventory(Player player){
        Inventory i = plugin.getServer().createInventory(null, 27, ChatColor.DARK_GREEN + "Choose your destiny!");
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        if (emptyMeta != null) {
            emptyMeta.setDisplayName(" ");
        }
        empty.setItemMeta(emptyMeta);

        ItemStack survival = new ItemStack(Material.DIAMOND_SWORD, 1);
        ItemMeta oMeta = survival.getItemMeta();
        if (oMeta != null) {
            oMeta.setDisplayName(ChatColor.YELLOW + "Survival");
        }
        survival.setItemMeta(oMeta);

        ItemStack creative = new ItemStack(Material.DARK_OAK_PLANKS, 1);
        ItemMeta nMeta = creative.getItemMeta();
        if (nMeta != null) {
            nMeta.setDisplayName(ChatColor.RED + "Creative");
        }
        creative.setItemMeta(nMeta);

        i.setItem(0,empty);
        i.setItem(1,empty);
        i.setItem(2,empty);
        i.setItem(3,empty);
        i.setItem(4,empty);
        i.setItem(5,empty);
        i.setItem(6,empty);
        i.setItem(7,empty);
        i.setItem(8,empty);
        i.setItem(9,empty);
        i.setItem(10,empty);
        i.setItem(11,empty);
        i.setItem(12, survival);
        i.setItem(13,empty);
        i.setItem(14, creative);
        i.setItem(15,empty);
        i.setItem(16,empty);
        i.setItem(17,empty);
        i.setItem(18,empty);
        i.setItem(19,empty);
        i.setItem(20,empty);
        i.setItem(21,empty);
        i.setItem(22,empty);
        i.setItem(23,empty);
        i.setItem(24,empty);
        i.setItem(25,empty);
        i.setItem(26,empty);
        player.openInventory(i);
    }

    public void newWildInventory(Player player){
        Inventory i = plugin.getServer().createInventory(null, 27, ChatColor.DARK_GREEN + "WildTP - Choose world below.");
        ItemStack empty = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta emptyMeta = empty.getItemMeta();
        if (emptyMeta != null) {
            emptyMeta.setDisplayName(" ");
        }
        empty.setItemMeta(emptyMeta);

        ItemStack overworld = new ItemStack(Material.SUNFLOWER, 1);
        ItemMeta oMeta = overworld.getItemMeta();
        if (oMeta != null) {
            oMeta.setDisplayName(ChatColor.YELLOW + "Overworld.");
        }
        overworld.setItemMeta(oMeta);

        ItemStack nether = new ItemStack(Material.NETHER_STAR, 1);
        ItemMeta nMeta = nether.getItemMeta();
        if (nMeta != null) {
            nMeta.setDisplayName(ChatColor.RED + "Nether.");
        }
        nether.setItemMeta(nMeta);

        i.setItem(0,empty);
        i.setItem(1,empty);
        i.setItem(2,empty);
        i.setItem(3,empty);
        i.setItem(4,empty);
        i.setItem(5,empty);
        i.setItem(6,empty);
        i.setItem(7,empty);
        i.setItem(8,empty);
        i.setItem(9,empty);
        i.setItem(10,empty);
        i.setItem(11,empty);
        i.setItem(12, overworld);
        i.setItem(13,empty);
        i.setItem(14, nether);
        i.setItem(15,empty);
        i.setItem(16,empty);
        i.setItem(17,empty);
        i.setItem(18,empty);
        i.setItem(19,empty);
        i.setItem(20,empty);
        i.setItem(21,empty);
        i.setItem(22,empty);
        i.setItem(23,empty);
        i.setItem(24,empty);
        i.setItem(25,empty);
        i.setItem(26,empty);
        player.openInventory(i);
    }
}
