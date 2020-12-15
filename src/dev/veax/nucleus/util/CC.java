package dev.veax.nucleus.util;

import com.google.common.collect.Lists;
import net.md_5.bungee.api.ChatColor;

import java.util.List;

public class CC {

    public static String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static List<String> translate(List<String> message) {
        message.forEach(lines -> {
            ChatColor.translateAlternateColorCodes('&', lines);
        });
        return message;
    }
}
