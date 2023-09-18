package com.samifying.hideyqol.listeners;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class MendingItemSaveListener implements Listener {
    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();

        if (isUnusable(item)) {
            event.setCancelled(true);
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (!meta.hasEnchant(Enchantment.MENDING)) return;
        if (isArmorPiece(item)) return;

        int maxDurability = item.getType().getMaxDurability();
        int totalDamage = ((Damageable) meta).getDamage() + event.getDamage();
        int targetDurability = maxDurability - totalDamage;

        if (targetDurability <= 1) {
            ((Damageable) meta).setDamage(maxDurability - 1);
            item.setItemMeta(meta);

            event.setCancelled(true);
        }
    }

    private boolean isUnusable(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable)) return false;
        if (!meta.hasEnchant(Enchantment.MENDING)) return false;
        if (isArmorPiece(item)) return false;

        int durability = item.getType().getMaxDurability() - ((Damageable) meta).getDamage();
        return durability <= 1;
    }

    // this code makes me sad ☹
    // pls tell me there's a better way to do this without NMS or hardcoding
    private boolean isArmorPiece(ItemStack item) {
        return item.getType().name().endsWith("_HELMET")
                || item.getType().name().endsWith("_CHESTPLATE")
                || item.getType().name().endsWith("_LEGGINGS")
                || item.getType().name().endsWith("_BOOTS")
                || item.getType().equals(Material.ELYTRA);
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

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (isUnusable(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    // (are there more events that need to be cancelled?)
}
