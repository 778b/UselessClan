package io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class settreasureUserCommand extends PlayerCommandBase {

    @Override
    public @NotNull String commandDescription() {
        return "Description.Settreasure";
    }

    @Override
    public boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        if (senderClan == null || senderRole == null) return false;
        return (senderRole == EClanRole.LEADER);
    }

    @Override
    public boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args) {
        if (senderClan == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Base.HavntClan");
            return false;
        }

        if (!tempPlayer.getWorld().getName().equals("world")) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.WrongWorldToSet");
            return false;
        }

        Location tempLoc = tempPlayer.getLocation();
        senderClan.setTreasureClan(tempLoc);
        ChatSender.MessageTo(tempPlayer, "UselessClan", "Treasure.TreasureSuccessSet");
        return true;
    }
}
