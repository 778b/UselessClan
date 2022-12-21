package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class requestsUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan requests&b - to see list of all requests to join your clan\"";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanAccept.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole SenderRole = null;
        if (senderClan != null) {
            SenderRole = senderClan.getMemberRole(tempPlayer.getName());
        }

        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
            return false;
        }
        if (SenderRole == EClanRole.LEADER || SenderRole == EClanRole.OFFICER) {
            if (senderClan.getRequests().size() > 0) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "######## CLAN REQUESTS ########");
                for (String tempMemberName : senderClan.getRequests()) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# %s", tempMemberName));
                }
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "0 requests for join to your clan");
            }
        } else {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
            return false;
        }
        return true;
    }
}