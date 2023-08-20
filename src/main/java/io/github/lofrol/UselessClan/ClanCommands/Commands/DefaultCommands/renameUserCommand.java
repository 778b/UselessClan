package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class renameUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Rename";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() == EClanRole.LEADER.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRenameWithoutArgs");
            return false;
        }

        StringBuilder tempBuilder = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            tempBuilder.append(args[i]);
            tempBuilder.append(" ");
        }
        String tempStringName = tempBuilder.toString();

        if (tempStringName.length() < 5 || tempStringName.length() > 22) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRenameIncorrectLength");
            return false;
        }

        if (!senderClan.setClanName(tempStringName)) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRenameIncorrectSymbols");
            return false;
        }

        ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRenameSuccessful");
        return true;
    }
}
