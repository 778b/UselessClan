package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class matesAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Mates";
    }


    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Mates.Admin.MissingArgToMates");
            return false;
        }

        Clan findedClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (findedClan == null) {
            ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Base.HavntClan");
            return false;
        }
        ChatSender.MessageTo(sender, "<red>UselessClan</Red>", "Mates.Label");
        for (ClanMember tempMember : findedClan.getMembers()) {
            ChatSender.NonTranslateMessageTo(sender, "<red>UselessClan</Red>", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage(
                            "Mates.Unit"), tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
        }

        return true;
    }
}
