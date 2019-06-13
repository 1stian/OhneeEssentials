package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.Ohnee;
import com.ohneemc.OhneeEssentials.commands.Wild;
import com.ohneemc.OhneeEssentials.events.JoinQuitEvent;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class OhneeEssentials extends JavaPlugin {

    private FileConfiguration config = this.getConfig();
    private OhneeEssentials pl = this;

    //Maps
    public static final HashMap<Player, Long> coolmap = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        pl = this;
        //Setting and getting config
        config.options().copyDefaults(true);
        this.saveConfig();

        this.reloadConfig();

        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuitEvent(this), this);

        //Commands
        this.getCommand("Wild").setExecutor(new Wild(this));
        this.getCommand("ohnee").setExecutor(new Ohnee(this));

        //Create message file and load it
        new MessageHelper(pl);
    }

    public void onDisable() {

    }
}