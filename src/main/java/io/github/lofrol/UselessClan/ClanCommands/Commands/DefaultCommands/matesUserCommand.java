package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class matesUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan mates&b - to show list of clanmates";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return true;
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou haven't Clan!");
            return false;
        }

        ChatSender.MessageTo(tempPlayer, "UselessClan", "####### CLANMATES #######");
        for (ClanMember tempMember : senderClan.getMembers()) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", String.format(
                    "# %s &a%s %d", tempMember.getMemberRole().toString(), tempMember.getPlayerName(), (int) tempMember.getGeneralPlayerDeposit()));
        }
        return true;
    }
}
