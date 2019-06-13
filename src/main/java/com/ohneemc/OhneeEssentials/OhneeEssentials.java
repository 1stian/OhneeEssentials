package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.Wild;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class OhneeEssentials extends JavaPlugin {

    private FileConfiguration config = this.getConfig();

    private OhneeEssentials pl;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        pl = this;
        //Setting and getting config
        config.options().copyDefaults(true);
        this.saveConfig();

        this.reloadConfig();

        //Register events class
        this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);

        //Commands
        this.getCommand("Wild").setExecutor(new Wild(this));
    }

    public void onDisable() {

    }
}