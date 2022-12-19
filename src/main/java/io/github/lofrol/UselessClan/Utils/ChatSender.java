package io.github.lofrol.UselessClan.Utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatSender {
    public static void MessageTo(Player recipient, String prefix, String message) {
        recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&f[&8%s&f] &b%s", prefix, message)));
    }

    public static void MessageTo(CommandSender recipient, String prefix, String message) {
        recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&f[&8%s&f] &b%s", prefix, message)));
    }
}
