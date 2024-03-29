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
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class OhneeEssentials extends JavaPlugin {

    private WarpConfigHelper warpConfigHelper = new WarpConfigHelper(this);
    private Toml tomlWarpsConfig;
    private Json jsonWarpsConfig;
    private Yaml ymlWarpsConfig;
    private Toml settings = new Toml("settings", getDataFolder().toString());
    private Json worldData = new Json("worlddata", getDataFolder().toString());
    private Integer fileType;
    private Permission vPerm;

    public Permission vPerm(){
        return vPerm;
    }

    public Toml settings() {
        return settings;
    }

    public Json worldData() {
        return worldData;
    }

    public Toml tomlWarps() {
        return tomlWarpsConfig;
    }

    public Json jsonWarps() {
        return jsonWarpsConfig;
    }

    public Yaml yamlWarps() {
        return ymlWarpsConfig;
    }

    public Integer fileUse() {
        return fileType;
    }

    private Plugin pl;

    public Plugin plugin() {
        return pl;
    }

    //Maps
    private HashMap<Player, Long> coolmap = new HashMap<>();
    private HashMap<String, Location> warpMap = new HashMap<>();
    private HashMap<UUID, String> tpah = new HashMap<>();

    public HashMap<UUID, String> tp() {
        return tpah;
    }

    public HashMap<Player, Long> cMap() {
        return coolmap;
    }

    public HashMap<String, Location> wMap() {
        return warpMap;
    }

    //Playtime
    private HashMap<UUID, Long> playime = new HashMap<>();

    public HashMap<UUID, Long> pTime() {
        return playime;
    }

    //Last location holder
    private HashMap<UUID, Location> lastLoc = new HashMap<>();

    public HashMap<UUID, Location> lLoc() {
        return lastLoc;
    }

    public List<String> safeBlocks;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        pl = this;
        //Enabling metrics
        Metrics metrics = new Metrics(this);
        //vPerm = (Permission) getServer().getPluginManager().getPlugin("Vault");
        setupPermissions();


        //Setting defaults!
        settings.setDefault("PluginSettings.Warp.toml", false);
        settings.setDefault("PluginSettings.Warp.yaml", false);
        settings.setDefault("PluginSettings.Warp.json", true);
        //settings.setDefault("PluginSettings.Warp.mysql", false);
        settings.setDefault("PluginSettings.WildTP.cooldown", 60);
        settings.setDefault("PluginSettings.WildTP.countdown", 3);
        settings.setDefault("PluginSettings.WildTP.Radius.maxX", 10000);
        settings.setDefault("PluginSettings.WildTP.Radius.minX", -10000);
        settings.setDefault("PluginSettings.WildTP.Radius.maxZ", 10000);
        settings.setDefault("PluginSettings.WildTP.Radius.minZ", -10000);
        List<String> defaltSafeBlocks = Arrays.asList("GRASS", "STONE", "SNOW", "SNOW_LAYER", "SAND", "ICE", "CLAY", "TERRACOTTA");
        settings.setDefault("PluginSettings.WildTP.SafeBlocks", defaltSafeBlocks);
        settings.setDefault("PluginSettings.Teleportation.Tp.TimeToRespond", 30);
        List<String> defaultGroups = Arrays.asList("admin:10", "mod:10", "vip:5", "regular:3", "default:1");
        settings.setDefault("PluginSettings.Homes.LimitPrGroup", defaultGroups);
        //Custom message file
        new MessageHelper(this); // <-- This will be redone at some point, so everything will be customizable!

        //Setting warp file type.
        if (settings().getBoolean("PluginSettings.Warp.json")) {
            //JSON
            fileType = 1;
            jsonWarpsConfig = new Json("warps", getDataFolder().toString());
        } else if (settings().getBoolean("PluginSettings.Warp.toml")) {
            //Toml
            fileType = 2;
            tomlWarpsConfig = new Toml("warps", getDataFolder().toString());
        } else if (settings().getBoolean("PluginSettings.Warp.yaml")) {
            //Yaml
            fileType = 3;
            ymlWarpsConfig = new Yaml("warps", getDataFolder().toString());
        }

        warpConfigHelper.warpLoad();
        safeBlocks = settings.getStringList("PluginSettings.WildTP.SafeBlocks");

        registerEvents();
        registerCommands();
    }

    public void onDisable() {

    }

    private void registerCommands() {
        //Commands
        this.getCommand("Wild").setExecutor(new Wild(this));
        this.getCommand("ohnee").setExecutor(new Ohnee(this));
        this.getCommand("Tp").setExecutor(new Tp(this));
        this.getCommand("Tphere").setExecutor(new Tphere(this));
        this.getCommand("Tpa").setExecutor(new Tpa(this));
        this.getCommand("Tpahere").setExecutor(new Tpahere(this));
        this.getCommand("Tpaccept").setExecutor(new Tpaccept(this));
        this.getCommand("Tpdeny").setExecutor(new Tpdeny(this));
        this.getCommand("Back").setExecutor(new Back(this));
        this.getCommand("Weather").setExecutor(new Weather());
        this.getCommand("Time").setExecutor(new Time(this));
        this.getCommand("Warp").setExecutor(new Warp(this));
        this.getCommand("Setwarp").setExecutor(new Setwarp(this));
        this.getCommand("Delwarp").setExecutor(new Delwarp(this));
        this.getCommand("Gamemode").setExecutor(new Gamemode(this));
        this.getCommand("Setspawn").setExecutor(new Setspawn(this));
        this.getCommand("Spawn").setExecutor(new Spawn(this));
        this.getCommand("sethome").setExecutor(new Sethome(this));
        this.getCommand("home").setExecutor(new Home(this));
        this.getCommand("delhome").setExecutor(new Delhome(this));
    }

    private void registerEvents() {
        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuitEvent(this), this);
        this.getServer().getPluginManager().registerEvents(new KeepXp(this), this);
        this.getServer().getPluginManager().registerEvents(new LastLocation(this), this);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        vPerm = rsp.getProvider();
        return vPerm != null;
    }
}