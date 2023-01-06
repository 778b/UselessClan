package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class promoteUserCommand extends PlayerCommandBase {
    @Override
    public @NotNull String commandDescription() {
        return "Description.Promote";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole == EClanRole.OFFICER || senderRole == EClanRole.LEADER);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        EClanRole SenderRole = senderClan.getMemberRole(tempPlayer.getName());

        if (args.length == 1) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Rank.ClanPromoteWithoutArgs");

        }
        else {
            ClanMember tempClanMember = senderClan.getClanMember(args[1]);

            if (tempClanMember == null) {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Enter.VictimWrongClan");
                return false;
            }
            if (tempClanMember.getMemberRole() == EClanRole.ROOKIE) {
                if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.MEMBER)) {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Rank.VictimHadThisRank");
                    return false;
                }
                senderClan.SendMessageForOnlinePlayers(String.format(
                        UselessClan.getLocalManager().getLocalizationMessage(
                                "Rank.PlayerPromote"), tempClanMember.getPlayerName(), EClanRole.MEMBER));
            }
            else if (tempClanMember.getMemberRole() == EClanRole.MEMBER) {
                if (SenderRole == EClanRole.LEADER) {
                    if (!senderClan.ChangeMemberRole(tempClanMember.getPlayerName(), EClanRole.OFFICER)) {
                        ChatSender.MessageTo(tempPlayer, "UselessClan", "Rank.VictimHadThisRank");
                        return false;
                    }
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Rank.PlayerPromote"), tempClanMember.getPlayerName(), EClanRole.OFFICER));
                }
                else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.WrongRank");
                }
            }
            else if (tempClanMember.getMemberRole() == EClanRole.OFFICER) {
                if (SenderRole == EClanRole.LEADER) {
                    senderClan.ChangeLeader(tempClanMember.getPlayerName());
                    senderClan.SendMessageForOnlinePlayers(String.format(
                            UselessClan.getLocalManager().getLocalizationMessage(
                                    "Rank.LeaderChanged"), tempClanMember.getPlayerName()));
                }
                else {
                    ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.WrongRank");
                }
            }
            else {
                ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.NothingChanged");
            }
        }
        return true;
    }
}
