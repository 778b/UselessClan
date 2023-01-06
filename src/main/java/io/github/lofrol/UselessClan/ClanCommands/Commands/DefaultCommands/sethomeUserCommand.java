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
        return "Description.Sethome";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole.ordinal() >= senderClan.getSettingsClan().RoleCanSethome.ordinal());
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.WrongWorldToSet");
            return false;
        }

        Location tempLoc = tempPlayer.getLocation();
        senderClan.setHomeClan(tempLoc);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.HomeSuccessSet");
        return true;
    }
}
