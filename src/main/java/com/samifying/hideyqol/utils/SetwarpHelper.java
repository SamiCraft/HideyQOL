package com.samifying.hideyqol.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class SetwarpHelper {
    private static File configFile;
    private static FileConfiguration config;

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
        if (!config.isConfigurationSection("players")) {
            config.createSection("players");
        }

        // 5 * 20 = 5 seconds
        new AutoSaveSetwarpCooldownsRunnable().runTaskTimerAsynchronously(plugin, 0, 5 * 20);
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

        return lastWarp - (System.currentTimeMillis() / 1000) + 15;
    }

    public static void setWarpCooldown(String uuid) {
        config.set("players." + uuid + ".last-set-warp", System.currentTimeMillis() / 1000);
    }
}
