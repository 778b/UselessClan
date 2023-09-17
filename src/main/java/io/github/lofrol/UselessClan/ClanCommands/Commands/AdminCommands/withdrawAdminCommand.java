package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class withdrawAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Withdraw";
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length < 3) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Economy.Admin.WithdrawWithoutArgs");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Base.HavntClan");
            return false;
        }

        double moneyToWithdraw = Double.parseDouble(args[1]);
        if (moneyToWithdraw <= 0) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Economy.WrongWithdrawMoney");
            return false;
        }

        foundClan.WithdrawMoneyFromClan(moneyToWithdraw);
        foundClan.SendMessageForOnlinePlayers(String.format(
                UselessClan.getLocalManager().getLocalizationMessage(
                        "Economy.WithdrawPlayer"), sender.getName(), moneyToWithdraw));
        return true;
    }
}
