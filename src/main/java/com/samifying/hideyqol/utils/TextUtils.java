package com.samifying.hideyqol.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class TextUtils {
    private static List<Component> getComponentChildrenRecursive(Component component) {
        List<Component> children = new ArrayList<>();
        for (Component child : component.children()) {
            children.add(child);
            children.addAll(getComponentChildrenRecursive(child));
        }

        if (component.children().isEmpty()) {
            children.add(component);
        }

        return children;
    }

    public static String getRawText(Component component) {
        StringBuilder builder = new StringBuilder();
        for (Component child : getComponentChildrenRecursive(component)) {
            if (child instanceof TextComponent) {
                builder.append(((TextComponent) child).content());
            }
        }

        return ChatColor.stripColor(builder.toString());
    }
}
