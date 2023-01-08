package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class joinUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Join";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.AlreadyInClan");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.JoinWithoutArgs");
        }
        else {
            Clan ClanToRequest = UselessClan.getMainManager().getClanByName(args[1]);
            if (ClanToRequest != null) {
                if (!ClanToRequest.SendRequestForJoin(tempPlayer.getName())) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.JoinAlreadySent");
                }
            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.InvalidClanName");
                return false;
            }
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.RequestSent");
            ClanToRequest.SendMessageForOnlineOfficers(String.format(
                    UselessClan.getLocalManager().getLocalizationMessage(
                            "Enter.RequestOfficerNotify"), tempPlayer.getName()));
        }
        return true;

    }

}
