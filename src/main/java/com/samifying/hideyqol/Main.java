package com.samifying.hideyqol;

import com.samifying.hideyqol.commands.PortalCalculatorCommand;
import com.samifying.hideyqol.listeners.SilenceMobsListener;
import com.samifying.hideyqol.listeners.SpeedFoxListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic

        // Register events
        // todo: make them toggleable
        this.getServer().getPluginManager().registerEvents(new SpeedFoxListener(), this);
        this.getServer().getPluginManager().registerEvents(new SilenceMobsListener(), this);

        // Register commands
        this.getCommand("portalcalc").setExecutor(new PortalCalculatorCommand());

        // Initialisation done
        getLogger().info("HideyQOL " + this.getDescription().getVersion() + "has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
