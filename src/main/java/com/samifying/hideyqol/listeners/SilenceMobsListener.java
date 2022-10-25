package com.samifying.hideyqol.listeners;

import com.samifying.hideyqol.PluginConstants;
import com.samifying.hideyqol.utils.TextUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilenceMobsListener implements Listener {
    @EventHandler
    public void onEntityRename(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();

        ItemStack usedItem = player.getInventory().getItemInMainHand();
        if (usedItem.getType().isAir()) {
            usedItem = player.getInventory().getItemInOffHand();
        }

        if (usedItem.getType().isAir()) return;

        if (usedItem.getType() == Material.NAME_TAG) {
            ItemMeta meta = usedItem.getItemMeta();
            if (meta == null) return;
            Component displayName = meta.displayName();
            if (displayName == null) return;

            String rawText = TextUtils.getRawText(displayName).toLowerCase();

            if (rawText.contains("silence me") || rawText.contains("silence_me") || rawText.contains("silence-me") || rawText.contains("silenceme")) {
                if (!event.getRightClicked().isSilent()) {
                    player.sendMessage(Component.text("Mob silenced!").color(PluginConstants.SUCCESS_COLOR));
                    event.getRightClicked().setSilent(true);
                }
            } else {
                if (event.getRightClicked().isSilent()) {
                    player.sendMessage(Component.text("Mob unsilenced!").color(PluginConstants.SUCCESS_COLOR));
                    event.getRightClicked().setSilent(false);
                }
            }
        }
    }
}
