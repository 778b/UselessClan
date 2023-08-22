package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class debugInfoAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.DebugInfo";
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Info.Admin.MissedArg");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Base.HavntClan");
            return false;
        }

        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.Admin.Label"), foundClan.getPrefixClan()));

        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanName"), foundClan.getNameClan()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanDescription"), foundClan.getDescriptionClan()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanPrefix"), foundClan.getPrefixClan()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanLevel"), foundClan.getClanLevel()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.Home"), foundClan.getHomeClan()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.Treasure"), foundClan.getTreasureClan()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.LeaderName"), foundClan.getLeaderName()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.MemberCount"), foundClan.getMembers().size()));
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.RequestCount"), foundClan.getRequests().size()));
        if (UselessClan.EconomyPtr != null) {
            ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage("Info.Money"), (int) foundClan.getMoneyClan()));
        }

        return true;
    }
}
