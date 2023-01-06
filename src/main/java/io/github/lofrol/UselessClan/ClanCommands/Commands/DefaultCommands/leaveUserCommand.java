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
        return "Description.Leave";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return senderClan != null;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }
        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        UselessClan.getMainManager().CalculateClanLevel(senderClan);
        if (SenderRole == EClanRole.LEADER) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.LeaderCantLeave");
            return false;
        }
        senderClan.PlayerLeavedFromClan(tempPlayer);
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Enter.SuccessLeave"), senderClan.getNameClan()));
        return true;
    }
}