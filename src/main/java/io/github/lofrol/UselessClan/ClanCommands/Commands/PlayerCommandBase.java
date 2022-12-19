package io.github.lofrol.UselessClan.ClanCommands.Commands;

import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PlayerCommandBase extends CommandBase {

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player tempPlayer) {
            Clan senderClan = UselessClan.getMainManager().FindClanToPlayer(tempPlayer.getName());
            return executeCommand(tempPlayer, senderClan, args);
        }
        return false;
    }

    public abstract @NotNull String commandDescription();
    public abstract boolean havePermission(Player tempPlayer, Clan senderClan, EClanRole senderRole);
    public abstract boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args);
}