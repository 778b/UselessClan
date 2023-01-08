package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class infoUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Info";
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

        ChatSender.MessageTo(tempPlayer, "UselessClan", "Info.Label");
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanName"), senderClan.getNameClan()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanPrefix"), senderClan.getPrefixClan()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.ClanLevel"), senderClan.getClanLevel()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.LeaderName"), senderClan.getLeaderName()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.MemberCount"), senderClan.getMembers().size()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.Money"), (int) senderClan.getMoneyClan()));
        ChatSender.NonTranslateMessageTo(tempPlayer, "UselessClan", String.format(
                UselessClan.getLocalManager().getLocalizationMessage("Info.Rank"), SenderRole.toString()));
        return true;
    }
}