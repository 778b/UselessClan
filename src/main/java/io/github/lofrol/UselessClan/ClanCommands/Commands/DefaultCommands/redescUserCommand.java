package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class redescUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Redescription";
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
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRedescWithoutArgs");
            return false;
        }

        StringBuilder tempBuilder = new StringBuilder();
        for (int i = 1; i < args.length; ++i) {
            tempBuilder.append(args[i]);
            tempBuilder.append(" ");
        }
        String tempStringName = tempBuilder.toString();

        if (tempStringName.length() < 5 || tempStringName.length() > 220) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRedescIncorrectLength");
            return false;
        }

        if (!senderClan.setClanDescription(tempStringName)) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRedescIncorrectSymbols");
            return false;
        }

        ChatSender.MessageTo(tempPlayer, "UselessClan", "Rename.ClanRedescSuccessful");
        return true;
    }
}
