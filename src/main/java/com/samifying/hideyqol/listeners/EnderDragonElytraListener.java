package com.samifying.hideyqol.listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class EnderDragonElytraListener implements Listener {
    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.ENDER_DRAGON) return;

        // prevent adding multiple elytras
        if (event.getDrops().stream().anyMatch(i -> i.getType() == Material.ELYTRA)) return;
        event.getDrops().add(new ItemStack(Material.ELYTRA));
    }
}
