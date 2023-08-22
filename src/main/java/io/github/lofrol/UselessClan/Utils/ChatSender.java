package io.github.lofrol.UselessClan.Utils;

import com.google.common.base.Preconditions;
import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatSender {
    public static void MessageTo(Player recipient, String prefix, String messageKey) {
        String tempLocalizationMessage = UselessClan.getLocalManager().getLocalizationMessage(messageKey);
        recipient.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        String.format("&f[&8%s&f] &b%s", prefix, tempLocalizationMessage)));
    }

    public static void MessageTo(CommandSender recipient, String prefix, String messageKey) {
        String tempLocalizationMessage = UselessClan.getLocalManager().getLocalizationMessage(messageKey);
        recipient.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        String.format("&f[&8%s&f] &b%s", prefix, tempLocalizationMessage)));
    }

    public static void NonTranslateMessageTo(CommandSender recipient, String prefix, String message) {
        recipient.sendMessage(
                ChatColor.translateAlternateColorCodes('&',
                        String.format("&f[&8%s&f] &b%s", prefix, message)));
    }

    @NotNull
    private static String translateAlternateColorCodes(char altColorChar, @NotNull String textToTranslate) {
        Preconditions.checkArgument(textToTranslate != null, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }
}
