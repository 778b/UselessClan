package io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager;

import io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands.*;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

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

    }

    public CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

}
