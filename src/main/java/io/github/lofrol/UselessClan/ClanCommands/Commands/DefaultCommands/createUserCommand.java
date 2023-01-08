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
        return "Description.Create";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.AlreadyInClan");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.MissedArgToCreate");
            return false;
        }

        if (args[1].length() < 3) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.PrefixIsShorterThree");
            return false;
        } else if (args[1].length() > 6) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.PrefixIsLongerSeven");
            return false;
        }
        // check for bad symbols
        for (char tempChar : args[1].toCharArray()) {
            if (!(tempChar == 95 || (tempChar >= 65 && tempChar <= 90) ||
                    (tempChar >= 97 && tempChar <= 122) ||
                    (tempChar >= 48 && tempChar <= 57))) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.InvalidPrefixSymbols");
                return false;
            }
        }
        // check another clan name collision
        if (UselessClan.getMainManager().getServerClans().get(args[1]) != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.PrefixAlreadyExist");
            return false;
        }
        // if had Economy extension
        if (UselessClan.EconomyPtr != null) {
            double moneyToClan = UselessClan.getConfigManager().getClanConfig().getMoneyToCreate();
            if (!UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToClan)) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                        String.format(UselessClan.getLocalManager().getLocalizationMessage(
                                "Create.NotEnoughMoneyToCreate"), moneyToClan));
                return false;
            }
            UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToClan);
        }
        // Creating new clan
        UselessClan.getMainManager().CreateClan(args[1], tempPlayer);
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Create.SuccessCreateClan"), args[1]));
        return true;
    }
}