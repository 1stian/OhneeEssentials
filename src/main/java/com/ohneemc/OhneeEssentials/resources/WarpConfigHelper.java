package com.ohneemc.OhneeEssentials.resources;

import com.ohneemc.OhneeEssentials.OhneeEssentials;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Set;

public class WarpConfigHelper {
    private OhneeEssentials plugin;

    public WarpConfigHelper(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    public void warpLoad() {
        try {
            switch (plugin.fileUse()){
                case 1:
                    //JSON
                    loadJson();
                    break;
                case 2:
                    //Toml
                    loadToml();
                    break;
                case 3:
                    //Yaml
                    loadYaml();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadJson(){
        Set<String> names = plugin.jsonWarps().getKeySet();

        if (!(names.isEmpty())){
            for (String warp : names) {
                String n1 = warp.replaceAll("\\[", "");
                String name = n1.replaceAll("]", "");
                World world = plugin.getServer().getWorld(plugin.jsonWarps().getString(name + ".world"));
                double x = plugin.jsonWarps().getDouble(name + ".x");
                double y = plugin.jsonWarps().getDouble(name + ".y");
                double z = plugin.jsonWarps().getDouble(name + ".z");
                Float pitch = plugin.jsonWarps().getFloat(name + ".pitch");
                Float yaw = plugin.jsonWarps().getFloat(name + ".yaw");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                plugin.wMap().put(warp, loc);
            }
        }
    }

    private void loadToml(){
        Set<String> names = plugin.tomlWarps().getKeySet();

        if (!(names.isEmpty())){
            for (String warp : names) {
                String n1 = warp.replaceAll("\\[", "");
                String name = n1.replaceAll("]", "");
                World world = plugin.getServer().getWorld(plugin.tomlWarps().getString(name + ".world"));
                double x = plugin.tomlWarps().getDouble(name + ".x");
                double y = plugin.tomlWarps().getDouble(name + ".y");
                double z = plugin.tomlWarps().getDouble(name + ".z");
                Float pitch = plugin.tomlWarps().getFloat(name + ".pitch");
                Float yaw = plugin.tomlWarps().getFloat(name + ".yaw");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                plugin.wMap().put(warp, loc);
            }
        }
    }

    private void loadYaml(){
        Set<String> names = plugin.yamlWarps().getKeySet();

        if (!(names.isEmpty())){
            for (String warp : names) {
                String n1 = warp.replaceAll("\\[", "");
                String name = n1.replaceAll("]", "");
                World world = plugin.getServer().getWorld(plugin.yamlWarps().getString(name + ".world"));
                double x = plugin.yamlWarps().getDouble(name + ".x");
                double y = plugin.yamlWarps().getDouble(name + ".y");
                double z = plugin.yamlWarps().getDouble(name + ".z");
                Float pitch = plugin.yamlWarps().getFloat(name + ".pitch");
                Float yaw = plugin.yamlWarps().getFloat(name + ".yaw");

                Location loc = new Location(world, x, y, z, yaw, pitch);
                plugin.wMap().put(warp, loc);
            }
        }
    }
}
