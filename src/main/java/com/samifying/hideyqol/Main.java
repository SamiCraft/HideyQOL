package com.samifying.hideyqol;

import com.samifying.hideyqol.commands.PortalCalculatorCommand;
import com.samifying.hideyqol.listeners.*;
import com.samifying.hideyqol.utils.SetwarpHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        // Plugin startup logic

        SetwarpHelper.init(this);

        // Register events
        // todo: make them toggleable
        this.getServer().getPluginManager().registerEvents(new SpeedFoxListener(), this);
        this.getServer().getPluginManager().registerEvents(new SilenceMobsListener(), this);
        this.getServer().getPluginManager().registerEvents(new EnderDragonElytraListener(), this);
        this.getServer().getPluginManager().registerEvents(new ZombifyListener(), this);

        if (this.getServer().getPluginManager().getPlugin("Essentials") != null) {
            this.getServer().getPluginManager().registerEvents(new WarpCooldownListener(), this);
        } else {
            getLogger().warning("EssentialsX not loaded, not registering WarpCooldownListener!");
        }

        // Register commands
        this.getCommand("portalcalc").setExecutor(new PortalCalculatorCommand());

        // Initialisation done
        getLogger().info("HideyQOL " + this.getDescription().getVersion() + "has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetwarpHelper.saveConfig();
    }
}
