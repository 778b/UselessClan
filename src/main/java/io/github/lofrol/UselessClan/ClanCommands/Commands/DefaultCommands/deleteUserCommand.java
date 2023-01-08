package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class deleteUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.Delete";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null) return false;
        return (senderRole == EClanRole.LEADER);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        if (SenderRole != EClanRole.LEADER) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.WrongRank");
            return false;
        }

        UselessClan.getMainManager().DeleteClan(senderClan);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Create.ClanDeleted");
        return true;
    }
}