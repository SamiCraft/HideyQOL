package com.samifying.hideyqol.utils;

import org.bukkit.scheduler.BukkitRunnable;

// shortest java class name
public class AutoSaveSetwarpCooldownsRunnable extends BukkitRunnable {
    @Override
    public void run() {
        SetwarpHelper.saveConfig();
    }
}
