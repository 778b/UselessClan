package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;


import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands.*;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.util.Map.entry;

public class BaseClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("help",       new helpUserCommand()),
            entry("requests",   new requestsUserCommand()),
            entry("top",        new topUserCommand()),
            entry("claim",      new claimUserCommand()),
            entry("create",     new createUserCommand()),
            entry("delete",     new deleteUserCommand()),
            entry("leave",      new leaveUserCommand()),
            entry("deposit",    new depositUserCommand()),
            entry("withdraw",   new withdrawUserCommand()),
            entry("home",       new homeUserCommand()),
            entry("sethome",    new sethomeUserCommand()),
            entry("settreasure",new settreasureUserCommand()),
            entry("info",       new infoUserCommand()),
            entry("mates",      new matesUserCommand()),
            entry("accept",     new acceptUserCommand()),
            entry("decline",    new declineUserCommand()),
            entry("join",       new joinUserCommand()),
            entry("kick",       new kickUserCommand()),
            entry("promote",    new promoteUserCommand()),
            entry("demote",     new demoteUserCommand()),
            entry("setting",    new settingsUserCommand())
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
