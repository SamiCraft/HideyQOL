package com.samifying.hideyqol.listeners;

import com.samifying.hideyqol.PluginConstants;
import com.samifying.hideyqol.utils.SetwarpHelper;
import net.ess3.api.IUser;
import net.essentialsx.api.v2.events.WarpModifyEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarpCooldownListener implements Listener {
    @EventHandler
    public void onWarpCreated(WarpModifyEvent event) {
        if (event.getCause() == WarpModifyEvent.WarpModifyCause.DELETE) return;

        IUser user = event.getUser();
        Player player = user.getBase();

        if (player.hasPermission("hideyqol.setwarpcooldown.bypass")) return;

        String uuid = user.getUUID().toString();
        long cooldown = SetwarpHelper.getWarpCoolDown(uuid);

        if (!(cooldown <= 0)) {
            long hours = cooldown / 3600;
            long minutes = (cooldown % 3600) / 60;
            long seconds = cooldown % 60;

            player.sendMessage(Component.text("You can set your next warp in ").color(PluginConstants.ERROR_COLOR)
                    .append(Component.text(hours + "h " + minutes + "m " + seconds + "s").color(TextColor.color(0xeba0ac)))
                    .append(Component.text(".").color(PluginConstants.ERROR_COLOR)));
            event.setCancelled(true);
        } else {
            SetwarpHelper.setWarpCooldown(uuid);
        }
    }
}
