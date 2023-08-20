package io.github.lofrol.UselessClan.ClanCommands.Commands;


import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class CommandBase {
    public abstract @NotNull String commandDescription();

    public abstract boolean executeCommand(CommandSender sender, String[] args);

    public abstract boolean havePermission(CommandSender sender);

}


