package com.ohneemc.OhneeEssentials.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.UserData;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.UUID;

public class Import implements CommandExecutor {

    private OhneeEssentials plugin;
    public Import(OhneeEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (command.getName().equalsIgnoreCase("Import")) {
            return runImport();
        }
        return false;
    }

    private boolean runImport(){
        Essentials ess = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");

        File path = new File(plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
        File[] files = path.listFiles();
        if (files != null){
            for (File i : files){
                plugin.getServer().getLogger().info(i.toString());
                String[] s1 = i.toString().split("\\.");
                String s2 = s1[0].substring(0, s1[0].length() - 1);
                String clean = s2+s1[1]+".yml";
                plugin.getServer().getLogger().info(clean);

                Yaml getName = new Yaml(i.getName() + ".yml", plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
                String pName = getName.getString("lastAccountName");
                plugin.getServer().getLogger().info(pName);
                UserData uData = ess.getOfflineUser(pName);

                Yaml essentialsdata = new Yaml(i.getName(), plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
                Json userHome = new Json(i.getName(), plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/");
                File already = new File(plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/" + i.getName());

                if (!already.exists()){
                    for (String h : uData.getHomes()){
                        userHome.set(h + ".x", essentialsdata.getDouble("homes." + h + ".x"));
                        userHome.set(h + ".y", essentialsdata.getDouble("homes." + h + ".y"));
                        userHome.set(h + ".z", essentialsdata.getDouble("homes." + h + ".z"));
                        userHome.set(h + ".pitch", essentialsdata.getFloat("homes." + h + ".pitch"));
                        userHome.set(h + ".yaw", essentialsdata.getFloat("homes." + h + ".yaw"));
                        userHome.set(h + ".world", essentialsdata.getString("homes." + h + ".world"));

                        plugin.getServer().getLogger().info("Successfully imported homes from essentials, for player: " + pName+"("+ i + ")");
                    }
                }
            }
            plugin.getServer().getLogger().info("Import is now done!");
            return true;
        }else{
            plugin.getServer().getLogger().warning("No homes to import from essentials.");
            return true;
        }
    }
}
