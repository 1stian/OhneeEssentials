package com.ohneemc.OhneeEssentials.resources;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TpHelper {

    public boolean Tp(Plugin plugin, String p1, String p2){
        try{
            Player one = plugin.getServer().getPlayer(p1);
            Player two = plugin.getServer().getPlayer(p2);
            if (one != null && two != null){
                one.teleport(two.getLocation());
                one.sendMessage("You've been teleported to: " + two);
                return true;
            }
            return false;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return false;
    }
}
