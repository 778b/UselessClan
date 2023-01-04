package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getOfflinePlayer;

public class depositUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan deposit %value&b - to deposit money to your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (UselessClan.EconomyPtr != null);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
            return false;
        }

        if (args.length == 1) {
            if (UselessClan.EconomyPtr == null) return false;

            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about value of deposit, use &a/clan deposit %money");
        } else {
            if (UselessClan.EconomyPtr == null) return false;

            double moneyToDeposit = Double.parseDouble(args[1]);
            if (moneyToDeposit <= 0 || !UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToDeposit)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cWrong money count!");
                return false;
            }
            senderClan.DepositMoneyToClan(moneyToDeposit);
            ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
            tempMember.addGeneralPlayerDeposit(moneyToDeposit);

            UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToDeposit);
            senderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b deposit &a%s&b to your clan!", tempPlayer.getName(), moneyToDeposit));
        }
        return true;
    }
}