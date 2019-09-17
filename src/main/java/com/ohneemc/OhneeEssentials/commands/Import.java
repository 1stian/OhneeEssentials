package com.ohneemc.OhneeEssentials.commands;

import com.earth2me.essentials.Essentials;
import com.earth2me.essentials.UserData;
import com.ohneemc.OhneeEssentials.OhneeEssentials;
import de.leonhard.storage.Json;
import de.leonhard.storage.Yaml;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            CommandSender sender = commandSender;
            return runImport(sender);
        }
        return false;
    }

    private boolean runImport(CommandSender player){
        if (plugin.getServer().getPluginManager().getPlugin("Essentials") != null){
            Essentials ess = (Essentials) plugin.getServer().getPluginManager().getPlugin("Essentials");

            File path = new File(plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
            File[] files = path.listFiles();
            if (files != null){
                for (File i : files){
                    String[] s1 = i.toString().split("\\.");
                    String s2 = s1[0].substring(0, s1[0].length() - 1);
                    String clean = s2+s1[1]+".yml";

                    Yaml getName = new Yaml(i.getName().split("\\.")[0], plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
                    String pName = getName.getString("lastAccountName");
                    UserData uData = ess.getOfflineUser(pName);

                    Yaml essentialsdata = new Yaml(i.getName().split("\\.")[0], plugin.getServer().getWorldContainer().getAbsolutePath() + "/plugins/Essentials/userdata/");
                    File already = new File(plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/" + i.getName().split("\\.")[0] + ".json");

                    if (!already.exists() && uData != null){
                        for (String h : uData.getHomes()){
                            Json userHome = new Json(i.getName().split("\\.")[0], plugin.getDataFolder().getAbsolutePath() + "/userdata/homes/");
                            userHome.set(h + ".x", essentialsdata.getDouble("homes." + h + ".x"));
                            userHome.set(h + ".y", essentialsdata.getDouble("homes." + h + ".y"));
                            userHome.set(h + ".z", essentialsdata.getDouble("homes." + h + ".z"));
                            userHome.set(h + ".pitch", essentialsdata.getFloat("homes." + h + ".pitch"));
                            userHome.set(h + ".yaw", essentialsdata.getFloat("homes." + h + ".yaw"));
                            userHome.set(h + ".world", essentialsdata.getString("homes." + h + ".world"));

                            plugin.getServer().getLogger().info("Successfully imported home: "+ h + " from essentials, for player: " + pName+"("+ uData.getConfigUUID() + ")");
                        }
                    }
                }
                plugin.getServer().getLogger().info("Import is now done!");
                return true;
            }else{
                plugin.getServer().getLogger().warning("No homes to import from essentials.");
                return true;
            }
        }else{
            player.sendMessage("Essentials not found, aborting import.");
            return true;
        }
    }
}
