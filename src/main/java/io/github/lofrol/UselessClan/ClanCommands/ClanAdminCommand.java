package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.AdminCommands.AdminClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanManager;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.ClanMember;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public final class ClanAdminCommand extends Command {

    private final Map<String, CommandBase> ClanCommands;

    public static ClanAdminCommand CreateDefaultInts(ClanManager manager) {
        return new ClanAdminCommand("ClanAdmin", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clanadmin", "ClanAdmin", "clad", "ClAd").collect(Collectors.toList()));
    }

    public boolean registerComamnd() {
        return getServer().getCommandMap().register("[UselessClan]", this);
    }

    private ClanAdminCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
        ClanCommands = new HashMap<>();
        AdminClanCommands.setupCommands(ClanCommands);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (!tempPlayer.hasPermission("UselessClan.Admin")) return false;

        if (args.length == 0) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan","Use command /ClanAdmin help, for access to clan system");
            return false;
        }

        CommandBase tempCommand = ClanCommands.get(args[0]);
        if (tempCommand == null) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan",
                    "&cInvalid command. Use command &a/ClanAdmin help&c, for access to clan system");
            return false;
        }
        return tempCommand.executeCommand(sender, args);
    }
}
