package io.github.lofrol.UselessClan.ClanCommands.Commands;


import org.bukkit.command.CommandSender;

public abstract class CommandBase {
    public abstract boolean executeCommand(CommandSender sender, String[] args);

    public abstract boolean havePermission(CommandSender sender);

}


