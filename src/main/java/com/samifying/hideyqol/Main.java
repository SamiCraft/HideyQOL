package com.samifying.hideyqol;

import com.samifying.hideyqol.listeners.SpeedFoxListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register events
        // todo: make them toggleable

        this.getServer().getPluginManager().registerEvents(new SpeedFoxListener(), this);

        // Initialisation done
        getLogger().info("HideyQOL " + this.getDescription().getVersion() + "has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
