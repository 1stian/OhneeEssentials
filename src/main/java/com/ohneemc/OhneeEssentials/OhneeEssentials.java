package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.*;
import com.ohneemc.OhneeEssentials.events.JoinQuitEvent;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import com.ohneemc.OhneeEssentials.resources.WarpConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;

public class OhneeEssentials extends JavaPlugin {

    private FileConfiguration config = this.getConfig();
    private File warpFile = new File(getDataFolder(), "warps.yml");
    private FileConfiguration warpConfig = YamlConfiguration.loadConfiguration(warpFile);
    private WarpConfigHelper warpConfigHelper = new WarpConfigHelper(this);

    public FileConfiguration warpConfig() {
        return warpConfig;
    }

    public File getWarpFile() {
        return warpFile;
    }

    //Maps
    public static HashMap<Player, Long> coolmap = new HashMap<>();
    public static HashMap<String, Location> warpMap = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        //Enabling metrics
        //Metrics metrics = new Metrics(this);

        //Setting and getting config
        config.options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
        //Custom message file
        new MessageHelper(this);
        //Custom warpFile
        if (!warpFile.exists()) {
            saveResource("warps.yml", false);
        }

        warpConfigHelper.warpLoad();
        //warpConfigHelper.warpSaver();

        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuitEvent(), this);

        //Commands
        this.getCommand("Wild").setExecutor(new Wild(this));
        this.getCommand("ohnee").setExecutor(new Ohnee(this));
        this.getCommand("Tp").setExecutor(new Tp(this));
        this.getCommand("Tphere").setExecutor(new Tphere(this));
        this.getCommand("Weather").setExecutor(new Weather(this));
        this.getCommand("Time").setExecutor(new Time(this));
        this.getCommand("Warp").setExecutor(new Warp());
        this.getCommand("Setwarp").setExecutor(new Setwarp(this));
        this.getCommand("Delwarp").setExecutor(new Delwarp(this));

        //Testing jenkins1
    }

    public void onDisable() {
        //Saving waprs in map.
        this.getLogger().info("Stopping warp warpConfigHelper.");
        Bukkit.getScheduler().cancelTask(warpConfigHelper.taskId);
        this.getLogger().info("Saving warps from memory.");
        warpConfigHelper.saveFile();
    }
}