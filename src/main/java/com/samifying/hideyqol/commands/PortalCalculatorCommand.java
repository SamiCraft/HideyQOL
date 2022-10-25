package com.samifying.hideyqol.commands;

import com.samifying.hideyqol.PluginConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class PortalCalculatorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Location location = player.getLocation();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        int resultX;
        int resultZ;

        switch (location.getWorld().getEnvironment()) {
            case NORMAL -> {
                resultX = x / 8;
                resultZ = z / 8;
            }
            case NETHER -> {
                resultX = x * 8;
                resultZ = z * 8;
            }
            default -> {
                player.sendMessage(Component.text("You have to be in the").color(PluginConstants.ERROR_COLOR)
                        .append(Component.text(" overworld ").color(TextColor.color(0x1CBA00)))
                        .append(Component.text("or").color(PluginConstants.ERROR_COLOR))
                        .append(Component.text(" the Nether ").color(TextColor.color(0xF27000)))
                        .append(Component.text("to use this command!").color(PluginConstants.ERROR_COLOR)));
                return true;
            }
        }

        Component coordinateComponentX = Component.text("X: ").append(Component.text(resultX));
        Component coordinateComponentZ = Component.text("Z: ").append(Component.text(resultZ));

        Component coordinateComponent = coordinateComponentX.color(TextColor.color(0xFFFFFF))
                .append(Component.text(" "))
                .append(coordinateComponentZ.color(TextColor.color(0xFFFFFF)));

        player.sendMessage(Component.text("The portal coordinates are: ").color(PluginConstants.SUCCESS_COLOR)
                .append(coordinateComponent.decorate(TextDecoration.UNDERLINED)));
        return true;
    }
}
