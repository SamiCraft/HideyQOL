package com.samifying.hideyqol.listeners;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Fox;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

import java.util.Optional;
import java.util.UUID;

public class SpeedFoxListener implements Listener {
    // UUID used to identify the speed attribute modifier for mounted foxes
    private static final UUID FOX_SPEED_MODIFIER_UUID = UUID.fromString("6cf41a5a-5aa8-4a89-98b0-5ff5c23cd7cb");

    @EventHandler
    public static void onEntityMount(EntityMountEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getMount() instanceof Fox fox)) return;

        AttributeInstance movementSpeed = fox.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (movementSpeed == null) return;

        // give fox attribute speed boost if it doesn't already have it
        Optional<AttributeModifier> any = movementSpeed.getModifiers().stream()
                .filter(modifier -> modifier.getUniqueId().equals(FOX_SPEED_MODIFIER_UUID))
                .findAny();
        if (any.isEmpty()) {
            double speedModifier = 0.5;
            if (fox.getAge() < 0) {
                speedModifier = 1.0;
            }

            movementSpeed.addModifier(
                    new AttributeModifier(FOX_SPEED_MODIFIER_UUID, "generic.movementSpeed", speedModifier,
                            AttributeModifier.Operation.ADD_NUMBER));
        }
    }

    @EventHandler
    public static void onEntityDismount(EntityDismountEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        if (!(event.getDismounted() instanceof Fox fox)) return;

        AttributeInstance movementSpeed = fox.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (movementSpeed == null) return;

        // remove fox attribute speed boost
        for (AttributeModifier modifier : movementSpeed.getModifiers()) {
            if (modifier.getUniqueId().equals(FOX_SPEED_MODIFIER_UUID)) {
                movementSpeed.removeModifier(modifier);
            }
        }
    }
}