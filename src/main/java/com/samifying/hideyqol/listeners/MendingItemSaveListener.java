package com.samifying.hideyqol.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerItemMendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class MendingItemSaveListener implements Listener {
    private static final NamespacedKey HIDEYQOL_UNUSABLE = new NamespacedKey("hideyqol", "unusable");
    private static final Component UNUSABLE_LORE = Component.text("Broken").color(TextColor.color(0xFF0000));

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        Material type = item.getType();

        if (isUnusable(item)) {
            event.setCancelled(true);
            return;
        }

        int maxDurability = type.getMaxDurability();
        int totalDamage = ((Damageable) item.getItemMeta()).getDamage() + event.getDamage();
        int targetDurability = maxDurability - totalDamage;

        if (targetDurability <= 1) {
            ItemMeta meta = item.getItemMeta();
            meta.getPersistentDataContainer().set(HIDEYQOL_UNUSABLE, PersistentDataType.BOOLEAN, true);
            ((Damageable) meta).setDamage(maxDurability - 1);
            item.setItemMeta(meta);

            List<Component> lore = item.lore();
            if (lore == null) {
                lore = new ArrayList<>();
            }
            lore.add(UNUSABLE_LORE);
            item.lore(lore);

            event.setCancelled(true);
        }
    }
    
    @EventHandler
    public void onItemRepair(PlayerItemMendEvent event) {
        System.out.println("item repaired !");
        ItemStack item = event.getItem();
        Material type = item.getType();

        int maxDurability = type.getMaxDurability();
        int totalDamage = ((Damageable) item.getItemMeta()).getDamage() - event.getRepairAmount();
        int targetDurability = maxDurability - totalDamage;

        if (targetDurability > 1) {
            ItemMeta meta = item.getItemMeta();

            meta.getPersistentDataContainer().remove(HIDEYQOL_UNUSABLE);
            ((Damageable) meta).setDamage(totalDamage);
            item.setItemMeta(meta);

            List<Component> lore = item.lore();
            if (lore != null) {
                lore.remove(UNUSABLE_LORE);
                item.lore(lore);
            }
        }
    }

    private boolean isUnusable(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer().has(HIDEYQOL_UNUSABLE, PersistentDataType.BOOLEAN);
    }

    // --- listeners for item usage ---

    @EventHandler
    public void onPlayerBreakBlockEvent(BlockBreakEvent event) {
        if (isUnusable(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamageEntityEvent(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player player) {
            if (isUnusable(player.getInventory().getItemInMainHand())) {
                event.setCancelled(true);
            }
        }
    }

    // (are there more events that need to be cancelled?)
}
