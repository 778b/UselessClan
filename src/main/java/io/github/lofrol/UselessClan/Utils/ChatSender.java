package io.github.lofrol.UselessClan.Utils;

import com.google.common.base.Preconditions;
import io.github.lofrol.UselessClan.UselessClan;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ChatSender {
    public static void MessageTo(Player recipient, String prefix, String messageKey) {
        String tempLocalizationMessage = UselessClan.getLocalManager().getLocalizationMessage(messageKey);

        var coloredText = MiniMessage.miniMessage();
        Component parsedText = coloredText.deserialize( String.format(
                "<White>[</White><DarkGray>%s</DarkGray><White>]</White> <Aqua>%s</Aqua>",
                prefix, tempLocalizationMessage));

        recipient.sendMessage(parsedText);
    }

    public static void MessageTo(CommandSender recipient, String prefix, String messageKey) {
        String tempLocalizationMessage = UselessClan.getLocalManager().getLocalizationMessage(messageKey);

        var coloredText = MiniMessage.miniMessage();
        Component parsedText = coloredText.deserialize( String.format(
                "<White>[</White><DarkGray>%s</DarkGray><White>]</White> <Aqua>%s</Aqua>",
                prefix, tempLocalizationMessage));

        recipient.sendMessage(parsedText);
    }

    public static void NonTranslateMessageTo(CommandSender recipient, String prefix, String message) {
        var coloredText = MiniMessage.miniMessage();
        Component parsedText = coloredText.deserialize(String.format(
                "<White>[</White><DarkGray>%s</DarkGray><White>]</White> <Aqua>%s</Aqua>",
                prefix, message));
        
        recipient.sendMessage(parsedText);
    }
}
