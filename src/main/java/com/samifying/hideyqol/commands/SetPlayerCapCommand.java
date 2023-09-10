package com.samifying.hideyqol.commands;

import com.samifying.hideyqol.Main;
import com.samifying.hideyqol.PluginConstants;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class SetPlayerCapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if (args.length < 1) {
            return false;
        }

        int newCap;
        try {
            newCap = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Component.text("Invalid number!").color(PluginConstants.ERROR_COLOR));
            return true;
        }

        if (newCap < 0) {
            sender.sendMessage(Component.text("Number must not be negative!").color(PluginConstants.ERROR_COLOR));
            return true;
        }

        Main.getInstance().getServer().setMaxPlayers(newCap);

        sender.sendMessage(Component.text("Player cap set to ").color(PluginConstants.SUCCESS_COLOR)
                .append(Component.text(newCap).color(TextColor.color(0x94e2d5))));

        return true;
    }
}
