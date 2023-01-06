package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static org.bukkit.Bukkit.getPlayer;

public class declineUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Decline";
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

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.ClanDeclineWithoutArgs");

        }
        else {
            String AcceptedPlayerName = args[1];
            if (!senderClan.HaveRequestFromPlayer(AcceptedPlayerName)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.CantFindRequest");
                return false;
            }
            senderClan.RemoveFromRequest(AcceptedPlayerName);

            ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan",
                    String.format(UselessClan.getLocalManager().getLocalizationMessage(
                            "Enter.PlayersRequestDeclined"), AcceptedPlayerName));
        }
        return true;
    }
}
