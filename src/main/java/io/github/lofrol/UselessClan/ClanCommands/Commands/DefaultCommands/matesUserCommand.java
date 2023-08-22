package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class matesUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Mates";
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

        ChatSender.MessageTo(tempPlayer, "UselessClan", "Mates.Label");
        for (ClanMember tempMember : senderClan.getMembers()) {
            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                    UselessClan.getLocalManager().getLocalizationMessage(
                            "Mates.Unit"), tempMember.getMemberRole().toString(), tempMember.getPlayerName()));
        }
        return true;
    }
}
