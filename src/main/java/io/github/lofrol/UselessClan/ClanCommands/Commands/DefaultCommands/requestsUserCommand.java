package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class requestsUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Requests";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (senderClan.getRequests().size() > 0) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Requests.Label");
            for (String tempMemberName : senderClan.getRequests()) {
                ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                        UselessClan.getLocalManager().getLocalizationMessage(
                                "Requests.Unit"), tempMemberName));
            }
        }
        else {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Requests.ZeroRequests");
        }

        return true;
    }
}