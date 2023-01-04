package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class demoteUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan demote %name&b - to demote player %name of your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole == EClanRole.OFFICER || senderRole == EClanRole.LEADER);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole SenderRole = null;
        if (senderClan != null) {
            SenderRole = senderClan.getMemberRole(tempPlayer.getName());
        }

        if (args.length == 1) {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            if (SenderRole == EClanRole.LEADER) {
                ChatSender.MessageTo(tempPlayer, "UselessClan",
                        "&cYou forgot about player %name, use &a/Clan promote %name");
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
            }
        } else {
            if (senderClan == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
                return false;
            }
            if (!(SenderRole == EClanRole.OFFICER || SenderRole == EClanRole.LEADER)) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                return false;
            }

            ClanMember tempClanMember = senderClan.getClanMember(args[1]);
            if (tempClanMember == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis player not in your clan!");
                return false;
            }
            if (tempClanMember.getMemberRole() == EClanRole.MEMBER) {
                if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.ROOKIE)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                    return false;
                }
                senderClan.SendMessageForOnlinePlayers(String.format(
                        "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), EClanRole.ROOKIE));
            } else if (tempClanMember.getMemberRole() == EClanRole.OFFICER) {
                if (SenderRole == EClanRole.LEADER) {
                    if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.MEMBER)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "&cThis Player already have this rank!");
                        return false;
                    }
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            "&cPlayer %s was demoted to %s", tempClanMember.getPlayerName(), EClanRole.MEMBER));
                } else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
                }
            } else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Nothing changed, :(");
            }
        }
        return true;
    }
}
