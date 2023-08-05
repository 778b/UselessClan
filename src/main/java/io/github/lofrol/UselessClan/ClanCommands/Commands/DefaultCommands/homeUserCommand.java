package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class homeUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Home";
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

        Location tempHome = senderClan.getHomeClan();
        if (tempHome == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.NoClanHome");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.WrongWorldToTeleport");
            return false;
        }

        tempPlayer.teleport(tempHome);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Home.HomeTeleportation");
        return true;
    }
}
