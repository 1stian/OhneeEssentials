package com.ohneemc.OhneeEssentials.commands;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import com.ohneemc.OhneeEssentials.events.CustomInventory;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.util.HashMap;
import java.util.UUID;

public class Invsee implements CommandExecutor, Listener {
    private OhneeEssentials plugin;
    public Invsee(OhneeEssentials plugin){
        this.plugin = plugin;
    }

    private HashMap<UUID, String> openInv = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("Invsee") && sender instanceof Player){
            Player player = ((Player) sender).getPlayer();
            Player target;

            if (args.length == 1){
                target = plugin.getServer().getPlayer(args[0]);
                if (player != null && target != null) {
                    CustomInventory inv = new CustomInventory();
                    openInv.put(player.getUniqueId(), target.getName());
                    inv.inventorySee(player, target);
                    sender.sendMessage(ChatColor.GREEN + "Opened " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " inventory.");
                    return true;
                }else{
                    sender.sendMessage(ChatColor.GREEN + "Couldn't open " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " inventory.");
                    return true;
                }
            }else{
                sender.sendMessage(ChatColor.GREEN + "You must specify a player to open their inventory.");
                return true;
            }
        }
        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        //ClickType click = e.getClick();
        Inventory open = e.getClickedInventory();
        InventoryView title = e.getView();
        String invTitle = title.getTitle();

        String target = openInv.get(player.getUniqueId());

        if (open == null) {
            return;
        }
        if (invTitle.equalsIgnoreCase(ChatColor.DARK_GREEN + "Player: " + target)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory close = event.getInventory();
        InventoryView title = event.getView();
        String invTitle = title.getTitle();
        String target = openInv.get(player.getUniqueId());

        if (invTitle.equalsIgnoreCase(ChatColor.DARK_GREEN + "Player: " + target)) {
            openInv.remove(player.getUniqueId());
        }
    }
}
