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

public class withdrawUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Withdraw";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (UselessClan.EconomyPtr != null && senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanWithdraw.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (UselessClan.EconomyPtr == null) return false;

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Economy.WithdrawWithoutArgs");
        }
        else {

            double moneyToWithdraw = Double.parseDouble(args[1]);
            if (moneyToWithdraw <= 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Economy.WrongWithdrawMoney");
                return false;
            }

            double tempValue = senderClan.WithdrawMoneyFromClan(moneyToWithdraw);
            if (tempValue == 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage("Economy.NotEnoughMoney"), moneyToWithdraw));
                return false;
            }

            ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
            tempMember.addGeneralPlayerDeposit(-moneyToWithdraw);
            UselessClan.EconomyPtr.depositPlayer(getOfflinePlayer(tempPlayer.getName()), tempValue);
            senderClan.SendMessageForOnlinePlayers(String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Economy.WithdrawPlayer"), tempPlayer.getName(), tempValue));
        }
        return true;
    }
}
