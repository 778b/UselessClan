package io.github.lofrol.UselessClan.ClanCommands;

import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandBase;
import io.github.lofrol.UselessClan.ClanCommands.Commands.CommandsManager.BaseClanCommands;
import io.github.lofrol.UselessClan.ClanCommands.Commands.PlayerCommandBase;
import io.github.lofrol.UselessClan.ClanObjects.Clan;
import io.github.lofrol.UselessClan.ClanObjects.EClanRole;
import io.github.lofrol.UselessClan.UselessClan;
import io.github.lofrol.UselessClan.Utils.ChatSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.Bukkit.*;

public final class ClanCommand extends Command {

    private final BaseClanCommands ClanCommands;


    @NotNull
    public static ClanCommand CreateDefaultInst() {
        return new ClanCommand("Clan", "Default command for access to the clan system",
                "Use &5/Clan help&r for learning more", Stream.of("clan", "Clan").collect(Collectors.toList()));
    }

    private ClanCommand(@NotNull String name, @NotNull String description,
                        @NotNull String usageMessage,
                        @NotNull List<String> aliases) {
        super(name, description, usageMessage, aliases);

        ClanCommands = new BaseClanCommands();
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        if (!(sender instanceof Player tempPlayer)) return false;

        if (args.length == 0) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Main.ClanCommandWithoutArgs");
            return false;
        }

        CommandBase tempCommand = ClanCommands.getCommand(args[0]);

        if (tempCommand == null) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Main.InvalidClanCommand");
            return false;
        }

        if (!tempCommand.havePermission(sender)) {
            ChatSender.MessageTo(tempPlayer, "UselessClan", "Main.InvalidPermissionToCommand");
            return false;
        }

        return tempCommand.executeCommand(sender, args);
    }

    public @NotNull Collection<PlayerCommandBase> getExecutableCommands(Player tempPlayer, Clan senderClan, EClanRole senderRole) {
        return ClanCommands.getExecutableCommands(tempPlayer, senderClan, senderRole);
    }
}
