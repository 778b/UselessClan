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

import java.util.Map;

import static java.util.Map.entry;

public class AdminClanCommands {
    private static final Map<String, CommandBase> ClanCommands = Map.ofEntries(
            entry("help",       new helpAdminCommand()),
            entry("list",       new listAdminCommand()),
            entry("info",       new infoAdminCommand()),
            entry("mates",      new matesAdminCommand()),
            entry("requests",   new requestsAdminCommand()),
            entry("delete",     new deleteAdminCommand()),
            entry("home",       new homeAdminCommand()),
            entry("treasure",   new treasureAdminCommand()),
            entry("calclvl",    new calclvlAdminCommand()),
            entry("level",      new levelAdminCommand()),
            entry("debuginfo",  new debugInfoAdminCommand()),
            entry("forcejoin",  new forceJoinAdminCommand())
    );

    public static CommandBase getCommand(String key) {
        return ClanCommands.get(key);
    }

}
