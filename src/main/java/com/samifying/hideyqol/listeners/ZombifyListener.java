package com.samifying.hideyqol.listeners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.*;

public class ZombifyListener implements Listener {
    @EventHandler
    public void onVillagerDeath(EntityDeathEvent event) {
        if (event.getEntityType() != EntityType.VILLAGER) return;

        EntityDamageEvent damageEvent = event.getEntity().getLastDamageCause();
        if (damageEvent == null) return;

        EntityDamageEvent.DamageCause damageCause = damageEvent.getCause();
        if (!isValidCause(damageCause)) return;

        Entity damager = getDamager(damageEvent);
        if (damager == null) return;

        Villager villager = (Villager) event.getEntity();

        // switch looks cleaner
        switch (damager.getType()) {
            case ZOMBIE, ZOMBIE_VILLAGER, HUSK, DROWNED -> {
                event.setCancelled(true);
                villager.zombify();
            }
        }
    }

    private boolean isValidCause(EntityDamageEvent.DamageCause cause) {
        return cause == ENTITY_ATTACK ||
                cause == ENTITY_SWEEP_ATTACK ||
                cause == ENTITY_EXPLOSION ||
                cause == PROJECTILE;
    }

    // casting hell
    private Entity getDamager(EntityDamageEvent damageEvent) {
        switch (damageEvent.getCause()) {
            case ENTITY_ATTACK, ENTITY_SWEEP_ATTACK -> {
                return ((EntityDamageByEntityEvent) damageEvent).getDamager();
            }
            case PROJECTILE -> {
                Entity e = ((EntityDamageByEntityEvent) damageEvent).getDamager();
                if (!(e instanceof Projectile)) return e;
                return (Entity) ((Projectile) e).getShooter();
            }
            default -> {
                return null;
            }
        }
    }
}
