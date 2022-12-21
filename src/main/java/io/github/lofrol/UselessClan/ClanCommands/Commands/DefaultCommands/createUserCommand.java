package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class createUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan create %name&b - to create your own clan with name %name";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant create clan while you have been in clan!");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about clan %name, use &a/Clan create %name&b, %name = name of your clan");
            return false;
        }

        if (args[1].length() < 3) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYour name is too short, name must be >3 symbols");
            return false;
        } else if (args[1].length() > 6) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYour name is too long, name must be <7 symbols");
            return false;
        }
        // check for bad symbols
        for (char tempChar : args[1].toCharArray()) {
            if (!(tempChar == 95 || (tempChar >= 65 && tempChar <= 90) ||
                    (tempChar >= 97 && tempChar <= 122) ||
                    (tempChar >= 48 && tempChar <= 57))) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInvalid clan name, use [A-Z; a-z; _; 0-9]");
                return false;
            }
        }
        // check another clan name collision
        if (UselessClan.getMainManager().getServerClans().get(args[1]) != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cClan with this name already exist!");
            return false;
        }
        // if had Economy extension
        if (UselessClan.EconomyPtr != null) {
            double moneyToClan = 10000;
            if (!UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToClan)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&cFor create your own clan you must have more than %s$", moneyToClan));
                return false;
            }
            UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToClan);
        }
        // Creating new clan
        UselessClan.getMainManager().CreateClan(args[1], tempPlayer);
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("Clan %s was created successfully!", args[1]));
        return true;
    }
}