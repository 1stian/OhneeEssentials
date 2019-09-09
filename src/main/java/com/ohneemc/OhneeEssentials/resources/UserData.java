package com.ohneemc.OhneeEssentials.resources;

import de.leonhard.storage.Json;
import org.bukkit.entity.Player;


public class UserData {

    public boolean setFly(Player player, String path){
        Json uData = new Json(player.getUniqueId() + ".yml", path);
        boolean getFly = uData.getBoolean("PlayerInfo.Fly");

        if (getFly){
            uData.set("PlayerInfo.Fly", false);
            return false;
        }else{
            uData.set("PlayerInfo.Fly", true);
            return true;
        }
    }

    public boolean getFly(Player player, String path){
        Json uData = new Json(player.getUniqueId() + ".yml", path);
        boolean getFly = uData.getBoolean("PlayerInfo.Fly");

        if (getFly){
            return true;
        }else{
            return false;
        }
    }
}
