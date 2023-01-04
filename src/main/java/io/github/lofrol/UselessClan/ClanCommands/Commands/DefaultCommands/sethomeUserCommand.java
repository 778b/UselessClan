package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class sethomeUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "&a/Clan sethome&b - to set home location of your clan";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal());
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

        if (!(SenderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal() || SenderRole == EClanRole.LEADER)) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou rank is too low to do that!");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "&cYou cant set clan home in this world!");
            return false;
        }

        Location tempLoc = tempPlayer.getLocation();
        senderClan.setHomeClan(tempLoc);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "&aClan home set successfully!");
        return true;
    }
}
