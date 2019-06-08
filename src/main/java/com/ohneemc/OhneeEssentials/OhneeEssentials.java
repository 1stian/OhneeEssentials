package com.ohneemc.OhneeEssentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class OhneeEssentials extends JavaPlugin {

    public FileConfiguration config = this.getConfig();

    @Override
    public void onEnable() {
        //Setting and getting config
        config.options().copyDefaults(true);
        this.saveConfig();

        this.reloadConfig();

        //Register events class
        this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);

        //Commands
        this.getCommand("wild").setExecutor(new CommandClass(this));
    }

    public void onDisable() {

    }
}