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
        return "Description.Deposit";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (UselessClan.EconomyPtr != null);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (args.length == 1) {
            if (UselessClan.EconomyPtr == null) return false;

            ChatSender.MessageTo(tempPlayer, "UselessClan", "Economy.DepositWithoutArgs");
        }
        else {
            if (UselessClan.EconomyPtr == null) return false;

            double moneyToDeposit = Double.parseDouble(args[1]);
            if (moneyToDeposit <= 0 || !UselessClan.EconomyPtr.has(getOfflinePlayer(tempPlayer.getName()), moneyToDeposit)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Economy.WrongDepositMoney");
                return false;
            }

            senderClan.DepositMoneyToClan(moneyToDeposit);
            ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
            tempMember.addGeneralPlayerDeposit(moneyToDeposit);

            UselessClan.EconomyPtr.withdrawPlayer(tempPlayer, moneyToDeposit);
            senderClan.SendMessageForOnlinePlayers(String.format(
                    UselessClan.getLocalManager().getLocalizationMessage(
                            "Economy.DepositPlayer"), tempPlayer.getName(), moneyToDeposit));
        }
        return true;
    }
}