package com.ohneemc.OhneeEssentials.resources;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class InvCreator {
    public Inventory inv;
    public String inventory_name;
    public int inv_boxes = 4;
    public int rows = inv_boxes * 9;

    public ItemStack createItem(Inventory inv, Material materialName, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack(materialName, amount);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s : loreString){
            lore.add(s);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1 , item);
        return item;
    }

    public ItemStack createItemByte(Inventory inv, Material materialName, int byteId, int amount, int invSlot, String displayName, String... loreString){
        ItemStack item;
        List<String> lore = new ArrayList<>();

        item = new ItemStack(materialName, amount, (short) byteId);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        for (String s : loreString){
            lore.add(s);
        }
        meta.setLore(lore);
        item.setItemMeta(meta);
        inv.setItem(invSlot - 1 , item);
        return item;
    }

    public void initialize(){
        inventory_name = "§6§lWildTP";
        inv = Bukkit.createInventory(null, inv_boxes);
    }

    public Inventory WildTP (Player p){
        Inventory toReturn = Bukkit.createInventory(null, inv_boxes, inventory_name);

        createItem(inv, Material.GLOWSTONE_DUST, 1, 1, "Survival", "Teleport to a random location", "in the survival world.");

        toReturn.setContents(inv.getContents());
        return toReturn;
    }

    public void invItemClicked(Player p, int slot, ItemStack clicked, Inventory inv){
        if (clicked.getItemMeta().getDisplayName().equalsIgnoreCase("Survival")){
            p.sendMessage("You clicked survival!");
        }
    }
}

