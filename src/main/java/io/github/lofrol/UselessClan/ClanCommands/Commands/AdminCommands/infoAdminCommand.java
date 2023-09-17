package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class infoAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Info";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length < 2) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Info.Admin.MissedArg");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Base.HavntClan");
            return false;
        }

        ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Info.Label");
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanName"), foundClan.getNameClan()));
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanDescription"), foundClan.getDescriptionClan()));
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanPrefix"), foundClan.getPrefixClan()));
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanLevel"), foundClan.getClanLevel()));
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.LeaderName"), foundClan.getLeaderName()));
        ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.MemberCount"), foundClan.getMembers().size()));
        if (UselessClan.EconomyPtr != null) {
            ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Info.Money"), (int) foundClan.getMoneyClan()));
        }
        return true;
    }
}
