package com.ohneemc.OhneeEssentials;

import org.bukkit.plugin.java.JavaPlugin;

public class OhneeEssentials extends JavaPlugin {

    @Override
    public void onEnable(){

        //Register events class
        this.getServer().getPluginManager().registerEvents(new MyEvents(this), this);

        //Commands
        this.getCommand("wild").setExecutor(new CommandClass(this));
    }

    public void onDisable(){

    }
}
