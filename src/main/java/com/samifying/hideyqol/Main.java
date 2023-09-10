package com.samifying.hideyqol;

import com.samifying.hideyqol.commands.PortalCalculatorCommand;
import com.samifying.hideyqol.commands.SetPlayerCapCommand;
import com.samifying.hideyqol.listeners.*;
import com.samifying.hideyqol.utils.SetwarpHelper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
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
        this.getCommand("setmaxplayers").setExecutor(new SetPlayerCapCommand());

        // Initialisation done
        getLogger().info("HideyQOL " + this.getDescription().getVersion() + "has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        SetwarpHelper.saveConfig();
    }

    public static Main getInstance() {
        return instance;
    }
}
