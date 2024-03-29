package com.samifying.hideyqol.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SetwarpHelper {
    private static File configFile;
    private static FileConfiguration config;

    private static long cooldown = 0;

    public static void init(JavaPlugin plugin) {
        configFile = new File(plugin.getDataFolder(), "setwarp-cooldown.yml");

        try {
            plugin.getDataFolder().mkdirs();
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        config = YamlConfiguration.loadConfiguration(configFile);
        config.addDefault("cooldown", 172800);

        cooldown = config.getLong("cooldown");

        if (!config.isConfigurationSection("players")) {
            config.createSection("players");
        }

        // 30 * 20 = 30 seconds
        new AutoSaveSetwarpCooldownsRunnable().runTaskTimerAsynchronously(plugin, 0, 30 * 20);
    }

    public static void saveConfig() {
        try {
            config.save(configFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static long getWarpCoolDown(String uuid) {
        long lastWarp = config.getLong("players." + uuid + ".last-set-warp", 0);
        if (lastWarp == 0) return 0;

        return lastWarp - (System.currentTimeMillis() / 1000) + cooldown;
    }

    public static void setWarpCooldown(String uuid) {
        config.set("players." + uuid + ".last-set-warp", System.currentTimeMillis() / 1000);
    }
}
