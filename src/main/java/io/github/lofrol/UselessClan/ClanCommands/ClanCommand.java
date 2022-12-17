package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.UserCommands.baseClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.UserCommands.requestClanCommands;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanRole;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.*;

public final class ClanCommand extends Command {
    private final Map<String, CommandBase> ClanCommands;
    public static ClanCommand CreateDefaultInst(ClanManager manager) {
        ClanCommand tempInst = new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));
        return tempInst;
    }

    public boolean registerCommand() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    private ClanCommand(@NotNull String name, @NotNull String description,
                          @NotNull String usageMessage,
                          @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
        ClanCommands = new HashMap<>();
        baseClanCommands.setupCommands(ClanCommands);
        requestClanCommands.setupCommands(ClanCommands);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (args.length == 0) {
            ChatSender.MessageTo(tempPlayer,"UselessClan", "Use command &a/Clan help&b, for access to clan system");
            return false;
        }

        CommandBase tempCommand = ClanCommands.get(args[0]);

        if (tempCommand == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan",
                    "&cInvalid command. Use command &a/Clan help&c, for access to clan system");
            return false;
        }

        return tempCommand.executeCommand(sender, args);
    }
}
