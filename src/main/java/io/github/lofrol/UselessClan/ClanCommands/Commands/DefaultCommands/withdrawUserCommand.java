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
        return "&a/Clan withdraw %value&b - to withdraw money from your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (UselessClan.EconomyPtr != null && senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanWithdraw.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole SenderRole = null;
        if (senderClan != null) {
            SenderRole = senderClan.getMemberRole(tempPlayer.getName());
        }

        if (args.length == 1) {
            if (UselessClan.EconomyPtr == null) return false;

            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            if (SenderRole.ordinal() < senderClan.getSettingsClan().RoleCanWithdraw.ordinal()) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about value of withdraw, use &a/clan withdraw %money");
        } else {
            if (UselessClan.EconomyPtr == null) return false;

            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            if (SenderRole.ordinal() < senderClan.getSettingsClan().RoleCanWithdraw.ordinal()) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }
            double moneyToWithdraw = Double.parseDouble(args[1]);
            if (moneyToWithdraw <= 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cWrong money count! Use [0;+inf)");
                return false;
            }

            double tempValue = senderClan.WithdrawMoneyFromClan(moneyToWithdraw);
            if (tempValue == 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("&cYou cant withdraw &a%s&c from you clan", moneyToWithdraw));
                return false;
            }

            ClanMember tempMember = senderClan.getClanMember(tempPlayer.getName());
            tempMember.addGeneralPlayerDeposit(-moneyToWithdraw);
            UselessClan.EconomyPtr.depositPlayer(getOfflinePlayer(tempPlayer.getName()), tempValue);
            senderClan.SendMessageForOnlinePlayers(String.format("player &a%s&b withdraw &a%s&b from clan balance", tempPlayer.getName(), tempValue));
        }
        return true;
    }
}
