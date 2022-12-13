package io.github.lofrol.UselessClan.ClanCommands.Commands;

import io.github.lofrol.UselessClan.ClanObjects.Clan;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class PlayerCommandBase extends CommandBase {

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player tempPlayer) {
            return executeCommand(tempPlayer,null, args);
        }
        return false;
    }
    public abstract boolean executeCommand(Player tempPlayer, Clan senderClan, String[] args);
}