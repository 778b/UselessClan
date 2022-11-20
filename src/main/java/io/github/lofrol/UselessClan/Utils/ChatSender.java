package io.github.lofrol.UselessClan.Utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class ChatSender {
    public static void MessageTo(Player recipient, String prefix, String message) {
        recipient.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&f[%s&f] %s%s", prefix, DefaultColor, message)));
    }

    public static String DefaultColor = "&b";
}
