package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager.AdminClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class ClanAdminCommand extends Command {

    private final AdminClanCommands AdminCommands;

    public static ClanAdminCommand CreateDefaultInts() {
        return new ClanAdminCommand("ClanAdmin", "Default command for access to the clan system",
                "Use <DarkPurple>/Clan help<reset> for learning more", Stream.of("clanadmin", "ClanAdmin", "clad", "ClAd").collect(Collectors.toList()));
    }


    private ClanAdminCommand(@NotNull String name, @NotNull String description, @NotNull String usageMessage, @NotNull List<String> aliases) {
        super(name,description,usageMessage,aliases);

        AdminCommands = new AdminClanCommands();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (!tempPlayer.hasPermission("UselessClan.Admin")) return false;

        if (args.length == 0) {
            ChatSender.MessageTo(tempPlayer, "<Red>UselessClan</Red>", "Main.ClanAdminCommandWithoutArgs");
            return false;
        }

        CommandBase tempCommand = AdminCommands.getCommand(args[0]);
        if (tempCommand == null) {
            ChatSender.MessageTo(tempPlayer, "<Red>UselessClan</Red>", "Main.InvalidClanAdminCommand");
            return false;
        }

        if (!tempCommand.havePermission(sender)) {
            ChatSender.MessageTo(tempPlayer, "<Red>UselessClan</Red>", "Main.InvalidPermissionToAdminCommand");
            return false;
        }

        return tempCommand.executeCommand(sender, args);
    }

    public @NotNull Collection<CommandBase> getExecutableCommands() {
        return AdminCommands.getCommands();
    }
}
