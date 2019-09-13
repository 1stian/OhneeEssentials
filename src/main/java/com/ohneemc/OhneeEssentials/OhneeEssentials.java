package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.*;
import com.ohneemc.OhneeEssentials.events.*;
import com.ohneemc.OhneeEssentials.resources.InvCreator;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import com.ohneemc.OhneeEssentials.resources.WarpConfigHelper;
import de.leonhard.storage.Json;
import de.leonhard.storage.Toml;
import de.leonhard.storage.Yaml;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class OhneeEssentials extends JavaPlugin {
    public boolean settingsLoaded = false;

    private WarpConfigHelper warpConfigHelper = new WarpConfigHelper(this);
    private Toml tomlWarpsConfig;
    private Json jsonWarpsConfig;
    private Yaml ymlWarpsConfig;
    private Toml settings = new Toml("settings", getDataFolder().toString());
    private Json worldData = new Json("worlddata", getDataFolder().toString());
    private Integer fileType;
    private Permission vPerm;
    private Economy eco;

    public Permission vPerm(){
        return vPerm;
    }
    public Economy eco(){return eco;}
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

    //Maps
    private HashMap<UUID, Long> coolmap = new HashMap<>();
    private HashMap<String, Location> warpMap = new HashMap<>();
    private HashMap<UUID, String> tpah = new HashMap<>();
    private HashMap<UUID, Boolean> sb = new HashMap<>();
    public HashMap<UUID, String> tp() {
        return tpah;
    }
    public HashMap<UUID, Long> cMap() {
        return coolmap;
    }
    public HashMap<String, Location> wMap() {
        return warpMap;
    }
    public HashMap<UUID, Boolean> getSb(){return sb;}
    public HashMap<UUID, Boolean> setSb(){return sb;}

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

    //Scoreboard
    public org.bukkit.scoreboard.Scoreboard s;
    public Objective o;

    //Wild
    public boolean wildLoaded = false;
    public List<String> UnsafeBlocks;
    public String wildWorld1;
    public String wildWorld2;
    public List<Material> materials = new ArrayList<>();
    public int maxX;
    public int minX;
    public int maxZ;
    public int minZ;
    public int countdown;
    public int cooldown;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        settingsLoaded = loadSettings();
        //Enabling metrics
        Metrics metrics = new Metrics(this);
        setupPermissions();
        setupEconomy();

        s = Bukkit.getScoreboardManager().getNewScoreboard();
        o = s.registerNewObjective("sidebar", "dummy");

        //Setting defaults!
        settings.setDefault("PluginSettings.Warp.toml", false);
        settings.setDefault("PluginSettings.Warp.yaml", false);
        settings.setDefault("PluginSettings.Warp.json", true);
        settings.setDefault("PluginSettings.WildTP.cooldown", 60);
        settings.setDefault("PluginSettings.WildTP.countdown", 3);
        settings.setDefault("PluginSettings.WildTP.Radius.maxX", 5000);
        settings.setDefault("PluginSettings.WildTP.Radius.minX", -5000);
        settings.setDefault("PluginSettings.WildTP.Radius.maxZ", 5000);
        settings.setDefault("PluginSettings.WildTP.Radius.minZ", -5000);
        List<String> defaltUnsafeBlocks = Arrays.asList("WATER", "LAVA", "AIR");
        settings.setDefault("PluginSettings.WildTP.UnsafeBlocks", defaltUnsafeBlocks);
        settings.setDefault("PluginSettings.WildTP.Worlds.world1", "world");
        settings.setDefault("PluginSettings.WildTP.Worlds.world2", "world_nether");
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
        this.getCommand("import").setExecutor(new Import(this));
        this.getCommand("me").setExecutor(new Me(this));
        this.getCommand("invsee").setExecutor(new Invsee(this));
        this.getCommand("fly").setExecutor(new Fly(this));
        this.getCommand("uuid").setExecutor(new Uuid(this));
        this.getCommand("scoreboard").setExecutor(new Scoreboard(this));
    }

    private void registerEvents() {
        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuit(this), this);
        this.getServer().getPluginManager().registerEvents(new KeepXp(this), this);
        this.getServer().getPluginManager().registerEvents(new LastLocation(this), this);
        this.getServer().getPluginManager().registerEvents(new PreLogin(this), this);
        this.getServer().getPluginManager().registerEvents(new Wild(this), this);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        vPerm = rsp.getProvider();
        return vPerm != null;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    public boolean loadSettings(){

        //Wild settings
        try {
            UnsafeBlocks = settings.getStringList("PluginSettings.WildTP.UnsafeBlocks");
            wildWorld1 = settings.getString("PluginSettings.WildTP.Worlds.world1");
            wildWorld2 = settings.getString("PluginSettings.WildTP.Worlds.world2");
            countdown = settings().getInt("PluginSettings.WildTP.countdown");
            cooldown = settings().getInt("PluginSettings.WildTP.cooldown");
            maxX = settings().getInt("PluginSettings.WildTP.Radius.maxX");
            minX = settings().getInt("PluginSettings.WildTP.Radius.minX");
            maxZ = settings().getInt("PluginSettings.WildTP.Radius.maxZ");
            minZ = settings().getInt("PluginSettings.WildTP.Radius.minZ");

            //Filling list material list from safeBlocks
            for (String material : UnsafeBlocks) {
                materials.add(Material.getMaterial(material.toUpperCase()));
            }

            wildLoaded = true;
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void PlayerScoreboard(Player p){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getSb().get(p.getUniqueId())){
                    s = Bukkit.getScoreboardManager().getNewScoreboard();
                    p.setScoreboard(s);
                    o = s.registerNewObjective("ohneesidebar", "dummy");
                    if (p == null || !p.isOnline()) {
                        cancel();
                        return;
                    }
                    o.setDisplayName("§a§lOhnee§6§lMC");
                    o.setDisplaySlot(DisplaySlot.SIDEBAR);
                    o.getScore("§6§lRank:").setScore(10);
                    String rank = vPerm.getPrimaryGroup(p.getPlayer());
                    String rankToScore = rank.substring(0, 1).toUpperCase() + rank.substring(1);
                    o.getScore(rankToScore).setScore(9);
                    o.getScore("  ").setScore(8);
                    o.getScore("§6§lOnline:").setScore(7);
                    o.getScore(Bukkit.getOnlinePlayers().size() + " ").setScore(6);
                    o.getScore("   ").setScore(5);
                    o.getScore("§6§lMoney:").setScore(4);
                    o.getScore(String.valueOf( (int)eco.getBalance(p.getPlayer())) + ChatColor.GREEN + "$").setScore(3);
                    o.getScore("  ").setScore(2);
                    o.getScore("§6§lIP:").setScore(1);
                    o.getScore("play.ohneemc.com").setScore(0);
                }
            }
        }.runTaskTimer(this, 10, 100);
    }
}