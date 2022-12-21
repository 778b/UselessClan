package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class leaveUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan leave&b - to leave from your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
            return false;
        }
        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        UselessClan.getMainManager().CalculateClanLevel(senderClan);
        if (SenderRole == EClanRole.LEADER) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant leave from clan, because you are Leader of this clan");
            return false;
        }
        senderClan.PlayerLeavedFromClan(tempPlayer);
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("You successfully leaved from &6%s", senderClan.getNameClan()));
        return true;
    }
}