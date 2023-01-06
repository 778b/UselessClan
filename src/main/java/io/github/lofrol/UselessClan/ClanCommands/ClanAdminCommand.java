package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager.AdminClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.getServer;

public final class ClanAdminCommand extends Command {


    public static boolean CreateDefaultInts() {
        ClanAdminCommand tempClanAdminCommand = new ClanAdminCommand("ClanAdmin", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clanadmin", "ClanAdmin", "clad", "ClAd").collect(Collectors.toList()));
        return getServer().getCommandMap().register("[UselessClan]", tempClanAdminCommand);
    }


    private ClanAdminCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (!tempPlayer.hasPermission("UselessClan.Admin")) return false;

        if (args.length == 0) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan", "Main.ClanAdminCommandWithoutArgs");
            return false;
        }

        CommandBase tempCommand = AdminClanCommands.getCommand(args[0]);
        if (tempCommand == null) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan", "Main.InvalidClanAdminCommand");
            return false;
        }

        if (!tempCommand.havePermission(sender)) {
            ChatSender.MessageTo(tempPlayer, "&4UselessClan", "Main.InvalidPermissionToAdminCommand");
            return false;
        }

        return tempCommand.executeCommand(sender, args);
    }
}
