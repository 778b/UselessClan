package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;

import io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands.*;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.UselessClan;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class AdminClanCommands {
    private final Map<String, CommandBase> ClanCommands;

    public AdminClanCommands() {
        ClanCommands = new HashMap<>();
        ClanCommands.put("help", new helpAdminCommand());
        ClanCommands.put("list", new listAdminCommand());
        ClanCommands.put("info", new infoAdminCommand());
        ClanCommands.put("mates", new matesAdminCommand());
        ClanCommands.put("requests", new requestsAdminCommand());
        ClanCommands.put("delete", new deleteAdminCommand());
        ClanCommands.put("home", new homeAdminCommand());
        ClanCommands.put("treasure", new treasureAdminCommand());
        ClanCommands.put("calclvl", new calclvlAdminCommand());
        ClanCommands.put("level", new levelAdminCommand());
        ClanCommands.put("debuginfo", new debugInfoAdminCommand());
        ClanCommands.put("forcejoin", new forceJoinAdminCommand());
        if (UselessClan.EconomyPtr != null) {
            ClanCommands.put("deposit", new depositAdminCommand());
            ClanCommands.put("withdraw", new withdrawAdminCommand());
        }
    }

    public CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

    public Collection<CommandBase> getCommands() {
        return ClanCommands.values();
    }
}
