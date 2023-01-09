package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands.*;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands.versionUserCommand;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.util.Map.entry;

public class PluginClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("version",       new versionUserCommand())
    );

    public static CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

    public static @NotNull Collection<PlayerCommandBase> getExecutableCommands(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        Collection<PlayerCommandBase> tempCommandArray = new ArrayList<>();
        for (CommandBase tempBase : ClanCommands.values()) {
            if (tempBase instanceof PlayerCommandBase tempPlayerCommand) {
                if (tempPlayerCommand.havePermission(tempPlayer, senderClan, senderRole)) {
                    tempCommandArray.add(tempPlayerCommand);
                }
            }
        }
        return tempCommandArray;
    }
}
