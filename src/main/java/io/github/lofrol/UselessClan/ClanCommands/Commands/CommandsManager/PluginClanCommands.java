package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands.reloadGeneralCommand;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PluginCommands.versionUserCommand;

import java.util.Map;

import static java.util.Map.entry;

public class PluginClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("version",       new versionUserCommand()),
            entry("reload",        new reloadGeneralCommand())
    );

    public static CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

}
