package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class requestsAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Requests";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Requests.Admin.MissingArgToMates");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Base.HavntClan");
            return false;
        }

        if (foundClan.getRequests().size() > 0) {
            ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage(
                            "Requests.Admin.Label"), foundClan.getPrefixClan()));
            for (String tempMemberName : foundClan.getRequests()) {
                ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage(
                                "Requests.Unit"), tempMemberName));
            }
        } else {
            ChatSender.MessageTo(sender, "&4UselessClan", "Requests.ZeroRequests");
        }
        return true;
    }
}
