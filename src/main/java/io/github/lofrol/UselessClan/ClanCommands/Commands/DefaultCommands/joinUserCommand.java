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
        return "&a/Clan join %name&b - to send request for join the clan %name";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan != null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou already have Clan!");
            return false;
        }

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou forgot about clan %name, use &a/Clan join %name&b, %name = name of clan");
        } else {
            Clan ClanToRequest = UselessClan.getMainManager().getClanByName(args[1]);
            if (ClanToRequest != null) {
                if (!ClanToRequest.SendRequestForJoin(tempPlayer.getName())) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou already sent request for join to this clan!");
                }
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cInvalid clan name!");
                return false;
            }
            ChatSender.MessageTo(tempPlayer, "UselessClan", "You send request for join to this clan, wait until leader or officer accept this request");
            ClanToRequest.SendMessageForOnlineOfficers(String.format("Player %s was send request for join to you clan, type &a/clan requests", tempPlayer.getName()));
        }
        return true;

    }

}
