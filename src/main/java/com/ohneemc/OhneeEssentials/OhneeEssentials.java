package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.*;
import com.ohneemc.OhneeEssentials.events.JoinQuitEvent;
import com.ohneemc.OhneeEssentials.events.KeepXp;
import com.ohneemc.OhneeEssentials.events.LastLocation;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import com.ohneemc.OhneeEssentials.resources.WarpConfigHelper;
import de.leonhard.storage.Json;
import de.leonhard.storage.Toml;
import de.leonhard.storage.Yaml;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class OhneeEssentials extends JavaPlugin {

    private WarpConfigHelper warpConfigHelper = new WarpConfigHelper(this);
    private Toml tomlWarpsConfig;
    private Json jsonWarpsConfig;
    private Yaml ymlWarpsConfig;
    private Toml settings = new Toml("settings", getDataFolder().toString());

    public Toml settings() {return  settings;}
    public Toml tomlWarps() {return  tomlWarpsConfig;}
    public Json jsonWarps() {return  jsonWarpsConfig;}
    public Yaml yamlWarps() {return  ymlWarpsConfig;}

    private Plugin pl;
    public Plugin plugin(){return pl;}

    //Maps
    public static HashMap<Player, Long> coolmap = new HashMap<>();
    public static HashMap<String, Location> warpMap = new HashMap<>();

    //Playtime
    private HashMap<UUID, Date> playime= new HashMap<>();
    public HashMap pTime(){return  playime;}

    //Last location holder
    private HashMap<UUID, Location> lastLoc = new HashMap<>();
    public HashMap lLoc(){return  lastLoc;}

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        pl = this;
        //Enabling metrics
        //Metrics metrics = new Metrics(this);


        //Setting defaults!
        settings.setDefault("PluginSettings.Warp.toml", false);
        settings.setDefault("PluginSettings.Warp.yaml", false);
        settings.setDefault("PluginSettings.Warp.json", true);
        //settings.setDefault("PluginSettings.Warp.mysql", false);
        settings.setDefault("PluginSettings.Teleport.cooldown", 60);
        settings.setDefault("PluginSettings.Teleport.countdown", 3);
        settings.setDefault("PluginSettings.WildTP.Radius.maxX", 10000);
        settings.setDefault("PluginSettings.WildTP.Radius.minX", -10000);
        settings.setDefault("PluginSettings.WildTP.Radius.maxZ", 10000);
        settings.setDefault("PluginSettings.WildTP.Radius.minZ", -10000);
        List<String> defaltSafeBlocks = Arrays.asList("GRASS", "STONE", "SNOW", "SNOW_LAYER", "SAND");
        settings.setDefault("PluginSettings.WildTP.SafeBlocks", defaltSafeBlocks);
        //Custom message file
        new MessageHelper(this); // <-- This will be redone at some point, so everything will be customizable!
        //Custom warpFile(Which type of warp saving)
        if (settings.getBoolean("PluginSettings.Warp.json")){
            jsonWarpsConfig = new Json("warps", getDataFolder().toString());
        }else if (settings.getBoolean("PluginSettings.Warp.toml")){
            tomlWarpsConfig = new Toml("warps", getDataFolder().toString());
        }else if (settings.getBoolean("PluginSettings.Warp.yaml")){
            ymlWarpsConfig = new Yaml("warps",getDataFolder().toString());
        }

        warpConfigHelper.warpLoad();

        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuitEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new KeepXp(this), this);
        this.getServer().getPluginManager().registerEvents(new LastLocation(this), this);

        //Commands
        this.getCommand("Wild").setExecutor(new Wild(this));
        this.getCommand("ohnee").setExecutor(new Ohnee(this));
        this.getCommand("Tp").setExecutor(new Tp(this));
        this.getCommand("Tphere").setExecutor(new Tphere(this));
        this.getCommand("Back").setExecutor(new Back(this));
        this.getCommand("Weather").setExecutor(new Weather(this));
        this.getCommand("Time").setExecutor(new Time(this));
        this.getCommand("Warp").setExecutor(new Warp());
        this.getCommand("Setwarp").setExecutor(new Setwarp(this));
        this.getCommand("Delwarp").setExecutor(new Delwarp(this));

        //Test comment for jenkins/discord.
    }

    public void onDisable() {

    }
}