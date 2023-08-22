package io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class deleteAdminCommand extends CommandBase {
    @Override
    public boolean havePermission(CommandSender sender) {
        return true;
    }

    @Override
    public @NotNull String commandDescription() {
        return "Description.Admin.Delete";
    }

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Create.Admin.MissingArgToDelete");
            return false;
        }

        Clan foundClan = UselessClan.getMainManager().getServerClans().get(args[1]);
        if (foundClan == null) {
            ChatSender.MessageTo(sender, "&4UselessClan", "Base.HavntClan");
            return false;
        }
        UselessClan.getMainManager().DeleteClan(foundClan);
        ChatSender.NonTranslateMessageTo(sender, "&4UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Create.Admin.ClanDelete"), foundClan.getPrefixClan()));

        return true;
    }
}
