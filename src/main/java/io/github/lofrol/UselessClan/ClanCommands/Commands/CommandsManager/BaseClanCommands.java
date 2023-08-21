package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;


import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.DefaultCommands.*;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class BaseClanCommands {
    private final Map<String, CommandBase> ClanCommands;

    public BaseClanCommands() {
        ClanCommands = new HashMap<>();
        ClanCommands.put("help", new helpUserCommand());
        ClanCommands.put("requests", new requestsUserCommand());
        ClanCommands.put("top", new topUserCommand());
        ClanCommands.put("claim", new claimUserCommand());
        ClanCommands.put("calclvl", new calclvlUserCommand());
        ClanCommands.put("create", new createUserCommand());
        ClanCommands.put("delete", new deleteUserCommand());
        ClanCommands.put("leave", new leaveUserCommand());
        ClanCommands.put("home", new homeUserCommand());
        ClanCommands.put("sethome", new sethomeUserCommand());
        ClanCommands.put("rename", new renameUserCommand());
        ClanCommands.put("redesc", new redescUserCommand());
        ClanCommands.put("settreasure", new settreasureUserCommand());
        ClanCommands.put("info", new infoUserCommand());
        ClanCommands.put("mates", new matesUserCommand());
        ClanCommands.put("accept", new acceptUserCommand());
        ClanCommands.put("decline", new declineUserCommand());
        ClanCommands.put("join", new joinUserCommand());
        ClanCommands.put("kick", new kickUserCommand());
        ClanCommands.put("promote", new promoteUserCommand());
        ClanCommands.put("demote", new demoteUserCommand());
        ClanCommands.put("setting", new settingsUserCommand());
        if (UselessClan.EconomyPtr != null) {
            ClanCommands.put("deposit", new depositUserCommand());
            ClanCommands.put("withdraw", new withdrawUserCommand());
        }

    }

    public CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

    public @NotNull Collection<PlayerCommandBase> getExecutableCommands(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
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
