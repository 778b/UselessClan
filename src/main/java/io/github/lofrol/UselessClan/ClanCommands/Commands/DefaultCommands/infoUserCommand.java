package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class infoUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan info&b - to info about your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        EClanRole SenderRole = null;
        if (senderClan != null) {
            SenderRole = senderClan.getMemberRole(tempPlayer.getName());
        } else {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou havent Clan!");
            return false;
        }

        ChatSender.MessageTo(tempPlayer, "UselessClan", "########## CLAN INFO ##########");
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Name: %s", senderClan.getNameClan()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Prefix: %s", senderClan.getPrefixClan()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Level: %s", senderClan.getClanLevel()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# LeaderName: %s", senderClan.getLeaderName()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Count of Members: %s", senderClan.getMembers().size()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Money: %d", (int) senderClan.getMoneyClan()));
        ChatSender.MessageTo(tempPlayer, "UselessClan", String.format("# Your rank: %s", SenderRole.toString()));
        return true;
    }
}