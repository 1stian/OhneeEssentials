package com.ohneemc.OhneeEssentials.resources;

import com.google.common.base.Strings;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.IOException;
import java.util.Set;

public class WarpConfigHelper {
    private OhneeEssentials plugin;

    public WarpConfigHelper(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    public int taskId;

    public void warpSaver() {
        taskId = Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run() {
                saveFile();
                plugin.getLogger().info("Saved warps to file.");
            }
        }, 300L, 6000L).getTaskId();
    }

    public void warpLoad() {
        try {
            plugin.warpConfig().load(plugin.getWarpFile());

            Set<String> names = plugin.warpConfig().getKeys(false);
            if (!(names.isEmpty())){
                for (String warp : names) {
                    String n1 = warp.replaceAll("\\[", "");
                    String name = n1.replaceAll("\\]", "");
                    World world = plugin.getServer().getWorld(plugin.warpConfig().getString(name + ".world"));
                    double x = plugin.warpConfig().getDouble(name + ".x");
                    double y = plugin.warpConfig().getDouble(name + ".y");
                    double z = plugin.warpConfig().getDouble(name + ".z");
                    float pitch = plugin.warpConfig().getLong(name + ".pitch");
                    float yaw = plugin.warpConfig().getLong(name + ".yaw");

                    Location loc = new Location(world, x, y, z, pitch, yaw);
                    OhneeEssentials.warpMap.put(warp, loc);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void saveFile() {
        try {
            plugin.warpConfig().save(plugin.getWarpFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveMySql() {

    }
}
