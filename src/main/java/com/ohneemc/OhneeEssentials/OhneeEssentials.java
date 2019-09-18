package com.ohneemc.OhneeEssentials;

import com.ohneemc.OhneeEssentials.commands.*;
import com.ohneemc.OhneeEssentials.events.*;
import com.ohneemc.OhneeEssentials.resources.MessageHelper;
import com.ohneemc.OhneeEssentials.resources.WarpConfigHelper;
import de.leonhard.storage.Json;
import de.leonhard.storage.Toml;
import de.leonhard.storage.Yaml;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bstats.bukkit.Metrics;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class OhneeEssentials extends JavaPlugin {
    private boolean settingsLoaded = false;

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

    public String wildWorld1;
    public String wildWorld2;
    public List<Material> materials = new ArrayList<>();
    public int w1MaxX;
    public int w1MinX;
    public int w1MaxZ;
    public int w1Minz;
    public int w2MaxX;
    public int w2MinX;
    public int w2MaxZ;
    public int w2Minz;
    public int cooldown;

    //Spawn
    public HashMap<World, Location> worldSpawns = new HashMap<>();

    //AFK
    public int afkTimer;
    public boolean afkCancelOnChat;

    @SuppressWarnings("ConstantConditions")
    @Override
    public void onEnable() {
        //Enabling metrics
        Metrics metrics = new Metrics(this);
        setupPermissions();
        setupEconomy();

        s = Bukkit.getScoreboardManager().getNewScoreboard();
        o = s.registerNewObjective("sidebar", "dummy", ChatColor.DARK_GREEN + "OhneeMC Sidebar");

        //Setting defaults!
        settings.setDefault("PluginSettings.Warp.toml", false);
        settings.setDefault("PluginSettings.Warp.yaml", false);
        settings.setDefault("PluginSettings.Warp.json", true);
        settings.setDefault("PluginSettings.WildTP.cooldown", 60);
        settings.setDefault("PluginSettings.WildTP.World1.Radius.w1MaxX", 5000);
        settings.setDefault("PluginSettings.WildTP.World1.Radius.w1MinX", -5000);
        settings.setDefault("PluginSettings.WildTP.World1.Radius.w1MaxZ", 5000);
        settings.setDefault("PluginSettings.WildTP.World1.Radius.w1Minz", -5000);
        settings.setDefault("PluginSettings.WildTP.World2.Radius.w1MaxX", 4200);
        settings.setDefault("PluginSettings.WildTP.World2.Radius.w1MinX", -4200);
        settings.setDefault("PluginSettings.WildTP.World2.Radius.w1MaxZ", 4200);
        settings.setDefault("PluginSettings.WildTP.World2.Radius.w1Minz", -4200);
        List<String> defaltUnsafeBlocks = Arrays.asList("WATER", "LAVA", "AIR");
        settings.setDefault("PluginSettings.WildTP.UnsafeBlocks", defaltUnsafeBlocks);
        settings.setDefault("PluginSettings.WildTP.Worlds.world1", "world");
        settings.setDefault("PluginSettings.WildTP.Worlds.world2", "world_nether");
        settings.setDefault("PluginSettings.Teleportation.Tp.TimeToRespond", 30);
        List<String> defaultGroups = Arrays.asList("admin:10", "mod:10", "vip:5", "regular:3", "default:1");
        settings.setDefault("PluginSettings.Homes.LimitPrGroup", defaultGroups);
        settings.setDefault("PluginSettings.AFK.After", 5);
        settings.setDefault("PluginSettings.AFK.CancelOnChat", false);

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

        if (!loadSettings()){
            Bukkit.getLogger().warning("[Ohnee] Something went wrong while loading the settings.");
            Bukkit.getLogger().warning("[Ohnee] Disabling my self... Bye bye...");
            getPluginLoader().disablePlugin(this);
        }

        warpConfigHelper.warpLoad();

        registerEvents();
        registerCommands();


    }

    public void onDisable() {

    }

    @SuppressWarnings("ConstantConditions")
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
        //this.getCommand("import").setExecutor(new Import(this));
        this.getCommand("me").setExecutor(new Me(this));
        this.getCommand("invsee").setExecutor(new Invsee(this));
        this.getCommand("fly").setExecutor(new Fly(this));
        this.getCommand("uuid").setExecutor(new Uuid(this));
        this.getCommand("scoreboard").setExecutor(new Scoreboard(this));
        this.getCommand("vanish").setExecutor(new Vanish(this));
        this.getCommand("guide").setExecutor(new Guide(this));
        this.getCommand("afk").setExecutor(new Afk());
    }

    private void registerEvents() {
        //Register events class
        this.getServer().getPluginManager().registerEvents(new JoinQuit(this), this);
        this.getServer().getPluginManager().registerEvents(new KeepXp(this), this);
        this.getServer().getPluginManager().registerEvents(new LastLocation(this), this);
        this.getServer().getPluginManager().registerEvents(new PreLogin(this), this);
        this.getServer().getPluginManager().registerEvents(new Wild(this), this);
        this.getServer().getPluginManager().registerEvents(new Invsee(this), this);
        this.getServer().getPluginManager().registerEvents(new AfkListener(this), this);
    }

    private void setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp == null) {
            return;
        }
        vPerm = rsp.getProvider();
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        eco = rsp.getProvider();
    }

    public boolean loadSettings(){
        try {
            Bukkit.getLogger().info("[Ohnee] Grabbing settings for wild.");
            //Wild
            List<String> unsafeBlocks = settings.getStringList("PluginSettings.WildTP.UnsafeBlocks");
            wildWorld1 = settings.getString("PluginSettings.WildTP.Worlds.world1");
            wildWorld2 = settings.getString("PluginSettings.WildTP.Worlds.world2");
            cooldown = settings().getInt("PluginSettings.WildTP.cooldown");
            w1MaxX = settings().getInt("PluginSettings.WildTP.World1.Radius.w1MaxX");
            w1MinX = settings().getInt("PluginSettings.WildTP.World1.Radius.w1MinX");
            w1MaxZ = settings().getInt("PluginSettings.WildTP.World1.Radius.w1MaxZ");
            w1Minz = settings().getInt("PluginSettings.WildTP.World1.Radius.w1Minz");
            w2MaxX = settings().getInt("PluginSettings.WildTP.World2.Radius.w1MaxX");
            w2MinX = settings().getInt("PluginSettings.WildTP.World2.Radius.w1MinX");
            w2MaxZ = settings().getInt("PluginSettings.WildTP.World2.Radius.w1MaxZ");
            w2Minz = settings().getInt("PluginSettings.WildTP.World2.Radius.w1Minz");

            //Filling list material list from safeBlocks
            for (String material : unsafeBlocks) {
                materials.add(Material.getMaterial(material.toUpperCase()));
            }

            Bukkit.getLogger().info("[Ohnee] Grabbing spawn locations.");
            //Spawn
            for (String spawn : worldData.getKeySet()){
                Bukkit.getLogger().info("[Ohnee] Spawn Loc name "+spawn);
                double x = worldData.getDouble(spawn + ".X");
                double y = worldData.getDouble(spawn + ".Y");
                double z = worldData.getDouble(spawn + ".Z");
                float yaw = worldData.getFloat(spawn + ".Yaw");
                float pitch = worldData.getFloat(spawn + ".Pitch");
                String world = worldData.getString(spawn + ".name");

                World currWorld = getServer().getWorld(world);
                Location spawnLoc = new Location(currWorld, x,y,z,yaw,pitch);
                worldSpawns.put(currWorld, spawnLoc);
            }

            Bukkit.getLogger().info("[Ohnee] Grabbing AFK settings.");
            //AFK
            afkTimer = settings.getInt("PluginSettings.AFK.After");
            afkCancelOnChat = settings.getBoolean("PluginSettings.AFK.CancelOnChat");


            Bukkit.getLogger().info("[Ohnee] Settings loaded successfully.");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @SuppressWarnings("ConstantConditions")
    public void PlayerScoreboard(Player p){
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getSb().get(p.getUniqueId())){
                    s = Bukkit.getScoreboardManager().getNewScoreboard();
                    p.setScoreboard(s);
                    o = s.registerNewObjective("ohneesidebar", "dummy", ChatColor.DARK_GREEN + "OhneeMC Sidebar");
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
                    o.getScore("    ").setScore(8);
                    o.getScore("§6§lOnline:").setScore(7);
                    String pOnline = String.valueOf(Bukkit.getOnlinePlayers().size());
                    o.getScore(pOnline).setScore(6);
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